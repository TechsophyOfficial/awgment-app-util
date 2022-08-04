package com.techsophy.tsf.util.exception;

public class ProjectNotFoundException extends RuntimeException
{
    final String errorCode;
    final String message;
    public ProjectNotFoundException(String errorCode,String message)
    {
        super(message);
        this.errorCode = errorCode;
        this.message=message;
    }
}
