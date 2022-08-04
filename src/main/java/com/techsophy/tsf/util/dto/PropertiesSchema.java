package com.techsophy.tsf.util.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;
import javax.validation.constraints.NotBlank;
import java.util.List;
import static com.techsophy.tsf.util.constants.PropertiesConstants.NAME_NOT_BLANK;

@With
@Data
@AllArgsConstructor
public class PropertiesSchema
{
    String id;
    @NotBlank(message = NAME_NOT_BLANK)
    String projectName;
    List<PropertiesMap> properties;
}
