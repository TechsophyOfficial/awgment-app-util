package com.techsophy.tsf.util.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.techsophy.tsf.util.constants.FileUploadTestConstants.VALUE;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.POST;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.TEST_ACTIVE_PROFILE;
import static com.techsophy.tsf.util.constants.QRCodeConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(TEST_ACTIVE_PROFILE)
class WebClientWrapperTest
{
    @Mock
    ObjectMapper objectMapper;

    @Mock
    WebClient webClientMock;

    @InjectMocks
    WebClientWrapper webClientWrapper;


    @BeforeEach
    public void init() {
        WebClient.RequestBodyUriSpec requestBodyUriMock = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersMock = mock(WebClient.RequestHeadersSpec.class);
        WebClient.RequestBodySpec requestBodyMock = mock(WebClient.RequestBodySpec.class);
        WebClient.ResponseSpec responseMock = mock(WebClient.ResponseSpec.class);
        when(webClientMock.get()).thenReturn(requestHeadersUriSpec);
        when(webClientMock.method(HttpMethod.DELETE)).thenReturn(requestBodyUriMock);
        when(webClientMock.post()).thenReturn(requestBodyUriMock);
        when(webClientMock.put()).thenReturn(requestBodyUriMock);
        when(requestBodyUriMock.uri(LOCAL_HOST_URL)).thenReturn(requestBodyMock);
        when(requestHeadersUriSpec.uri(LOCAL_HOST_URL)).thenReturn(requestHeadersMock);
        when(requestHeadersMock.header(any(), any())).thenReturn(requestHeadersMock);
        when(requestBodyMock.header(any(), any())).thenReturn(requestBodyMock);
        when(requestBodyMock.bodyValue(TOKEN)).thenReturn(requestHeadersMock);
        when(requestBodyMock.retrieve()).thenReturn(responseMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.onStatus(any(), any())).thenReturn(responseMock);
        when(responseMock.bodyToMono(String.class))
                .thenReturn(Mono.just(TEST));
        when(responseMock.toEntity(String.class))
                .thenReturn(Mono.just(ResponseEntity.of(Optional.of(TEST))));
    }


    @Order(1)
    @Test
    void getWebClientRequestTest()
    {
        String getResponse= webClientWrapper.webclientRequest(TOKEN,LOCAL_HOST_URL, GET,null);
        assertEquals(TEST,getResponse);
        String putResponse= webClientWrapper.webclientRequest(TOKEN,LOCAL_HOST_URL,PUT,TOKEN);
        assertEquals(TEST,putResponse);
        String deleteResponse= webClientWrapper.webclientRequest(TOKEN,LOCAL_HOST_URL,DELETE,null);
        assertEquals(TEST,deleteResponse);
        String deleteBodyResponse= webClientWrapper.webclientRequest(TOKEN,LOCAL_HOST_URL,DELETE,TOKEN);
        assertEquals(TEST,deleteBodyResponse);
        String postResponse= webClientWrapper.webclientRequest(TOKEN,LOCAL_HOST_URL,POST,TOKEN);
        assertEquals(TEST,postResponse);
    }

    @Test
    void webclientRequestwithHeaderResponseTest(){
        ResponseEntity<String> getResponse = webClientWrapper.webclientRequestwithHeaderResponse(TOKEN,LOCAL_HOST_URL, GET);
        Assertions.assertNotNull(getResponse);
    }

//    @Test
//    void availableMethodTest() throws JsonProcessingException {
//        Exception ex= new Exception("exception");
//        Map<String,Object> map = new HashMap<>();
//        map.put(KEY,VALUE);
//        Mockito.when(this.objectMapper.readValue("abc", Map.class)).thenReturn(map);
//        Assertions.assertThrows(NullPointerException.class,()->webClientWrapper.availableMethod(ex));
//    }
}
