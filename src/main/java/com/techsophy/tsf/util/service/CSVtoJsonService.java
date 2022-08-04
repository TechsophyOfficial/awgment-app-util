package com.techsophy.tsf.util.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CSVtoJsonService
{
    List<Map<String,Object>> convertToJson(String documentId) throws IOException;

}
