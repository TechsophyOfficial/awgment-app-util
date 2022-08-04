package com.techsophy.tsf.util.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.techsophy.tsf.util.config.GlobalMessageSource;
import com.techsophy.tsf.util.dto.PropertiesMap;
import com.techsophy.tsf.util.entity.PropertiesDefinition;
import com.techsophy.tsf.util.exception.ProjectNotFoundException;
import com.techsophy.tsf.util.repository.PropertiesDefinitionCustomRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import static com.techsophy.tsf.util.constants.ErrorConstants.PROJECT_NOT_FOUND_WITH_GIVEN_NAME;
import static com.techsophy.tsf.util.constants.PropertiesConstants.*;

@AllArgsConstructor
public class PropertiesDefinitionCustomRepositoryImpl implements PropertiesDefinitionCustomRepository
{
    private final MongoTemplate mongoTemplate;
    private final GlobalMessageSource globalMessageSource;
    private final ObjectMapper objectMapper;

    @Override
    public boolean existsByProjectName(String projectName)
    {
        Query query=new Query();
        String searchString = URLDecoder.decode(projectName, StandardCharsets.UTF_8);
        query.addCriteria(Criteria.where(PROJECT_NAME).is(searchString));
        return mongoTemplate.exists(query,PropertiesDefinition.class);
    }

    @Override
    public PropertiesDefinition findByProjectName(String projectName)
    {
        Query query=new Query();
        String searchString = URLDecoder.decode(projectName, StandardCharsets.UTF_8);
        query.addCriteria(Criteria.where(PROJECT_NAME).regex(searchString));
        return mongoTemplate.findOne(query,PropertiesDefinition.class);
    }

    @Override
    public List<PropertiesMap> findByProjectNameAndFilter(String projectName, String filter)
    {
        Query query=new Query();
        String searchString = URLDecoder.decode(projectName, StandardCharsets.UTF_8);
        query.addCriteria(Criteria.where(PROJECT_NAME).regex(searchString));
        PropertiesDefinition propertiesDefinition = mongoTemplate.findOne(query,PropertiesDefinition.class);
        if(propertiesDefinition==null)
        {
            throw new ProjectNotFoundException(PROJECT_NOT_FOUND_WITH_GIVEN_NAME,globalMessageSource.get(PROJECT_NOT_FOUND_WITH_GIVEN_NAME,projectName));
        }
        List<PropertiesMap> requiredPropertiesMapList =new ArrayList<>();
        if(StringUtils.isEmpty(filter))
        {
          requiredPropertiesMapList.addAll(propertiesDefinition.getProperties());
          return requiredPropertiesMapList;
        }
        String[] parts=filter.split(COMMA);
        Map<String,String> keyValueMap=new HashMap<>();
        for (String part : parts)
        {
            String[] keyValue = part.split(COLON);
            keyValueMap.put(keyValue[0].replaceAll(REGEX_PATTERN_1, EMPTY_STRING), keyValue[1].replaceAll(REGEX_PATTERN_1, EMPTY_STRING));
        }
        var temp=Arrays.asList(new Document(MATCH,
                        new Document(PROJECT_NAME, projectName)),
                new Document(PROJECT,
                        new Document(PROPERTIES,
                                new Document(FIRST_FILTER,
                                        new Document(INPUT,PROPERTIES_KEY)
                                                .append(AS,P)
                                                .append(CONDITION,
                                                        new Document(OR_CONDITION, Arrays.asList(new Document(EQUALS_CONST, Arrays.asList(CATEGORY_IN_PROPERTIES_MAP,String.valueOf(keyValueMap.get(CATEGORY)))),
                                                                new Document(EQUALS_CONST, Arrays.asList(KEY_IN_PROPERTIES_MAP,String.valueOf(keyValueMap.get(KEY)))),
                                                                new Document(EQUALS_CONST, Arrays.asList(VALUE_IN_PROPERTIES_MAP,String.valueOf(keyValueMap.get(VALUE)))))))))));
        MongoCollection<Document> collection=mongoTemplate.getCollection(TP_PROPERTIES_DEFINITION_COLLECTION);
        var result= collection.aggregate(temp,Map.class);
        var c=result.cursor().next();
        ArrayList<PropertiesMap> propertiesMapArrayList=objectMapper.convertValue(c.get(PROPERTIES), new TypeReference<>() {
        });
        requiredPropertiesMapList.addAll(propertiesMapArrayList);
        return requiredPropertiesMapList;
    }

    @Override
    public void deleteByProjectName(String projectName)
    {
        Query query = new Query();
        String searchString = URLDecoder.decode(projectName, StandardCharsets.UTF_8);
        query.addCriteria(Criteria.where(PROJECT_NAME).is(searchString));
        mongoTemplate.findAndRemove(query,PropertiesDefinition.class);
    }
}
