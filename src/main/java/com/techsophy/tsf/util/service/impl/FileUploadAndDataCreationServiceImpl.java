package com.techsophy.tsf.util.service.impl;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.techsophy.idgenerator.IdGeneratorImpl;
import com.techsophy.tsf.util.config.GlobalMessageSource;
import com.techsophy.tsf.util.dto.FileDataUploadResponse;
import com.techsophy.tsf.util.dto.FileUploadDataSchema;
import com.techsophy.tsf.util.entity.FileDataDefinition;
import com.techsophy.tsf.util.exception.*;
import com.techsophy.tsf.util.repository.FileUploadDataRepository;
import com.techsophy.tsf.util.service.FileUploadAndDataCreationService;
import com.techsophy.tsf.util.utils.TokenUtils;
import com.techsophy.tsf.util.utils.UserDetails;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static com.techsophy.tsf.util.constants.FileUploadConstants.*;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FileUploadAndDataCreationServiceImpl implements FileUploadAndDataCreationService {

  private final ObjectMapper objectMapper;
  private final GlobalMessageSource globalMessageSource;
  private final IdGeneratorImpl idGenerator;
  private final UserDetails userDetails;
  private final TokenUtils tokenUtils;
  private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);
  public static final Map<String, String> SUPPORTED_TYPES = Map.of(
    CSV, MediaType.TEXT_PLAIN_VALUE);
  private final FileUploadDataRepository fileUploadDataRepository;

  @Override
  public FileDataUploadResponse uploadRecords(MultipartFile file) throws IOException {
    Map<String, Object> loggedInUser = userDetails.getUserDetails().get(0);
    logger.info(FILE_VALIDATIONS_GOING_ON);

    List<String> validationErrors = new ArrayList<>();

    try (InputStream inputStream = file.getInputStream()) {
      if (file.isEmpty()) {
        validationErrors.add(PLEASE_SELECT_CSV_FILE_WITH_DATA);
      }

      String fileName = file.getOriginalFilename();
      if (StringUtils.isBlank(fileName)) {
        validationErrors.add(CSV_FILE_NAME_NOT_EMPTY);
      } else {
        String fileExtension = FilenameUtils.getExtension(fileName);
        if (!SUPPORTED_TYPES.containsKey(fileExtension)) {
          validationErrors.add("UNSUPPORTED_FILE_TYPE");
        } else {
          CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
          CsvMapper csvMapper = new CsvMapper();
          try (MappingIterator<Map<String, Object>> iterator = csvMapper.readerFor(Map.class).with(csvSchema).readValues(inputStream)) {
            iterator.forEachRemaining(row -> {
              List<String> missingValues = new ArrayList<>();
              row.forEach((key, value) -> {
                if (value == null || StringUtils.isBlank(value.toString())) {
                  missingValues.add(key.toString());
                }
              });

              if (!missingValues.isEmpty()) {
                String errorMsg = String.format("USER_INFO_MISSING_FORMAT", missingValues.size(), StringUtils.join(missingValues, ", "));
                validationErrors.add(errorMsg);
              } else {
                BigInteger recordId = idGenerator.nextId();
                FileDataDefinition fileUploadDataDefinition = FileDataDefinition.builder()
                  .id(recordId)
                  .userData(row)
                  .isConfirmed(false)
                  .createdOn(Instant.now())
                  .createdById(BigInteger.valueOf(Long.parseLong(loggedInUser.get(ID).toString())))
                  .updatedOn(Instant.now())
                  .updatedById(BigInteger.valueOf(Long.parseLong(loggedInUser.get(ID).toString())))
                  .build();
                this.fileUploadDataRepository.save(fileUploadDataDefinition);
              }
            });
          }
        }
      }
    }
    FileDataUploadResponse fileDataUploadResponse=new FileDataUploadResponse();
    if (!validationErrors.isEmpty()) {
      fileDataUploadResponse.setSuccess(false);
      fileDataUploadResponse.setErrors(validationErrors);
    } else {
      fileDataUploadResponse.setSuccess(true);
      fileDataUploadResponse.setErrors(List.of("FILE_UPLOAD_SUCCESS_MESSAGE"));
    }

    return fileDataUploadResponse;
  }

  @Override
  public List<FileUploadDataSchema> getAllRecords() {
    List<FileDataDefinition> dbRecords = fileUploadDataRepository.findAll();
    return dbRecords.stream()
      .map(this::convertToSchema)
      .collect(Collectors.toList());
  }

  private FileUploadDataSchema convertToSchema(FileDataDefinition dbRecord) {
    return this.objectMapper.convertValue(dbRecord, FileUploadDataSchema.class);
  }

  @Override
  public FileUploadDataSchema filterRecordsByMobileNumberOrEmpId(String mobileNumber, String empId) {
    return this.objectMapper.convertValue(fileUploadDataRepository.findByMobileNumberOrEmpId(mobileNumber, empId),FileUploadDataSchema.class);
  }

  @Override
  public FileUploadDataSchema updateRecord(String id, FileUploadDataSchema newData) {
    FileDataDefinition existingRecord = fileUploadDataRepository.findById(BigInteger.valueOf(Long.parseLong(id)))
      .orElseThrow(() -> new NoDataFoundException("Record not found with this id","Record not found with this id"));
    // Update the existing record with new data
    existingRecord.setUserData(newData.getUserData()); // Assuming userData is the field to update
    existingRecord.setIsConfirmed(newData.getIsConfirmed()); // Assuming isConfirmed is the field to update

    return this.objectMapper.convertValue(fileUploadDataRepository.save(existingRecord),FileUploadDataSchema.class);
  }


}
