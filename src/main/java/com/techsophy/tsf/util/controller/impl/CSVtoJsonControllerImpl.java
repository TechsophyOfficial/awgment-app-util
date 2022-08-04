package com.techsophy.tsf.util.controller.impl;

import com.techsophy.tsf.util.controller.CSVtoJsonController;
import com.techsophy.tsf.util.model.ApiResponse;
import com.techsophy.tsf.util.service.CSVtoJsonService;
import com.techsophy.tsf.util.service.impl.FileUploadServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.techsophy.tsf.util.constants.CSVtoJsonConstants.CONVERTION_SUCCESS;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class CSVtoJsonControllerImpl implements CSVtoJsonController {
    @Autowired
    CSVtoJsonService csVtoJsonService;

    @Override
    public ApiResponse<List<Map<String, Object>>> convertToJson(String documentId) throws IOException {
        return new ApiResponse<>(csVtoJsonService.convertToJson(documentId),true,CONVERTION_SUCCESS);

    }
}
