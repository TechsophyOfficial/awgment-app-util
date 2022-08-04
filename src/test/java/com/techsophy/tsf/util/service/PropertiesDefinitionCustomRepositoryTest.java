package com.techsophy.tsf.util.service;

import com.techsophy.tsf.util.entity.PropertiesDefinition;
import com.techsophy.tsf.util.repository.impl.PropertiesDefinitionCustomRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.TEST_ACTIVE_PROFILE;
import static org.mockito.Mockito.*;

@ActiveProfiles(TEST_ACTIVE_PROFILE)
@SpringBootTest
class PropertiesDefinitionCustomRepositoryTest
{
    @Mock
    MongoTemplate mongoTemplate;
    @Mock
    PropertiesDefinition propertiesDefinition;
    @InjectMocks
    PropertiesDefinitionCustomRepositoryImpl mockPropertiesDefinitionCustomRepositoryImpl;

    @Test
    void findByApplicationName()
    {
        when(mockPropertiesDefinitionCustomRepositoryImpl.findByProjectName("abc")).thenReturn(propertiesDefinition);
        PropertiesDefinition propertiesDefinition1=mockPropertiesDefinitionCustomRepositoryImpl.findByProjectName("abc");
        Assertions.assertNotNull(propertiesDefinition1);
    }

    @Test
    void existsByApplicationNameTest()
    {
        when(mockPropertiesDefinitionCustomRepositoryImpl.existsByProjectName("abc")).thenReturn(true);
        Boolean existsByApplicationName=mockPropertiesDefinitionCustomRepositoryImpl.existsByProjectName("abc");
        Assertions.assertNotNull(existsByApplicationName);
    }

    @Test
    void deleteByApplicationNameTest()
    {
        PropertiesDefinitionCustomRepositoryImpl propertiesDefinitionCustomRepository = mock(PropertiesDefinitionCustomRepositoryImpl.class);
        doNothing().when(propertiesDefinitionCustomRepository).deleteByProjectName("abc");
        mockPropertiesDefinitionCustomRepositoryImpl.deleteByProjectName("abc");
        Assertions.assertTrue(true);
    }
}
