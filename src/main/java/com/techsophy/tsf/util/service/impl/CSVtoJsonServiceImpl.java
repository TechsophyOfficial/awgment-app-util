package com.techsophy.tsf.util.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.techsophy.tsf.util.config.GlobalMessageSource;
import com.techsophy.tsf.util.exception.FileNameNotPresentException;
import com.techsophy.tsf.util.exception.InvalidInputException;
import com.techsophy.tsf.util.exception.UnsupportedFileFormatException;
import com.techsophy.tsf.util.service.CSVtoJsonService;
import com.techsophy.tsf.util.utils.TokenUtils;
import com.techsophy.tsf.util.utils.WebClientWrapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static com.techsophy.tsf.util.constants.CSVtoJsonConstants.*;
import static com.techsophy.tsf.util.constants.ErrorConstants.*;
import static com.techsophy.tsf.util.constants.FileUploadConstants.CSV_FILE_NAME_NOT_EMPTY;
import static com.techsophy.tsf.util.constants.PropertiesConstants.GATEWAY_URI;
import static com.techsophy.tsf.util.constants.PropertiesConstants.GET;

@Service
//@AllArgsConstructor(onConstructor_ = {@Autowired})
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CSVtoJsonServiceImpl implements CSVtoJsonService
{
    @Value(GATEWAY_URI)
    private final String gatewayUrl;

    private final WebClientWrapper webClientWrapper;

    private final TokenUtils tokenUtils;

    private final ObjectMapper objectMapper;

    private final GlobalMessageSource globalMessageSource;

    private static final Logger logger = LoggerFactory.getLogger(CSVtoJsonServiceImpl.class);

    @Override
    public List<Map<String,Object>> convertToJson(String documentId) throws IOException {

        //  To get the token
        String token = tokenUtils.getTokenFromContext();

        logger.info(FETCHING_DOCUMENT_FROM_DMS);

        //  Calling external API with header response
        ResponseEntity<String> response = webClientWrapper.webclientRequestwithHeaderResponse(token, gatewayUrl+DOCUMENT_DOWNLOAD_URI+documentId, GET);

        // Check the header for csv file
        String result = response.getHeaders().getContentDisposition().getFilename();
        if(result != null) {
            if (result.contains(CSV)) {

                logger.info(CONVERSION_STARTED);

                //  Code to convert csv to json
                CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
                CsvMapper csvMapper = new CsvMapper();
                String body = response.getBody();
                if(body != null) {
                    List<Object> dynamicContent = csvMapper.readerFor(Map.class).with(csvSchema).readValues(body.getBytes()).readAll();

                    logger.info(CONVERSION_COMPLETED);

                    return objectMapper.convertValue(dynamicContent, objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
                }
                else {
                    throw new InvalidInputException(DATA_NOT_FOUND_WITH_GIVEN_ID,globalMessageSource.get(DATA_NOT_FOUND_WITH_GIVEN_ID));
                }
            }
            else {
                //  If file is not csv
                throw new UnsupportedFileFormatException(UNSUPPORTED_FILE_FORMAT, UNSUPPORTED_FILE_FORMAT);
            }
        }
        else{
            throw new FileNameNotPresentException(CSV_FILE_NAME_NOT_EMPTY,globalMessageSource.get(CSV_FILE_NAME_NOT_EMPTY));
        }
    }
}