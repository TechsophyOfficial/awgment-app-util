package com.techsophy.tsf.util.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.util.dto.PropertiesMap;
import com.techsophy.tsf.util.entity.PropertiesDefinition;
import java.util.List;

public interface PropertiesDefinitionCustomRepository
{
 void deleteByProjectName(String projectName);
 boolean existsByProjectName(String projectName);
 PropertiesDefinition findByProjectName(String projectName);
 List<PropertiesMap> findByProjectNameAndFilter(String projectName, String filter) throws JsonProcessingException;
}
