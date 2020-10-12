package com.capgemini.MovieService.beans;

public class ErrorResponse {

	private String errorMsg;
	private String code;
	
	public ErrorResponse() {
		super();
		// Default Constructor
	}

	public ErrorResponse(String errorMsg, String code) {
		super();
		this.errorMsg = errorMsg;
		this.code = code;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
}
