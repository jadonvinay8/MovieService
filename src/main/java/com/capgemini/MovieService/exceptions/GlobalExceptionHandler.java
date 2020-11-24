package com.capgemini.MovieService.exceptions;

import com.capgemini.MovieService.dto.ErrorResponse;
import com.capgemini.MovieService.dto.MicroserviceResponse;
import com.capgemini.MovieService.utilities.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<MicroserviceResponse> movieNotFound(MovieNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("No movie was found with given id", e.getMessage(), "404");
        MicroserviceResponse response = ResponseBuilder.build(HttpStatus.NOT_FOUND.value(), null, errorResponse);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<MicroserviceResponse> nullPointer(NullPointerException e) {
        ErrorResponse errorResponse = new ErrorResponse("Null Values are not allowed in payload", e.getMessage(), "400");
        MicroserviceResponse response = ResponseBuilder.build(HttpStatus.BAD_REQUEST.value(), null, errorResponse);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MicroserviceResponse> invalidInputPayload(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse("Invalid Payload Supplied, check for nulls/blanks", e.getMessage(), "400");
        MicroserviceResponse response = ResponseBuilder.build(HttpStatus.BAD_REQUEST.value(), null, errorResponse);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<MicroserviceResponse> invalidInput(ConstraintViolationException e) {
        ErrorResponse errorResponse = new ErrorResponse("Constraints violated, check for nulls/blanks", e.getMessage(), "400");
        MicroserviceResponse response = ResponseBuilder.build(HttpStatus.BAD_REQUEST.value(), null, errorResponse);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MicroserviceResponse> nullPayload(HttpMessageNotReadableException e) {
        ErrorResponse errorResponse = new ErrorResponse("Invalid request body supplied", e.getMessage(), "400");
        MicroserviceResponse response = ResponseBuilder.build(HttpStatus.BAD_REQUEST.value(), null, errorResponse);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MicroserviceResponse> exception(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("Some unknown error occurred", e.getMessage(), "500");
        MicroserviceResponse response = ResponseBuilder.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, errorResponse);
        return ResponseEntity.ok(response);
    }

}