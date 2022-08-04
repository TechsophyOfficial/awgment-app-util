package com.techsophy.tsf.util.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.util.dto.FileUploadResponse;
import com.techsophy.tsf.util.dto.FileUploadSchema;
import com.techsophy.tsf.util.dto.PaginationResponsePayload;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface FileUploadService
{

    FileUploadResponse uploadRecords(MultipartFile file,String type) throws IOException;

    FileUploadResponse updateStatus(FileUploadSchema fileUploadSchema) throws JsonProcessingException;

    List<FileUploadSchema> getAllRecords(Sort sort, String documentId);

    PaginationResponsePayload getAllRecords(Pageable pageable, String documentId);

    void deleteRecordById(String id, String documentId);

}
