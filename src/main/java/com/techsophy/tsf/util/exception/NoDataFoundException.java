package com.techsophy.tsf.util.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NoDataFoundException extends RuntimeException
{
    final String message;
    final String errorCode;
    public NoDataFoundException(String errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
        this.message=message;
    }

}
