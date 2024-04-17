package com.techsophy.tsf.util.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class FileDataUploadResponse {
  boolean success;
  List<String> errors;
}
