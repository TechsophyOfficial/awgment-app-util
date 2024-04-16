package com.techsophy.tsf.util.service;

import com.techsophy.tsf.util.dto.FileDataUploadResponse;
import com.techsophy.tsf.util.dto.FileUploadDataSchema;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface FileUploadAndDataCreationService {
  FileDataUploadResponse uploadRecords(MultipartFile file) throws IOException;

  List<FileUploadDataSchema> getAllRecords();

  FileUploadDataSchema filterRecordsByMobileNumberOrEmpId(String mobileNumber, String empId);

  FileUploadDataSchema updateRecord(String id, FileUploadDataSchema newData);

}
