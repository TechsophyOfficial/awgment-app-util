package com.techsophy.tsf.util.controller;

import com.techsophy.tsf.util.dto.FileDataUploadResponse;
import com.techsophy.tsf.util.dto.FileUploadDataSchema;
import com.techsophy.tsf.util.entity.FileDataDefinition;
import com.techsophy.tsf.util.model.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

import static com.techsophy.tsf.util.constants.FileUploadConstants.*;
import static com.techsophy.tsf.util.constants.PropertiesConstants.*;

@RequestMapping(BASE_URL+ VERSION_V1)
public interface FileUploadAndDataCreation {

  @PostMapping(value = FILE_UPLOAD_DATA,consumes={MediaType.MULTIPART_FORM_DATA_VALUE })
  @PreAuthorize(CREATE_OR_ALL_ACCESS)
  ApiResponse<FileDataUploadResponse> uploadFile(@RequestPart(value= FILE) MultipartFile file) throws IOException;

  @GetMapping(RECORDS)
  ApiResponse<List<FileUploadDataSchema>> getAllRecords();

  @GetMapping(RECORDS+"/"+FILTER)
  public ApiResponse<FileUploadDataSchema> filterRecordsByMobileNumberOrEmpId(
    @RequestParam(required = false) String mobileNumber,
    @RequestParam(required = false) String empId );

  @PostMapping("/records/{recordId}")
  public ApiResponse<FileUploadDataSchema> updateRecord(@PathVariable String recordId, @RequestBody FileUploadDataSchema newData);

  }
