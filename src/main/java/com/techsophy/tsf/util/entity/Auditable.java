package com.techsophy.tsf.util.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.time.Instant;

@Data
@With
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class Auditable
{

    private BigInteger createdById;
    private BigInteger updatedById;
    private Instant createdOn;
    private Instant updatedOn;
}

