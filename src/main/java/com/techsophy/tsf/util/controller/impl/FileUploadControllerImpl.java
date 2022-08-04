package com.techsophy.tsf.util.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.util.config.GlobalMessageSource;
import com.techsophy.tsf.util.controller.FileUploadController;
import com.techsophy.tsf.util.dto.FileUploadResponse;
import com.techsophy.tsf.util.dto.FileUploadSchema;
import com.techsophy.tsf.util.model.ApiResponse;
import com.techsophy.tsf.util.service.FileUploadService;
import com.techsophy.tsf.util.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import static com.techsophy.tsf.util.constants.FileUploadConstants.*;

@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class FileUploadControllerImpl implements FileUploadController
{

    private final FileUploadService fileUploadService;
    private final GlobalMessageSource globalMessageSource;
    private final TokenUtils tokenUtils;

    @Override
    public ApiResponse<FileUploadResponse> uploadFile(MultipartFile file, String type) throws IOException
    {
        FileUploadResponse fileUploadResponse = fileUploadService.uploadRecords(file,type);
        return new ApiResponse<>(fileUploadResponse,true,globalMessageSource.get(FILE_UPLOAD_SUCCESS));
    }

    @Override
    public ApiResponse<FileUploadResponse> updateStatus(FileUploadSchema fileUploadSchema) throws JsonProcessingException
    {
        FileUploadResponse fileUploadResponse = fileUploadService.updateStatus(fileUploadSchema);
        return new ApiResponse<>(fileUploadResponse,true,globalMessageSource.get(STATUS_UPDATE_SUCCESS));
    }

    @Override
    public ApiResponse getAllRecords(Integer page, Integer pageSize, String[] sortBy, String documentId)
    {
        if(page==null)
        {
            return new ApiResponse<>(fileUploadService.getAllRecords( tokenUtils.getSortBy(sortBy), documentId),true,globalMessageSource.get(GET_ALL_RECORDS_SUCCESS));
        }
        return new ApiResponse<>(fileUploadService.getAllRecords(tokenUtils.getPageRequest(page,pageSize,sortBy), documentId),true,globalMessageSource.get(GET_ALL_RECORDS_SUCCESS));
    }

    @Override
    public ApiResponse<Void> deleteRecordById(String id, String documentId)
    {
        fileUploadService.deleteRecordById(id,documentId);
        return new ApiResponse<>(null,true,globalMessageSource.get(DELETE_RECORD_SUCCESS));
    }
}
