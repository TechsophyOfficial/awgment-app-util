package com.techsophy.tsf.util.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.util.config.GlobalMessageSource;
import com.techsophy.tsf.util.controller.PropertiesController;
import com.techsophy.tsf.util.dto.PropertiesMap;
import com.techsophy.tsf.util.dto.PropertiesResponse;
import com.techsophy.tsf.util.dto.PropertiesResponseSchema;
import com.techsophy.tsf.util.dto.PropertiesSchema;
import com.techsophy.tsf.util.model.ApiResponse;
import com.techsophy.tsf.util.service.PropertiesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import static com.techsophy.tsf.util.constants.PropertiesConstants.*;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class PropertiesControllerImpl implements PropertiesController
{
    private final PropertiesService propertiesService;
    private final GlobalMessageSource globalMessageSource;

    @Override
    public ApiResponse<PropertiesResponse> saveProperties(PropertiesSchema properties) throws JsonProcessingException
    {
        PropertiesResponse data= propertiesService.saveProperties(properties);
        return new ApiResponse<>(data,true,globalMessageSource.get(SAVE_PROPERTIES_SUCCESS));
    }

    @Override
    public ApiResponse<List<PropertiesMap>> getPropertiesByProjectAndFilter(String projectName, String filter) throws JsonProcessingException
    {
        return new ApiResponse<>(propertiesService.getPropertiesByProjectName(projectName,filter), true, globalMessageSource.get(GET_PROPERTIES_SUCCESS));
    }

    @Override
    public ApiResponse<List<PropertiesResponseSchema>> getAllProperties()
    {
        return new ApiResponse<>(propertiesService.getAllProperties(),true, globalMessageSource.get(GET_PROPERTIES_SUCCESS));
    }

    @Override
    public ApiResponse<Void> deletePropertiesByProjectName(String projectName)
    {
        propertiesService.deletePropertiesByProjectName(projectName);
        return new ApiResponse<>(null,true,globalMessageSource.get(DELETE_PROPERTIES_SUCCESS));
    }
}
