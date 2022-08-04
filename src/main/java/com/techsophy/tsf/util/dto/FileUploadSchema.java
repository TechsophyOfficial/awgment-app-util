package com.techsophy.tsf.util.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.Map;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadSchema
{

    String id;
    Map<String,Object> userData;
    String documentId;
    String status;
}
