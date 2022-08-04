package com.techsophy.tsf.util.exception;

public class UnsupportedFileFormatException extends RuntimeException
{
    final String errorCode;
    final String message;
    public UnsupportedFileFormatException(String errorCode, String message)
    {
        super(message);
        this.errorCode=errorCode;
        this.message=message;
    }
}
