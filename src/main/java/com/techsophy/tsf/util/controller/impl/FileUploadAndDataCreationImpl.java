package com.techsophy.tsf.util.controller.impl;

import com.techsophy.tsf.util.config.GlobalMessageSource;
import com.techsophy.tsf.util.controller.FileUploadAndDataCreation;
import com.techsophy.tsf.util.dto.FileDataUploadResponse;
import com.techsophy.tsf.util.dto.FileUploadDataSchema;
import com.techsophy.tsf.util.entity.FileDataDefinition;
import com.techsophy.tsf.util.model.ApiResponse;
import com.techsophy.tsf.util.service.FileUploadAndDataCreationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

import static com.techsophy.tsf.util.constants.FileUploadConstants.FILE_UPLOAD_SUCCESS;

@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class FileUploadAndDataCreationImpl implements FileUploadAndDataCreation {

  private final FileUploadAndDataCreationService fileUploadAndDataCreationService;
  private final GlobalMessageSource globalMessageSource;

  @Override
  public ApiResponse<FileDataUploadResponse> uploadFile(MultipartFile file) throws IOException {
    FileDataUploadResponse fileUploadResponse = fileUploadAndDataCreationService.uploadRecords(file);
    return new ApiResponse<>(fileUploadResponse,true,globalMessageSource.get(FILE_UPLOAD_SUCCESS));
  }

  @Override
  public ApiResponse<List<FileUploadDataSchema>> getAllRecords() {
    return new ApiResponse<>(fileUploadAndDataCreationService.getAllRecords(),true,globalMessageSource.get("Fetched All The Records SuccessFully"));
  }

  @Override
  public ApiResponse<FileUploadDataSchema> filterRecordsByMobileNumberOrEmpId(String mobileNumber, String empId) {
    return new ApiResponse<>(fileUploadAndDataCreationService.filterRecordsByMobileNumberOrEmpId(mobileNumber, empId),true,globalMessageSource.get("Fetched All The Records SuccessFully"));
  }

  @Override
  public ApiResponse<FileUploadDataSchema> updateRecord(String recordId, FileUploadDataSchema newData) {
    return new ApiResponse<>(fileUploadAndDataCreationService.updateRecord(recordId, newData),true,globalMessageSource.get("Fetched All The Records SuccessFully"));
  }
}
