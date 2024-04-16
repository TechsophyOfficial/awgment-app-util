package com.techsophy.tsf.util.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Map;

import static com.techsophy.tsf.util.constants.FileUploadConstants.TP_FILE_UPLOAD;
import static com.techsophy.tsf.util.constants.FileUploadConstants.USER_ID_NOT_NULL;

@EqualsAndHashCode(callSuper = true)
@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Document(collection = "khsEmployees")
public class FileDataDefinition extends Auditable{

  @NotNull(message = USER_ID_NOT_NULL)
  @Id
  private BigInteger id;
  private Map<String,Object> userData;
  private Boolean isConfirmed;
}
