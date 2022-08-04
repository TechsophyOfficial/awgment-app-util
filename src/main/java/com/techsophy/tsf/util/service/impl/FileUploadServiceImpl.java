package com.techsophy.tsf.util.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.techsophy.idgenerator.IdGeneratorImpl;
import com.techsophy.tsf.util.config.GlobalMessageSource;
import com.techsophy.tsf.util.dto.FileUploadResponse;
import com.techsophy.tsf.util.dto.FileUploadSchema;
import com.techsophy.tsf.util.dto.FileData;
import com.techsophy.tsf.util.dto.PaginationResponsePayload;
import com.techsophy.tsf.util.entity.FileUploadDefinition;
import com.techsophy.tsf.util.exception.*;
import com.techsophy.tsf.util.repository.FileUploadDefinitionRepository;
import com.techsophy.tsf.util.service.FileUploadService;
import com.techsophy.tsf.util.utils.TokenUtils;
import com.techsophy.tsf.util.utils.UserDetails;
import com.techsophy.tsf.util.utils.WebClientWrapper;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigInteger;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static com.techsophy.tsf.util.constants.FileUploadConstants.CSV;
import static com.techsophy.tsf.util.constants.FileUploadConstants.ID;
import static com.techsophy.tsf.util.constants.FileUploadConstants.*;
import static com.techsophy.tsf.util.constants.PropertiesConstants.*;
import static com.techsophy.tsf.util.constants.QRCodeConstants.POST;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class FileUploadServiceImpl implements FileUploadService
{
    @Value(GATEWAY_URI)
    private final String gatewayUrl;

    private final ObjectMapper objectMapper;
    private final GlobalMessageSource globalMessageSource;
    private final IdGeneratorImpl idGenerator;
    private final UserDetails userDetails;
    private final TokenUtils tokenUtils;
    private final WebClientWrapper webClientWrapper;

    private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);
    public static final Map<String, String> SUPPORTED_TYPES = Map.of(
            CSV, MediaType.TEXT_PLAIN_VALUE);
    private final FileUploadDefinitionRepository fileUploadDefinitionRepository;

    @Override
    public FileUploadResponse uploadRecords(MultipartFile file, String type) throws IOException
    {
            BigInteger documentId= idGenerator.nextId();
            AtomicReference<String> existingDocumentId = new AtomicReference<>();
            Map<String,Object> loggedInUser = userDetails.getUserDetails().get(0);
            logger.info(FILE_VALIDATIONS_GOING_ON);

            if (file.isEmpty())
            {
                throw new NoDataFoundException(PLEASE_SELECT_CSV_FILE_WITH_DATA,globalMessageSource.get(PLEASE_SELECT_CSV_FILE_WITH_DATA));
            }

            logger.info(UPLOADING_CSV_FILE, file.getOriginalFilename());
            String fileName = file.getOriginalFilename();
            if (StringUtils.isBlank(fileName) || StringUtils.isEmpty(fileName))
            {
                throw new FileNameNotPresentException(CSV_FILE_NAME_NOT_EMPTY,globalMessageSource.get(CSV_FILE_NAME_NOT_EMPTY));
            }

            checkValidFileName(fileName);
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            if (SUPPORTED_TYPES.containsKey(fileExtension))
            {
                CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
                CsvMapper csvMapper = new CsvMapper();
                List<?> dynamicContent = csvMapper.readerFor(Map.class).with(csvSchema).readValues(file.getBytes()).readAll();

                dynamicContent.stream().forEach(content ->
                {
                    FileUploadDefinition fileUploadDefinition = new FileUploadDefinition();
                    LinkedHashMap<String, Object> rowDetails= (LinkedHashMap<String, Object>) content;
                    FileUploadDefinition existingFileDefinition=new FileUploadDefinition();
                    Set<String> keys = rowDetails.keySet();
                    Collection<Object> userValues = rowDetails.values();
                    List<Object> valuesList = new ArrayList<>(userValues);
                    valuesList.stream().forEach(value -> {
                                if (value.equals(EMPTY)){
                                    String errorMsg = USER_INFO_MISSING + (dynamicContent.indexOf(content) + 1) + ROW_AND + (valuesList.indexOf(value) + 1) + COLUMN_IN_FILE_UPLOAD;
                                    throw new InvalidInputException(errorMsg,errorMsg);
                                }
                            });

                    if (!keys.contains(ID))
                    {
                        BigInteger recordId=idGenerator.nextId();
                        fileUploadDefinition = FileUploadDefinition.builder().id(recordId).userData(rowDetails).documentId(documentId).status(CREATED).createdOn(Instant.now())
                                .createdById(BigInteger.valueOf(Long.parseLong(loggedInUser.get(ID).toString())))
                                .createdByName(loggedInUser.get(USER_DEFINITION_FIRST_NAME) + SPACE + loggedInUser.get(USER_DEFINTION_LAST_NAME))
                                .updatedOn(Instant.now()).updatedById(BigInteger.valueOf(Long.parseLong(loggedInUser.get(ID).toString())))
                                .updatedByName(loggedInUser.get(USER_DEFINITION_FIRST_NAME)+SPACE+loggedInUser.get(USER_DEFINTION_LAST_NAME)).build();
                    }
                    else
                    {
                        existingFileDefinition=this.fileUploadDefinitionRepository.findById(BigInteger.valueOf(Long.parseLong(rowDetails.get(ID).toString())))
                                .orElseThrow(() -> new UploadedUserNotFoundException(CANNOT_UPDATE_RECORD_WITH_GIVEN_ID,globalMessageSource.get(CANNOT_UPDATE_RECORD_WITH_GIVEN_ID, rowDetails.get(ID).toString())));
                        existingDocumentId.set(existingFileDefinition.getDocumentId().toString());

                        fileUploadDefinition = FileUploadDefinition.builder().id(existingFileDefinition.getId()).userData(rowDetails).documentId(existingFileDefinition.getDocumentId()).status(UPDATED).createdOn(existingFileDefinition.getCreatedOn())
                                .createdById(existingFileDefinition.getCreatedById())
                                .createdByName(existingFileDefinition.getCreatedByName())
                                .updatedOn(Instant.now()).updatedById(BigInteger.valueOf(Long.parseLong(loggedInUser.get(ID).toString())))
                                .updatedByName(loggedInUser.get(USER_DEFINITION_FIRST_NAME)+SPACE+loggedInUser.get(USER_DEFINTION_LAST_NAME)).build();
                    }
                    FileData fileData = this.objectMapper.convertValue(fileUploadDefinition, FileData.class);
                    FileUploadDefinition fileUploadDefinition1 = this.objectMapper.convertValue(fileData, FileUploadDefinition.class);
                    this.fileUploadDefinitionRepository.save(fileUploadDefinition1);
                });

                Map<String, String> usersMap = new HashMap<>();
                String deploymentIdConvertedToString=String.valueOf(documentId);
                usersMap.put(TO,loggedInUser.get(EMAIL_ID).toString());
                usersMap.put(DOCUMENT_ID,deploymentIdConvertedToString);
                usersMap.put(TYPE,type);
                Map<String, Object> payload = new HashMap<>();
                payload.put(PROCESS_DEFINITION_KEY, PROCESS_ID);
                payload.put(BUSINESS_KEY,file.getOriginalFilename());
                payload.put(VARIABLES, usersMap);
                webClientWrapper.webclientRequest(tokenUtils.getTokenFromContext(), gatewayUrl + WORKFLOW_START_URL, POST, payload);
            }
            else
            {
                throw new InvalidInputException(UPLOAD_CSV_FILE_EXTENSION_ONLY,globalMessageSource.get(UPLOAD_CSV_FILE_EXTENSION_ONLY));
            }
            if(existingDocumentId.get()!=null&&(!existingDocumentId.get().equalsIgnoreCase("")))
            {
                return new FileUploadResponse(String.valueOf(existingDocumentId.get()));
            }
            return  new FileUploadResponse(String.valueOf(documentId));
        }

    @Override
    public FileUploadResponse updateStatus(FileUploadSchema fileUploadSchema) throws JsonProcessingException
    {
        Map<String,Object> loggedInUser = userDetails.getUserDetails().get(0);
        FileUploadDefinition existingFileDefinition=this.fileUploadDefinitionRepository.findById(BigInteger.valueOf(Long.parseLong(fileUploadSchema.getId()))).orElseThrow(() -> new UploadedUserNotFoundException(CANNOT_UPDATE_RECORD_WITH_GIVEN_ID,globalMessageSource.get(CANNOT_UPDATE_RECORD_WITH_GIVEN_ID, fileUploadSchema.getId())));
        existingFileDefinition.setStatus(fileUploadSchema.getStatus());
        existingFileDefinition.setUpdatedOn(Instant.now());
        existingFileDefinition.setUpdatedById(BigInteger.valueOf(Long.parseLong(loggedInUser.get(ID).toString())));
        existingFileDefinition.setUpdatedByName(loggedInUser.get(USER_DEFINITION_FIRST_NAME)+SPACE+loggedInUser.get(USER_DEFINTION_LAST_NAME));
        this.fileUploadDefinitionRepository.save(existingFileDefinition);
        return new FileUploadResponse(String.valueOf(existingFileDefinition.getDocumentId()));
    }

    @Override
    public List<FileUploadSchema> getAllRecords(Sort sort, String documentId) {
        if(StringUtils.isNotEmpty(documentId))
        {
            Stream<FileUploadDefinition> userDefinitionStream=this.fileUploadDefinitionRepository.findAllByDocumentId(BigInteger.valueOf(Long.parseLong(documentId)),sort)
                    .orElseThrow(() -> new UploadedUserNotFoundException(CANNOT_UPDATE_RECORD_WITH_GIVEN_ID,globalMessageSource.get(CANNOT_FIND_RECORD_WITH_GIVEN_DOCUMENT_ID, documentId)));
            return userDefinitionStream.map(this::convertFileUploadEntityToDTO).collect(Collectors.toList());
        }
        return this.fileUploadDefinitionRepository.findAll(sort).stream().map(this::convertFileUploadEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public PaginationResponsePayload getAllRecords(Pageable pageable, String documentId)
    {
        Page<FileUploadDefinition> fileUploadDefinitionPage = null;
        if(StringUtils.isNotEmpty(documentId))
        {
            fileUploadDefinitionPage =this.fileUploadDefinitionRepository.findAllByDocumentId(BigInteger.valueOf(Long.parseLong(documentId)),pageable)
                    .orElseThrow(() -> new UploadedUserNotFoundException(CANNOT_FIND_RECORD_WITH_GIVEN_DOCUMENT_ID,globalMessageSource.get(CANNOT_FIND_RECORD_WITH_GIVEN_DOCUMENT_ID, documentId)));
        }
        else {
            fileUploadDefinitionPage = this.fileUploadDefinitionRepository.findAll(pageable);
        }
        List<Map<String,Object>> fileUploadSchemaList=fileUploadDefinitionPage.stream().map(this::convertFileUploadEntityToMap).collect(Collectors.toList());
        return tokenUtils.getPaginationResponsePayload(fileUploadDefinitionPage,fileUploadSchemaList);
    }

    @Override
    public void deleteRecordById(String id, String documentId) {
        if(StringUtils.isNotEmpty(documentId))
        {
            this.fileUploadDefinitionRepository.deleteAll(BigInteger.valueOf(Long.parseLong(documentId)));
        }
        else
        {
            if (!fileUploadDefinitionRepository.existsById(BigInteger.valueOf(Long.parseLong(id)))) {
                throw new UploadedUserNotFoundException(USER_NOT_FOUND_BY_ID, globalMessageSource.get(USER_NOT_FOUND_BY_ID, id));
            }
            this.fileUploadDefinitionRepository.deleteById(BigInteger.valueOf(Long.parseLong(id)));
        }
    }

    public FileUploadSchema convertFileUploadEntityToDTO(FileUploadDefinition fileUploadDefinition)
    {
        return  this.objectMapper.convertValue(fileUploadDefinition, FileUploadSchema.class);
    }

    public Map<String,Object> convertFileUploadEntityToMap(FileUploadDefinition fileUploadDefinition)
    {
        return this.objectMapper.convertValue(fileUploadDefinition,Map.class);
    }

    private void checkValidFileName(String fileName)
    {
        if (!fileName.contains(DOT))
            throw new InvalidDataException(INVALID_FILE_NAME_OR_EXTENSION,globalMessageSource.get(INVALID_FILE_NAME_OR_EXTENSION));
        if (fileName.split(REGEX_SPLIT).length > 2)
            throw new InvalidDataException(INVALID_FILE_NAME_OR_EXTENSION,globalMessageSource.get(INVALID_FILE_NAME_OR_EXTENSION));
    }


}


