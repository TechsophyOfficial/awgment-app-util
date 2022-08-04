package com.techsophy.tsf.util.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import org.springframework.http.HttpStatus;
import java.time.Instant;
import static com.techsophy.tsf.util.constants.PropertiesConstants.DATE_PATTERN;
import static com.techsophy.tsf.util.constants.PropertiesConstants.TIME_ZONE;

@Value
public class ApiErrorResponse
{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = TIME_ZONE)
    Instant timestamp;
    String message;
    String error;
    HttpStatus status;
    String path;
}
