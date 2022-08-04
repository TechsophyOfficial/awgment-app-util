package com.techsophy.tsf.util.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.techsophy.tsf.util.config.GlobalMessageSource;
import com.techsophy.tsf.util.dto.PropertiesMap;
import com.techsophy.tsf.util.entity.PropertiesDefinition;
import com.techsophy.tsf.util.exception.ProjectNotFoundException;
import com.techsophy.tsf.util.repository.impl.PropertiesDefinitionCustomRepositoryImpl;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles(TEST_ACTIVE_PROFILE)
@SpringBootTest
class PropertiesDefinitionCustomRepoTest
{
    @Mock
    MongoCursor<Object> mockMongoCursor;
    @Mock
    MongoCollection<Document> documentMongoCollection;
    @Mock
    MongoTemplate mockMongoTemplate;
    @Mock
    PropertiesDefinition mockPropertiesDefinition;
    @Mock
    GlobalMessageSource mockGlobalMessageSource;
    @Mock
    MessageSource mockMessageSource;
    @Mock
    AggregateIterable<Object> mockResult;
    @Mock
    ObjectMapper mockObjectMapper;
    @InjectMocks
    PropertiesDefinitionCustomRepositoryImpl mockPropertiesDefinitionCustomRepoImpl;

    @Test
    void findByProjectNameAndFilterProjectExceptionTest()
    {
        Mockito.when(mockMongoTemplate.findOne(any(),any())).thenReturn(null);
        Assertions.assertThrows(ProjectNotFoundException.class, () ->
                mockPropertiesDefinitionCustomRepoImpl.findByProjectNameAndFilter(TEST_PROJECT_NAME,TEST_FILTER));
    }

    @Test
    void findByProjectNameAndEmptyFilterTest()
    {
        PropertiesMap propertiesMap=new PropertiesMap();
        propertiesMap.setValue(TEST_VALUE);
        propertiesMap.setKey(TEST_KEY);
        propertiesMap.setCategory(TEST_CATEGORY);
        PropertiesDefinition propertiesDefinition=new PropertiesDefinition(BigInteger.valueOf(Long.parseLong(TEST_ID)),TEST_PROJECT_NAME, List.of(propertiesMap));
        Mockito.when(mockMongoTemplate.findOne(any(),any())).thenReturn(propertiesDefinition);
        mockPropertiesDefinitionCustomRepoImpl.findByProjectNameAndFilter(TEST_PROJECT_NAME,EMPTY_STRING);
        verify(mockMongoTemplate,times(1)).findOne(any(),any());
    }

    @Test
    void findByProjectNameAndFilterTest()
    {
        PropertiesMap propertiesMap=new PropertiesMap();
        propertiesMap.setValue(TEST_VALUE);
        propertiesMap.setKey(TEST_KEY);
        propertiesMap.setCategory(TEST_CATEGORY);
        PropertiesDefinition propertiesDefinition=new PropertiesDefinition(BigInteger.valueOf(Long.parseLong(TEST_ID)),TEST_PROJECT_NAME, List.of(propertiesMap));
        Mockito.when(mockMongoTemplate.findOne(any(),any())).thenReturn(propertiesDefinition);
        Mockito.when(mockMongoTemplate.getCollection(any())).thenReturn(documentMongoCollection);
        Mockito.when(documentMongoCollection.aggregate(((List<Document>)(any())),any())).thenReturn(mockResult);
        Mockito.when(mockResult.cursor()).thenReturn(mockMongoCursor);
        Mockito.when(mockMongoCursor.next()).thenReturn(Map.of());
        ArrayList<PropertiesMap> a1=new ArrayList<>();
        a1.add(propertiesMap);
        Mockito.when(mockObjectMapper.convertValue(any(), (TypeReference<Object>) any())).thenReturn(a1);
        mockPropertiesDefinitionCustomRepoImpl.findByProjectNameAndFilter(TEST_PROJECT_NAME,"key1:value1,key2:value2");
        verify(documentMongoCollection,times(1)).aggregate(((List<Document>)(any())),any());
    }
}
