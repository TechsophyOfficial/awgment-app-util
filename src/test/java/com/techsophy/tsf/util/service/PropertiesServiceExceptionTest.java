package com.techsophy.tsf.util.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.idgenerator.IdGeneratorImpl;
import com.techsophy.tsf.util.config.GlobalMessageSource;
import com.techsophy.tsf.util.config.LocaleConfig;
import com.techsophy.tsf.util.dto.PropertiesMap;
import com.techsophy.tsf.util.dto.PropertiesSchema;
import com.techsophy.tsf.util.exception.EntityIdNotFoundException;
import com.techsophy.tsf.util.exception.ProjectNotFoundException;
import com.techsophy.tsf.util.exception.UserDetailsIdNotFoundException;
import com.techsophy.tsf.util.repository.PropertiesDefinitionRepository;
import com.techsophy.tsf.util.service.impl.PropertiesServiceImpl;
import com.techsophy.tsf.util.utils.TokenUtils;
import com.techsophy.tsf.util.utils.UserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.*;

@ActiveProfiles(TEST_ACTIVE_PROFILE)
@SpringBootTest
class PropertiesServiceExceptionTest
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
    PropertiesServiceImpl mockPropertiesServiceImpl;

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
        map.put(ID,EMPTY_STRING);
        map.put(USER_NAME, USER_FIRST_NAME);
        map.put(FIRST_NAME, USER_LAST_NAME);
        map.put(LAST_NAME, USER_FIRST_NAME);
        map.put(MOBILE_NUMBER, NUMBER);
        map.put(EMAIL_ID, MAIL_ID);
        map.put(DEPARTMENT, NULL);
        userList.add(map);
    }

    @Test
    void savePropertiesUserDetailsExceptionTest() throws JsonProcessingException
    {
        Mockito.when(mockUserDetails.getUserDetails())
                .thenReturn(userList);
        PropertiesMap propertiesMap=new PropertiesMap();
        propertiesMap.setCategory(TEST_CATEGORY);
        propertiesMap.setKey(TEST_KEY);
        propertiesMap.setValue(TEST_VALUE);
        PropertiesSchema propertiesSchema=new PropertiesSchema(TEST_ID,TEST_PROJECT_NAME,List.of(propertiesMap));
        Assertions.assertThrows(UserDetailsIdNotFoundException.class, () ->
                mockPropertiesServiceImpl.saveProperties(propertiesSchema));
    }

    @Test
    void getPropertiesByProjectNameNotFoundExceptionTest()
    {
        Mockito.when(mockPropertiesDefinitionRepository.existsByProjectName(TEST_PROJECT_NAME)).thenReturn(false);
        Assertions.assertThrows(ProjectNotFoundException.class, () ->
                mockPropertiesServiceImpl.getPropertiesByProjectName(TEST_PROJECT_NAME,TEST_FILTER));
    }

    @Test
    void deletePropertiesByProjectEntityNotFoundExceptionTest()
    {
        Mockito.when(mockPropertiesDefinitionRepository.existsByProjectName(TEST_PROJECT_NAME)).thenReturn(false);
        Assertions.assertThrows(EntityIdNotFoundException.class,()->
                mockPropertiesServiceImpl.deletePropertiesByProjectName(TEST_PROJECT_NAME));
    }
}
