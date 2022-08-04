package com.techsophy.tsf.util.exception;

import com.techsophy.tsf.util.model.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleFormException(ProjectNotFoundException ex, WebRequest request)
    {
        ApiErrorResponse errorDetails = new ApiErrorResponse(Instant.now(), ex.getMessage(), ex.errorCode,
                HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> entityNotFoundException(EntityIdNotFoundException ex, WebRequest request)
    {
        ApiErrorResponse errorDetails = new ApiErrorResponse(Instant.now(), ex.getMessage(), ex.errorCode,
                HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserDetailsIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> userDetailsNotFoundException(UserDetailsIdNotFoundException ex, WebRequest request)
    {
        ApiErrorResponse errorDetails = new ApiErrorResponse(Instant.now(), ex.getMessage(), ex.errorCode,
                HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiErrorResponse> invalidInputException(InvalidInputException ex, WebRequest request)
    {
        ApiErrorResponse errorDetails = new ApiErrorResponse(Instant.now(), ex.getMessage(), ex.errorCode,
                HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleProfilePictureNotPresentException(NoDataFoundException ex, WebRequest request)
    {
        ApiErrorResponse errorDetails = new ApiErrorResponse(Instant.now(), ex.message, ex.errorCode,
                HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ApiErrorResponse> handleProfilePictureNotPresentException(InvalidDataException ex, WebRequest request)
    {
        ApiErrorResponse errorDetails = new ApiErrorResponse(Instant.now(), ex.message, ex.errorCode,
                HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UploadedUserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleProfilePictureNotPresentException(UploadedUserNotFoundException ex, WebRequest request)
    {
        ApiErrorResponse errorDetails = new ApiErrorResponse(Instant.now(), ex.message, ex.errorCode,
                HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileNameNotPresentException.class)
    public ResponseEntity<ApiErrorResponse> handleProfilePictureNotPresentException(FileNameNotPresentException ex, WebRequest request)
    {
        ApiErrorResponse errorDetails = new ApiErrorResponse(Instant.now(), ex.message, ex.errorCode,
                HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(ExternalServiceErrorException.class)
    public ResponseEntity<ApiErrorResponse> externalServiceErrorException(ExternalServiceErrorException ex, WebRequest request)
    {
        ApiErrorResponse errorDetails = new ApiErrorResponse(Instant.now(), ex.message, ex.errorCode,
                HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(UnsupportedFileFormatException.class)
    public ResponseEntity<ApiErrorResponse> unsupportedFileFormatException(UnsupportedFileFormatException ex, WebRequest request)
    {
        ApiErrorResponse errorDetails = new ApiErrorResponse(Instant.now(), ex.message, ex.errorCode,
                HttpStatus.UNSUPPORTED_MEDIA_TYPE, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
}
