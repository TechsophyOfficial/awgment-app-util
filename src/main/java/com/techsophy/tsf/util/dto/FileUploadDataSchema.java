package com.techsophy.tsf.util.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Map;
import static com.techsophy.tsf.util.constants.FileUploadConstants.USER_ID_NOT_NULL;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadDataSchema {

  @NotEmpty(message = "id must not be empty")
  @NotNull(message = USER_ID_NOT_NULL)
  private String id;

  @NotEmpty(message = "userData must not be empty")
  private Map<String,Object> userData;

  private Boolean isConfirmed;

}
