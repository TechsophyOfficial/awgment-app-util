package com.techsophy.tsf.util.exception;

public class FileNameNotPresentException extends  RuntimeException
{

    final String errorCode;
    final String message;
    public FileNameNotPresentException(String errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
        this.message=message;
    }
}
