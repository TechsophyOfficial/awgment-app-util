package com.techsophy.tsf.util.dto;

import com.techsophy.tsf.util.entity.Auditable;
import lombok.*;
import org.springframework.data.annotation.Id;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Map;
import static com.techsophy.tsf.util.constants.FileUploadConstants.USER_ID_NOT_NULL;

@EqualsAndHashCode(callSuper = true)
@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class FileData extends Auditable
{
    @NotNull(message = USER_ID_NOT_NULL)
    @Id
    private BigInteger id;
    private Map<String,Object> userData;
    private BigInteger documentId;
    private String status;
}
