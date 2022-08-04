package com.techsophy.tsf.util.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.techsophy.tsf.util.config.GlobalMessageSource;
import com.techsophy.tsf.util.exception.FileNameNotPresentException;
import com.techsophy.tsf.util.exception.UnsupportedFileFormatException;
import com.techsophy.tsf.util.service.impl.CSVtoJsonServiceImpl;
import com.techsophy.tsf.util.utils.TokenUtils;
import com.techsophy.tsf.util.utils.WebClientWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.techsophy.tsf.util.constants.FileUploadTestConstants.*;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.TEST_ACTIVE_PROFILE;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ContextConfiguration
@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CSVtoJsonServiceImplTest {

    @Mock
    GlobalMessageSource mockGlobalMessageSource;
    @Mock
    TokenUtils tokenUtils;
    @Mock
    ObjectMapper mockObjectMapper;
    @Mock
    WebClientWrapper mockWebClientWrapper;

    @InjectMocks
    CSVtoJsonServiceImpl csVtoJsonService;

    @Test
    void convertToJsonTest() throws IOException {
        Map map = new HashMap();
        map.put(KEY, VALUE);
        String path = "src/test/resources/testdata/accounts_template_Id.csv";
        String str = Files.readString(Paths.get(path));
        Mockito.when(tokenUtils.getTokenFromContext()).thenReturn(TOKEN);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        ContentDisposition contentDisposition = ContentDisposition.builder("inline")
                .filename("Filename.csv")
                .build();
        headers.setContentDisposition(contentDisposition);
        ResponseEntity<String> data = new ResponseEntity<>(str, headers, 200);
        Mockito.when(mockWebClientWrapper.webclientRequestwithHeaderResponse(any(), any(), any())).thenReturn(data);
        TypeFactory typeFactory = TypeFactory.defaultInstance();
        JavaType javaType = mockObjectMapper.constructType(JavaType.class);
        Mockito.when(mockObjectMapper.getTypeFactory()).thenReturn(typeFactory);
        Mockito.when(mockObjectMapper.convertValue(any(), any(CollectionType.class))).thenReturn(java.util.List.of(map));
        java.util.List<Map<String, Object>> response = csVtoJsonService.convertToJson("1");
        assertThat(response).isInstanceOf(java.util.List.class);
    }

    @Test
    void convertToJsonExceptionTest() throws IOException {
        Map map = new HashMap();
        map.put(KEY, VALUE);
        String path = "src/test/resources/testdata/accounts_template_Id.csv";
        String str = Files.readString(Paths.get(path));
        Mockito.when(tokenUtils.getTokenFromContext()).thenReturn(TOKEN);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        ContentDisposition contentDisposition = ContentDisposition.builder("inline")
                .filename("Filename.ab")
                .build();
        headers.setContentDisposition(contentDisposition);
        ResponseEntity<String> data = new ResponseEntity<>(str, headers, 200);
        Mockito.when(mockWebClientWrapper.webclientRequestwithHeaderResponse(any(), any(), any())).thenReturn(data);
        Assertions.assertThrows(UnsupportedFileFormatException.class,()->csVtoJsonService.convertToJson("1"));
    }

    @Test
    void convertToJsonFileNameNotPresentExceptionTest() throws IOException {
        Map map = new HashMap();
        map.put(KEY, VALUE);
        String path = "src/test/resources/testdata/accounts_template_Id.csv";
        String str = Files.readString(Paths.get(path));
        Mockito.when(tokenUtils.getTokenFromContext()).thenReturn(TOKEN);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        ContentDisposition contentDisposition = ContentDisposition.builder("inline")
                .filename("abc.ab")
                .build();
        ResponseEntity<String> data = new ResponseEntity<>(str, headers, 200);
        Mockito.when(mockWebClientWrapper.webclientRequestwithHeaderResponse(any(), any(), any())).thenReturn(data);
        Assertions.assertThrows(FileNameNotPresentException.class,()->csVtoJsonService.convertToJson("1"));
    }
}
