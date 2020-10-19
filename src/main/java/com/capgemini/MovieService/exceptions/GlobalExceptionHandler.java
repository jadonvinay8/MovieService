package com.capgemini.MovieService.exceptions;

import com.capgemini.MovieService.beans.ErrorResponse;
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
	public ResponseEntity<ErrorResponse> movieNotFound(MovieNotFoundException e) {
		return new ResponseEntity<>(new ErrorResponse("No movie was found with given id",
				"404", e.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorResponse> nullPointer(NullPointerException e) {
		return new ResponseEntity<>(new ErrorResponse("Null Values are not allowed in payload",
				"400", e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> invalidInputPayload(MethodArgumentNotValidException e) {
		return new ResponseEntity<>(new ErrorResponse("Invalid Payload Supplied, check for nulls/blanks",
				"400", e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> invalidInput(ConstraintViolationException e) {
		return new ResponseEntity<>(new ErrorResponse("Constraints violated, check for nulls/blanks",
				"400",  e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> nullPayload(HttpMessageNotReadableException e) {
		return new ResponseEntity<>(new ErrorResponse("Request Body not supplied",
				"400",  e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exception(Exception e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
		return new ResponseEntity<>(new ErrorResponse("Some unknown error occurred",
				"500", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}