package com.techsophy.tsf.util.exception;

public class UploadedUserNotFoundException extends RuntimeException
{

    final String errorCode;
    final String message;
    public UploadedUserNotFoundException(String errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
        this.message=message;

    }
}
