package com.techsophy.tsf.util.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.util.config.CustomFilter;
import com.techsophy.tsf.util.dto.PropertiesMap;
import com.techsophy.tsf.util.dto.PropertiesResponse;
import com.techsophy.tsf.util.dto.PropertiesResponseSchema;
import com.techsophy.tsf.util.dto.PropertiesSchema;
import com.techsophy.tsf.util.service.impl.PropertiesServiceImpl;
import com.techsophy.tsf.util.utils.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.List;
import static com.techsophy.tsf.util.constants.PropertiesConstants.AWGMENT_UTIL_CREATE_OR_UPDATE;
import static com.techsophy.tsf.util.constants.PropertiesConstants.AWGMENT_UTIL_DELETE;
import static com.techsophy.tsf.util.constants.PropertiesConstants.AWGMENT_UTIL_READ;
import static com.techsophy.tsf.util.constants.PropertiesConstants.BASE_URL;
import static com.techsophy.tsf.util.constants.PropertiesConstants.TOKEN;
import static com.techsophy.tsf.util.constants.PropertiesConstants.VERSION_V1;
import static com.techsophy.tsf.util.constants.PropertiesConstants.*;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PropertiesControllerTest
{
    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtSaveOrUpdate = jwt().authorities(new SimpleGrantedAuthority(AWGMENT_UTIL_CREATE_OR_UPDATE));
    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtRead = jwt().authorities(new SimpleGrantedAuthority(AWGMENT_UTIL_READ));
    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtDelete = jwt().authorities(new SimpleGrantedAuthority(AWGMENT_UTIL_DELETE));

    @MockBean
    TokenUtils mockTokenUtils;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    PropertiesServiceImpl mockPropertiesServiceImpl;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    CustomFilter customFilter;

    @BeforeEach public void setUp()
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(customFilter)
                .apply(springSecurity())
                .build();
    }

    @Test
    void savePropertiesTest() throws Exception
    {
        ObjectMapper objectMapperTest=new ObjectMapper();
        PropertiesResponse propertiesResponse=new PropertiesResponse(TEST_PROJECT_NAME);
        PropertiesMap propertiesMap=new PropertiesMap();
        propertiesMap.setCategory(TEST_CATEGORY);
        propertiesMap.setKey(TEST_KEY);
        propertiesMap.setValue(TEST_VALUE);
        PropertiesSchema propertiesSchema=new PropertiesSchema("1","abc", List.of(propertiesMap));
        Mockito.when(mockPropertiesServiceImpl.saveProperties(propertiesSchema)).thenReturn(propertiesResponse);
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.post(BASE_URL+ VERSION_V1+PROPERTIES_URL)
                .content(objectMapperTest.writeValueAsString(propertiesSchema))
                .with(jwtSaveOrUpdate)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void getPropertiesByIdTest() throws Exception
    {
        ObjectMapper objectMapperTest=new ObjectMapper();
        PropertiesResponse propertiesResponse=new PropertiesResponse(TEST_PROJECT_NAME);
        PropertiesMap propertiesMap=new PropertiesMap();
        propertiesMap.setCategory(TEST_CATEGORY);
        propertiesMap.setKey(TEST_KEY);
        propertiesMap.setValue(TEST_VALUE);
        PropertiesResponseSchema propertiesResponseSchema=new PropertiesResponseSchema(
                TEST_ID,TEST_PROJECT_NAME, List.of(propertiesMap),TEST_CREATED_BY_ID_VALUE,TEST_CREATED_ON_INSTANT,TEST_CREATED_BY_NAME,TEST_UPDATED_BY_ID_VALUE,TEST_UPDATED_ON_INSTANT,TEST_UPDATED_BY_NAME);
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockPropertiesServiceImpl.getPropertiesByProjectName(TEST_PROJECT_NAME, TEST_FILTER)).thenReturn(List.of(propertiesMap));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(BASE_URL+ VERSION_V1+PROPERTIES_BY_PROJECT_NAME).param(PROJECT_NAME,TEST_PROPERTIES_NAME)
                .param(FILTER,TEST_FILTER)
                .content(objectMapperTest.writeValueAsString(propertiesResponse))
                .with(jwtRead)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void getAllPropertiesTest() throws Exception
    {
        PropertiesMap propertiesMap=new PropertiesMap();
        propertiesMap.setCategory(TEST_CATEGORY);
        propertiesMap.setKey(TEST_KEY);
        propertiesMap.setValue(TEST_VALUE);
        ObjectMapper objectMapperTest=new ObjectMapper();
        PropertiesResponseSchema propertiesResponseSchema=new PropertiesResponseSchema(TEST_ID
                ,TEST_PROPERTIES_NAME,List.of(propertiesMap),TEST_CREATED_BY_ID_VALUE
                ,null,TEST_CREATED_BY_NAME,TEST_UPDATED_BY_NAME,null,TEST_UPDATED_BY_NAME);
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockPropertiesServiceImpl.getAllProperties()).thenReturn(List.of(propertiesResponseSchema));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(BASE_URL+ VERSION_V1+PROPERTIES_URL)
                .content(objectMapperTest.writeValueAsString(propertiesResponseSchema))
                .with(jwtRead)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void deletePropertiesTest() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.doNothing().when(mockPropertiesServiceImpl).deletePropertiesByProjectName("abc");
        RequestBuilder requestBuilderTest=MockMvcRequestBuilders
                .delete(BASE_URL+ VERSION_V1+PROPERTIES_BY_PROJECT_NAME).param(PROJECT_NAME,TEST_PROJECT_NAME)
                .with(jwtDelete)
                .contentType(MediaType.APPLICATION_JSON);
       MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }
}
