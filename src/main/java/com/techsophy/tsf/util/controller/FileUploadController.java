package com.techsophy.tsf.util.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.util.dto.FileUploadResponse;
import com.techsophy.tsf.util.dto.FileUploadSchema;
import com.techsophy.tsf.util.model.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import static com.techsophy.tsf.util.constants.FileUploadConstants.*;
import static com.techsophy.tsf.util.constants.FileUploadConstants.ID;
import static com.techsophy.tsf.util.constants.PropertiesConstants.*;

@RequestMapping(BASE_URL+ VERSION_V1)
public interface FileUploadController
{

    @PostMapping(value = FILE_UPLOAD_URL,consumes={MediaType.MULTIPART_FORM_DATA_VALUE })
    @PreAuthorize(CREATE_OR_ALL_ACCESS)
    ApiResponse<FileUploadResponse> uploadFile(@RequestPart(value= FILE) MultipartFile file,@RequestParam String type) throws IOException;

    @PostMapping(FILE_STATUS_URL)
    @PreAuthorize(CREATE_OR_ALL_ACCESS)
    ApiResponse <FileUploadResponse> updateStatus(@RequestBody @Validated FileUploadSchema fileUploadSchema) throws JsonProcessingException;

    @GetMapping(FILE_URL)
    @PreAuthorize(READ_OR_ALL_ACCESS)
    ApiResponse<Void> getAllRecords(@RequestParam(value = PAGE, required = false) Integer page,
                                    @RequestParam(value = SIZE, required = false) Integer pageSize,
                                    @RequestParam(value = SORT_BY, required = false) String[] sortBy,
                                    @RequestParam(value =DOCUMENT_ID, required = false) String documentId);


    @DeleteMapping(DELETE_BY_ID)
    @PreAuthorize(DELETE_OR_ALL_ACCESS)
    ApiResponse<Void> deleteRecordById(@RequestParam(value =ID, required = false) String id,
                                       @RequestParam(value =DOCUMENT_ID, required = false) String documentId);
}
