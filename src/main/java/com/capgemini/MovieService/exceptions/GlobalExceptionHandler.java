package com.capgemini.MovieService.exceptions;

import com.capgemini.MovieService.beans.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MovieNotFoundException.class)
	public ResponseEntity<ErrorResponse> movieNotFound(MovieNotFoundException e) {
		return new ResponseEntity<>(new ErrorResponse("No movie was found with given id", "404"),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorResponse> nullPointer(NullPointerException e) {
		return new ResponseEntity<>(new ErrorResponse("Null Values are not allowed in payload", "400"),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exception(Exception e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
		return new ResponseEntity<>(new ErrorResponse("Some unknown error occurred", "500"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}