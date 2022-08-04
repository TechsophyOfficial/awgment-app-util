package com.techsophy.tsf.util.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.idgenerator.IdGeneratorImpl;
import com.techsophy.tsf.util.config.GlobalMessageSource;
import com.techsophy.tsf.util.config.LocaleConfig;
import com.techsophy.tsf.util.dto.PropertiesMap;
import com.techsophy.tsf.util.dto.PropertiesSchema;
import com.techsophy.tsf.util.entity.PropertiesDefinition;
import com.techsophy.tsf.util.repository.PropertiesDefinitionRepository;
import com.techsophy.tsf.util.service.impl.PropertiesServiceImpl;
import com.techsophy.tsf.util.utils.TokenUtils;
import com.techsophy.tsf.util.utils.UserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.*;
import static org.mockito.Mockito.*;

@ContextConfiguration
@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PropertiesServiceTest
{
    @Mock
    UserDetails mockUserDetails;
    @Mock
    TokenUtils tokenUtils;
    @Mock
    PropertiesDefinitionRepository mockPropertiesDefinitionRepository;
    @Mock
    ObjectMapper mockObjectMapper;
    @Mock
    IdGeneratorImpl mockIdGenerator;
    @Mock
    GlobalMessageSource mockGlobalMessageSource;
    @Mock
    LocaleConfig localeConfig;
    @InjectMocks
    PropertiesServiceImpl mockPropertiesService;

    List<Map<String, Object>> userList = new ArrayList<>();

    @BeforeEach
    public void init()
    {
        Map<String, Object> map = new HashMap<>();
        map.put(CREATED_BY_ID, NULL);
        map.put(CREATED_BY_NAME, NULL);
        map.put(CREATED_ON, NULL);
        map.put(UPDATED_BY_ID, NULL);
        map.put(UPDATED_BY_NAME, NULL);
        map.put(UPDATED_ON, NULL);
        map.put(ID, BIGINTEGER_ID);
        map.put(USER_NAME, USER_FIRST_NAME);
        map.put(FIRST_NAME, USER_LAST_NAME);
        map.put(LAST_NAME, USER_FIRST_NAME);
        map.put(MOBILE_NUMBER, NUMBER);
        map.put(EMAIL_ID, MAIL_ID);
        map.put(DEPARTMENT, NULL);
        userList.add(map);
    }

    @Test
    void savePropertiesTest() throws IOException
    {
        Mockito.when(mockUserDetails.getUserDetails())
                .thenReturn(userList);
        PropertiesMap propertiesMap=new PropertiesMap();
        propertiesMap.setCategory(TEST_CATEGORY);
        propertiesMap.setKey(TEST_KEY);
        propertiesMap.setValue(TEST_VALUE);
        PropertiesSchema propertiesSchema=new PropertiesSchema(TEST_ID,TEST_PROJECT_NAME,List.of(propertiesMap));
        Mockito.when(mockPropertiesDefinitionRepository.existsByProjectName(TEST_PROJECT_NAME)).thenReturn(false);
        mockPropertiesService.saveProperties(propertiesSchema);
        verify(mockPropertiesDefinitionRepository, times(1)).save(any());
    }

    @Test
    void savePropertiesUpdateTest() throws JsonProcessingException
    {
        Mockito.when(mockUserDetails.getUserDetails())
                .thenReturn(userList);
        PropertiesMap propertiesMap=new PropertiesMap();
        propertiesMap.setCategory(TEST_CATEGORY);
        propertiesMap.setKey(TEST_KEY);
        propertiesMap.setValue(TEST_VALUE);
        PropertiesSchema propertiesSchema=new PropertiesSchema(TEST_ID,TEST_PROJECT_NAME,List.of(propertiesMap));
        Mockito.when(mockPropertiesDefinitionRepository.existsByProjectName(TEST_PROJECT_NAME)).thenReturn(true);
        PropertiesDefinition existingDefinitionTest=new PropertiesDefinition(BigInteger.valueOf(Long.parseLong(TEST_ID)),TEST_PROJECT_NAME,List.of(propertiesMap));
        Mockito.when(mockPropertiesDefinitionRepository.findByProjectName(TEST_PROJECT_NAME)).thenReturn(existingDefinitionTest);
        mockPropertiesService.saveProperties(propertiesSchema);
        verify(mockPropertiesDefinitionRepository, times(1)).save(any());
    }

    @Test
    void getPropertiesByProjectNameTest() throws JsonProcessingException
    {
        Mockito.when(mockPropertiesDefinitionRepository.existsByProjectName(TEST_PROJECT_NAME)).thenReturn(true);
        mockPropertiesService.getPropertiesByProjectName(TEST_PROJECT_NAME,TEST_FILTER);
        verify(mockPropertiesDefinitionRepository,times(1)).findByProjectNameAndFilter(TEST_PROJECT_NAME,TEST_FILTER);
    }

    @Test
    void getAllPropertiesTest()
    {
        PropertiesMap propertiesMap=new PropertiesMap();
        propertiesMap.setCategory(TEST_CATEGORY);
        propertiesMap.setKey(TEST_KEY);
        propertiesMap.setValue(TEST_VALUE);
        PropertiesDefinition propertiesDefinition=new PropertiesDefinition(BigInteger.valueOf(Long.parseLong(TEST_ID)),TEST_PROJECT_NAME,List.of(propertiesMap));
        Mockito.when(mockPropertiesDefinitionRepository.findAll()).thenReturn(List.of(propertiesDefinition));
        mockPropertiesService.getAllProperties();
        verify(mockPropertiesDefinitionRepository,times(1)).findAll();
    }

    @Test
    void deletePropertiesByProjectName()
    {
        Mockito.when(mockPropertiesDefinitionRepository.existsByProjectName(TEST_PROJECT_NAME)).thenReturn(true);
        mockPropertiesService.deletePropertiesByProjectName(TEST_PROJECT_NAME);
        verify(mockPropertiesDefinitionRepository,times(1)).deleteByProjectName(TEST_PROJECT_NAME);
    }
}

