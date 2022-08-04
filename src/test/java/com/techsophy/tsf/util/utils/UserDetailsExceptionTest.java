package com.techsophy.tsf.util.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.util.config.GlobalMessageSource;
import com.techsophy.tsf.util.exception.InvalidInputException;
import com.techsophy.tsf.util.exception.UserDetailsIdNotFoundException;
import com.techsophy.tsf.util.model.ApiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.TEST_ACTIVE_PROFILE;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.TEST_TOKEN;
import static com.techsophy.tsf.util.constants.QRCodeConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ActiveProfiles(TEST_ACTIVE_PROFILE)
@SpringBootTest
class UserDetailsExceptionTest
{
    @Mock
    GlobalMessageSource mockGlobalMessageSource;
    @Mock
    TokenUtils mockTokenUtils;
    @Mock
    ObjectMapper mockObjectMapper;
    @Mock
    WebClientWrapper mockWebClientWrapper;
    @InjectMocks
    UserDetails mockUserDetails;

    @Test
    void getUserDetailsTest() throws JsonProcessingException
    {
        ApiResponse apiResponse=new ApiResponse(null,true,USER_DETAILS_RETRIEVED_SUCCESS);
        Mockito.when(mockTokenUtils.getLoggedInUserId()).thenReturn(EMPTY_STRING);
        Mockito.when(mockTokenUtils.getTokenFromContext()).thenReturn(TEST_TOKEN);
        Mockito.when(mockObjectMapper.readValue(anyString(),(TypeReference<ApiResponse>) any()))
                .thenReturn(apiResponse);
        Assertions.assertThrows(InvalidInputException.class, () ->
                mockUserDetails.getUserDetails());
    }

    @Test
    void getUserDetailsNotFoundExceptionTest() throws JsonProcessingException
    {
        ApiResponse apiResponse=new ApiResponse(null,true,USER_DETAILS_RETRIEVED_SUCCESS);
        Mockito.when(mockTokenUtils.getLoggedInUserId()).thenReturn("847117072898674688");
        Mockito.when(mockTokenUtils.getTokenFromContext()).thenReturn(TEST_TOKEN);
        Mockito.when(mockObjectMapper.readValue(anyString(),(TypeReference<ApiResponse>) any()))
                .thenReturn(apiResponse);
        Assertions.assertThrows(UserDetailsIdNotFoundException.class, () ->
                mockUserDetails.getUserDetails());
    }
}
