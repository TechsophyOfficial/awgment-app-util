package com.techsophy.tsf.util.controller;

import com.techsophy.tsf.util.model.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.techsophy.tsf.util.constants.CSVtoJsonConstants.CONVERT_URL;
import static com.techsophy.tsf.util.constants.CSVtoJsonConstants.DOCUMENT_ID;
import static com.techsophy.tsf.util.constants.PropertiesConstants.CREATE_OR_ALL_ACCESS;
import static com.techsophy.tsf.util.constants.QRCodeConstants.*;

@RequestMapping(BASE_URL+VERSION_V1)
public interface CSVtoJsonController
{
    @GetMapping(CONVERT_URL)
    @PreAuthorize(CREATE_OR_ALL_ACCESS)
    ApiResponse<List<Map<String,Object>>> convertToJson(@PathVariable(DOCUMENT_ID) String documentId) throws IOException;

}
