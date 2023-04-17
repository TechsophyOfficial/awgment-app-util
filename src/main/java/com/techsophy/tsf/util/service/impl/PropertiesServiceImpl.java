package com.techsophy.tsf.util.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.idgenerator.IdGeneratorImpl;
import com.techsophy.tsf.util.config.GlobalMessageSource;
import com.techsophy.tsf.util.dto.PropertiesMap;
import com.techsophy.tsf.util.dto.PropertiesResponse;
import com.techsophy.tsf.util.dto.PropertiesResponseSchema;
import com.techsophy.tsf.util.dto.PropertiesSchema;
import com.techsophy.tsf.util.entity.PropertiesDefinition;
import com.techsophy.tsf.util.exception.ProjectNotFoundException;
import com.techsophy.tsf.util.exception.EntityIdNotFoundException;
import com.techsophy.tsf.util.exception.UserDetailsIdNotFoundException;
import com.techsophy.tsf.util.repository.PropertiesDefinitionRepository;
import com.techsophy.tsf.util.service.PropertiesService;
import com.techsophy.tsf.util.utils.UserDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.techsophy.tsf.util.constants.ErrorConstants.*;
import static com.techsophy.tsf.util.constants.PropertiesConstants.*;

@Slf4j
@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class PropertiesServiceImpl implements PropertiesService
{
    private final  PropertiesDefinitionRepository propertiesDefinitionRepository;
    private final UserDetails userDetails;
    private final IdGeneratorImpl idGeneratorImpl;
    private final GlobalMessageSource globalMessageSource;

    @Override
    public PropertiesResponse saveProperties(PropertiesSchema propertiesSchema) throws JsonProcessingException
    {
        Map<String,Object> loggedInUserDetails =userDetails.getUserDetails().get(0);
        if (StringUtils.isEmpty(loggedInUserDetails.get(ID).toString()))
        {
            throw new UserDetailsIdNotFoundException(LOGGED_IN_USER_ID_NOT_FOUND_EXCEPTION,globalMessageSource.get(LOGGED_IN_USER_ID_NOT_FOUND_EXCEPTION,loggedInUserDetails.get(ID).toString()));
        }
        BigInteger loggedInUserId = BigInteger.valueOf(Long.parseLong(loggedInUserDetails.get(ID).toString()));
        BigInteger id=idGeneratorImpl.nextId();
        PropertiesDefinition propertiesDefinition=new PropertiesDefinition();
        String projectName = propertiesSchema.getProjectName();
        if(!propertiesDefinitionRepository.existsByProjectName(projectName))
        {
            propertiesDefinition.setId(id);
            propertiesDefinition.setProjectName(propertiesSchema.getProjectName());
            propertiesDefinition.setProperties(propertiesSchema.getProperties());
            propertiesDefinition.setCreatedById(loggedInUserId);
            propertiesDefinition.setCreatedOn(Instant.now());
        }
        else
        {
            PropertiesDefinition existingPropertiesDefinition=this.propertiesDefinitionRepository.findByProjectName(projectName);
            propertiesDefinition.setId(existingPropertiesDefinition.getId());
            propertiesDefinition.setProjectName(existingPropertiesDefinition.getProjectName());
            List<PropertiesMap> propertiesList=new ArrayList<>();
            propertiesList.addAll(existingPropertiesDefinition.getProperties());
            propertiesList.addAll(propertiesSchema.getProperties());
            propertiesDefinition.setProperties(propertiesList);
            propertiesDefinition.setCreatedById(existingPropertiesDefinition.getCreatedById());
            propertiesDefinition.setCreatedOn(existingPropertiesDefinition.getCreatedOn());
        }
        propertiesDefinition.setUpdatedById(loggedInUserId);
        propertiesDefinition.setUpdatedOn(Instant.now());
        this.propertiesDefinitionRepository.save(propertiesDefinition);
        return new PropertiesResponse(propertiesDefinition.getProjectName());
    }

    @Override
    public List<PropertiesMap> getPropertiesByProjectName(String projectName, String filter) throws JsonProcessingException
    {
        if(!propertiesDefinitionRepository.existsByProjectName(projectName))
        {
            throw new ProjectNotFoundException(PROJECT_NOT_FOUND_WITH_GIVEN_NAME,globalMessageSource.get(PROJECT_NOT_FOUND_WITH_GIVEN_NAME,projectName));
        }
        return this.propertiesDefinitionRepository.findByProjectNameAndFilter(projectName,filter);
    }

    @Override
    public List<PropertiesResponseSchema> getAllProperties()
    {
        List<PropertiesResponseSchema> propertiesResponseSchemaList=new ArrayList<>();
        List<PropertiesDefinition> propertiesDefinition=this.propertiesDefinitionRepository.findAll();
        for(PropertiesDefinition definition : propertiesDefinition)
        {
            PropertiesResponseSchema propertiesResponseSchema=new PropertiesResponseSchema(String.valueOf(definition.getId()),definition.getProjectName(),definition.getProperties(),String.valueOf(definition.getCreatedById())
            ,definition.getCreatedOn(),definition.getUpdatedOn());
        propertiesResponseSchemaList.add(propertiesResponseSchema);
        }
        return propertiesResponseSchemaList;
    }

    @Override
    public void deletePropertiesByProjectName(String projectName)
    {
        if(!propertiesDefinitionRepository.existsByProjectName(projectName))
        {
            throw new EntityIdNotFoundException(ENTITY_ID_NOT_FOUND_EXCEPTION,globalMessageSource.get(ENTITY_ID_NOT_FOUND_EXCEPTION, projectName));
        }
        propertiesDefinitionRepository.deleteByProjectName(projectName);
    }
}
