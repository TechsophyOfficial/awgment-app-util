package com.techsophy.tsf.util.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateQRDto
{
    private String data;
    private Integer height;
    private Integer width;
}
