package com.techsophy.tsf.util.entity;

import com.techsophy.tsf.util.dto.PropertiesMap;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import static com.techsophy.tsf.util.constants.PropertiesConstants.TP_PROPERTIES_DEFINITION_COLLECTION;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = TP_PROPERTIES_DEFINITION_COLLECTION)
public class PropertiesDefinition extends Auditable implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    private BigInteger id;
    private String projectName;
    private transient List<PropertiesMap> properties;
}
