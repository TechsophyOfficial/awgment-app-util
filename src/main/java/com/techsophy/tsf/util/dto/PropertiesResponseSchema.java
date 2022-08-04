package com.techsophy.tsf.util.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import lombok.With;
import java.time.Instant;
import java.util.List;
import static com.techsophy.tsf.util.constants.PropertiesConstants.DATE_PATTERN;
import static com.techsophy.tsf.util.constants.PropertiesConstants.TIME_ZONE;

@With
@Value
public class PropertiesResponseSchema
{
    String id;
    String projectName;
    List<PropertiesMap> properties;
    String createdById;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = TIME_ZONE)
    Instant createdOn;
    String createdByName;
    String updatedById;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = TIME_ZONE)
    Instant updatedOn;
    String updatedByName;
}
