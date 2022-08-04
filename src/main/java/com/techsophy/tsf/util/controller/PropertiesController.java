package com.techsophy.tsf.util.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.util.dto.PropertiesMap;
import com.techsophy.tsf.util.dto.PropertiesResponse;
import com.techsophy.tsf.util.dto.PropertiesResponseSchema;
import com.techsophy.tsf.util.dto.PropertiesSchema;
import com.techsophy.tsf.util.model.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.techsophy.tsf.util.constants.PropertiesConstants.*;

@RequestMapping(BASE_URL+ VERSION_V1)
public interface PropertiesController
{
    @PostMapping(PROPERTIES_URL)
    @PreAuthorize(CREATE_OR_ALL_ACCESS)
    ApiResponse<PropertiesResponse> saveProperties(@RequestBody @Validated PropertiesSchema form) throws JsonProcessingException;

    @GetMapping(PROPERTIES_BY_PROJECT_NAME)
    @PreAuthorize(READ_OR_ALL_ACCESS)
    ApiResponse<List<PropertiesMap>> getPropertiesByProjectAndFilter(@RequestParam(value= PROJECT_NAME) String projectName, @RequestParam(value =FILTER,required = false) String filter) throws JsonProcessingException;

    @GetMapping(PROPERTIES_URL)
    @PreAuthorize(READ_OR_ALL_ACCESS)
    ApiResponse<List<PropertiesResponseSchema>> getAllProperties();

    @DeleteMapping(PROPERTIES_BY_PROJECT_NAME)
    @PreAuthorize(DELETE_OR_ALL_ACCESS)
    ApiResponse<Void> deletePropertiesByProjectName(@RequestParam(value= PROJECT_NAME) String projectName);
}
