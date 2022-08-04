package com.techsophy.tsf.util.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.util.config.CustomFilter;
import com.techsophy.tsf.util.constants.PropertiesConstants;
import com.techsophy.tsf.util.dto.PropertiesMap;
import com.techsophy.tsf.util.dto.PropertiesResponse;
import com.techsophy.tsf.util.dto.PropertiesResponseSchema;
import com.techsophy.tsf.util.dto.PropertiesSchema;
import com.techsophy.tsf.util.exception.*;
import com.techsophy.tsf.util.service.impl.PropertiesServiceImpl;
import com.techsophy.tsf.util.utils.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.techsophy.tsf.util.constants.ErrorConstants.*;
import static com.techsophy.tsf.util.constants.PropertiesConstants.*;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.*;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.BASE_URL;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.TOKEN;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.VERSION_V1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc(addFilters = false)
class PropertiesControllerExceptionTest
{
    @MockBean
    TokenUtils mockTokenUtils;
    @MockBean
    PropertiesServiceImpl mockPropertiesServiceImpl;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    CustomFilter customFilter;
    @Mock
    private PropertiesController mockPropertiesController;

    @BeforeEach
    public void setUp()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(new GlobalExceptionHandler(),mockPropertiesController).addFilters(customFilter).build();
    }

    @Test
    void entityIdNotFoundExceptionTest() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockPropertiesController.deletePropertiesByProjectName(TEST_PROJECT_NAME)).thenThrow(new EntityIdNotFoundException(ENTITY_ID_NOT_FOUND_EXCEPTION,ENTITY_ID_NOT_FOUND_EXCEPTION));
        RequestBuilder requestBuilderTest=MockMvcRequestBuilders
                .delete(BASE_URL+ VERSION_V1+PROPERTIES_BY_PROJECT_NAME).param(PROJECT_NAME,TEST_PROJECT_NAME)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isInternalServerError()).andReturn();
        assertEquals(500,mvcResult.getResponse().getStatus());
    }

    @Test
    void userDetailsNotFoundExceptionTest() throws Exception
    {
        ObjectMapper objectMapperTest=new ObjectMapper();
        PropertiesResponse propertiesResponse=new PropertiesResponse(TEST_PROJECT_NAME);
        PropertiesMap propertiesMap=new PropertiesMap();
        propertiesMap.setCategory(TEST_CATEGORY);
        propertiesMap.setKey(TEST_KEY);
        propertiesMap.setValue(TEST_VALUE);
        PropertiesSchema propertiesSchema=new PropertiesSchema(TEST_ID,TEST_PROJECT_NAME, List.of(propertiesMap));
        Mockito.when(mockPropertiesController.saveProperties(propertiesSchema)).thenThrow(new UserDetailsIdNotFoundException(LOGGED_IN_USER_ID_NOT_FOUND_EXCEPTION,LOGGED_IN_USER_ID_NOT_FOUND_EXCEPTION));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.post(PropertiesConstants.BASE_URL+ PropertiesConstants.VERSION_V1+PROPERTIES_URL)
                .content(objectMapperTest.writeValueAsString(propertiesSchema))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isInternalServerError()).andReturn();
        assertEquals(500,mvcResult.getResponse().getStatus());
    }

    @Test
    void projectNameNotFoundExceptionTest() throws Exception
    {
        ObjectMapper objectMapperTest=new ObjectMapper();
        PropertiesResponse propertiesResponse=new PropertiesResponse(TEST_PROJECT_NAME);
        PropertiesMap propertiesMap=new PropertiesMap();
        propertiesMap.setCategory(TEST_CATEGORY);
        propertiesMap.setKey(TEST_KEY);
        propertiesMap.setValue(TEST_VALUE);
        PropertiesResponseSchema propertiesResponseSchema=new PropertiesResponseSchema(
                TEST_ID,TEST_PROJECT_NAME, List.of(propertiesMap),TEST_CREATED_BY_ID_VALUE,TEST_CREATED_ON_INSTANT,TEST_CREATED_BY_NAME,TEST_UPDATED_BY_ID_VALUE,TEST_UPDATED_ON_INSTANT,TEST_UPDATED_BY_NAME);
        Mockito.when(mockTokenUtils.getIssuerFromToken(PropertiesConstants.TOKEN)).thenReturn(TENANT);
        Mockito.when(mockPropertiesController.getPropertiesByProjectAndFilter(TEST_PROJECT_NAME, TEST_FILTER)).thenThrow(new ProjectNotFoundException(PROJECT_NOT_FOUND_WITH_GIVEN_NAME,PROJECT_NOT_FOUND_WITH_GIVEN_NAME));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(PropertiesConstants.BASE_URL+ PropertiesConstants.VERSION_V1+PROPERTIES_BY_PROJECT_NAME).param(PROJECT_NAME,TEST_PROPERTIES_NAME)
                .param(FILTER,TEST_FILTER)
                .content(objectMapperTest.writeValueAsString(propertiesResponse))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isInternalServerError()).andReturn();
        assertEquals(500,mvcResult.getResponse().getStatus());
    }

    @Test
    void invalidInputExceptionTest() throws Exception
    {
        ObjectMapper objectMapperTest=new ObjectMapper();
        PropertiesResponse propertiesResponse=new PropertiesResponse(TEST_PROJECT_NAME);
        PropertiesMap propertiesMap=new PropertiesMap();
        propertiesMap.setCategory(TEST_CATEGORY);
        propertiesMap.setKey(TEST_KEY);
        propertiesMap.setValue(TEST_VALUE);
        PropertiesSchema propertiesSchema=new PropertiesSchema(TEST_ID,TEST_PROJECT_NAME, List.of(propertiesMap));
        Mockito.when(mockPropertiesController.saveProperties(any())).thenThrow(new InvalidInputException(TOKEN_NOT_NULL,TOKEN_NOT_NULL));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.post(BASE_URL + VERSION_V1 + PROPERTIES_URL)
                .content(objectMapperTest.writeValueAsString(propertiesSchema))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isInternalServerError()).andReturn();
        assertEquals(500,mvcResult.getResponse().getStatus());
    }

    @Test
    void ExternalServiceErrorExceptionTest() throws Exception
    {
        ObjectMapper objectMapperTest=new ObjectMapper();
        PropertiesResponse propertiesResponse=new PropertiesResponse(TEST_PROJECT_NAME);
        PropertiesMap propertiesMap=new PropertiesMap();
        propertiesMap.setCategory(TEST_CATEGORY);
        propertiesMap.setKey(TEST_KEY);
        propertiesMap.setValue(TEST_VALUE);
        PropertiesSchema propertiesSchema=new PropertiesSchema(TEST_ID,TEST_PROJECT_NAME, List.of(propertiesMap));
        Mockito.when(mockPropertiesController.saveProperties(any())).thenThrow(new ExternalServiceErrorException(TOKEN_NOT_NULL,TOKEN_NOT_NULL));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.post(BASE_URL + VERSION_V1+ PROPERTIES_URL)
                .content(objectMapperTest.writeValueAsString(propertiesSchema))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isInternalServerError()).andReturn();
        assertEquals(500,mvcResult.getResponse().getStatus());
    }

    @Test
    void NoDataFoundExceptionTest() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockPropertiesController.deletePropertiesByProjectName(TEST_PROJECT_NAME)).thenThrow(new NoDataFoundException(TOKEN_NOT_NULL,TOKEN_NOT_NULL));
        RequestBuilder requestBuilderTest=MockMvcRequestBuilders
                .delete(BASE_URL+ VERSION_V1+PROPERTIES_BY_PROJECT_NAME).param(PROJECT_NAME,TEST_PROJECT_NAME)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isInternalServerError()).andReturn();
        assertEquals(500,mvcResult.getResponse().getStatus());
    }

    @Test
    void InvalidDataExceptionTest() throws Exception
    {
        ObjectMapper objectMapperTest=new ObjectMapper();
        PropertiesResponse propertiesResponse=new PropertiesResponse(TEST_PROJECT_NAME);
        PropertiesMap propertiesMap=new PropertiesMap();
        propertiesMap.setCategory(TEST_CATEGORY);
        propertiesMap.setKey(TEST_KEY);
        propertiesMap.setValue(TEST_VALUE);
        PropertiesSchema propertiesSchema=new PropertiesSchema(TEST_ID,TEST_PROJECT_NAME, List.of(propertiesMap));
        Mockito.when(mockPropertiesController.saveProperties(propertiesSchema)).thenThrow(new InvalidDataException(TOKEN,TOKEN));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.post(PropertiesConstants.BASE_URL+ PropertiesConstants.VERSION_V1+PROPERTIES_URL)
                .content(objectMapperTest.writeValueAsString(propertiesSchema))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isInternalServerError()).andReturn();
        assertEquals(500,mvcResult.getResponse().getStatus());
    }

    @Test
    void UploadedUserNotFoundExceptionTest() throws Exception
    {
        ObjectMapper objectMapperTest=new ObjectMapper();
        PropertiesResponse propertiesResponse=new PropertiesResponse(TEST_PROJECT_NAME);
        PropertiesMap propertiesMap=new PropertiesMap();
        propertiesMap.setCategory(TEST_CATEGORY);
        propertiesMap.setKey(TEST_KEY);
        propertiesMap.setValue(TEST_VALUE);
        PropertiesResponseSchema propertiesResponseSchema=new PropertiesResponseSchema(
                TEST_ID,TEST_PROJECT_NAME, List.of(propertiesMap),TEST_CREATED_BY_ID_VALUE,TEST_CREATED_ON_INSTANT,TEST_CREATED_BY_NAME,TEST_UPDATED_BY_ID_VALUE,TEST_UPDATED_ON_INSTANT,TEST_UPDATED_BY_NAME);
        Mockito.when(mockTokenUtils.getIssuerFromToken(PropertiesConstants.TOKEN)).thenReturn(TENANT);
        Mockito.when(mockPropertiesController.getPropertiesByProjectAndFilter(TEST_PROJECT_NAME, TEST_FILTER)).thenThrow(new UploadedUserNotFoundException(ENTITY_NOT_FOUND_EXCEPTION,ENTITY_NOT_FOUND_EXCEPTION));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(PropertiesConstants.BASE_URL+ PropertiesConstants.VERSION_V1+PROPERTIES_BY_PROJECT_NAME).param(PROJECT_NAME,TEST_PROPERTIES_NAME)
                .param(FILTER,TEST_FILTER)
                .content(objectMapperTest.writeValueAsString(propertiesResponse))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isInternalServerError()).andReturn();
        assertEquals(500,mvcResult.getResponse().getStatus());
    }

    @Test
    void FileNameNotPresentExceptionTest() throws Exception
    {
        ObjectMapper objectMapperTest=new ObjectMapper();
        PropertiesResponse propertiesResponse=new PropertiesResponse(TEST_PROJECT_NAME);
        PropertiesMap propertiesMap=new PropertiesMap();
        propertiesMap.setCategory(TEST_CATEGORY);
        propertiesMap.setKey(TEST_KEY);
        propertiesMap.setValue(TEST_VALUE);
        PropertiesResponseSchema propertiesResponseSchema=new PropertiesResponseSchema(
                TEST_ID,TEST_PROJECT_NAME, List.of(propertiesMap),TEST_CREATED_BY_ID_VALUE,TEST_CREATED_ON_INSTANT,TEST_CREATED_BY_NAME,TEST_UPDATED_BY_ID_VALUE,TEST_UPDATED_ON_INSTANT,TEST_UPDATED_BY_NAME);
        Mockito.when(mockTokenUtils.getIssuerFromToken(PropertiesConstants.TOKEN)).thenReturn(TENANT);
        Mockito.when(mockPropertiesController.getPropertiesByProjectAndFilter(TEST_PROJECT_NAME, TEST_FILTER)).thenThrow(new FileNameNotPresentException(PROJECT_NOT_FOUND_WITH_GIVEN_NAME,PROJECT_NOT_FOUND_WITH_GIVEN_NAME));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(PropertiesConstants.BASE_URL+ PropertiesConstants.VERSION_V1+PROPERTIES_BY_PROJECT_NAME).param(PROJECT_NAME,TEST_PROPERTIES_NAME)
                .param(FILTER,TEST_FILTER)
                .content(objectMapperTest.writeValueAsString(propertiesResponse))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isInternalServerError()).andReturn();
        assertEquals(500,mvcResult.getResponse().getStatus());
    }
}
