package com.techsophy.tsf.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDataException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	final String errorCode;
	final String message;
	public InvalidDataException(String errorCode, String message)
	{
		super(message);
		this.errorCode = errorCode;
		this.message=message;
	}

}
