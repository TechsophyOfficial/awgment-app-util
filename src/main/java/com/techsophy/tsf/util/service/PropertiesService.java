package com.techsophy.tsf.util.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.util.dto.PropertiesMap;
import com.techsophy.tsf.util.dto.PropertiesResponse;
import com.techsophy.tsf.util.dto.PropertiesResponseSchema;
import com.techsophy.tsf.util.dto.PropertiesSchema;
import java.util.List;

public interface PropertiesService
{
    PropertiesResponse saveProperties(PropertiesSchema properties) throws JsonProcessingException;
    List<PropertiesMap> getPropertiesByProjectName(String projectName, String filter) throws JsonProcessingException;
    List<PropertiesResponseSchema> getAllProperties();
    void deletePropertiesByProjectName(String projectName);
}
