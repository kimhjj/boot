package com.kimhj.helloboot.exceptions;

import java.util.List;

import org.springframework.validation.FieldError;

public class ApiException extends RuntimeException {
// 밸리데이션 체크를 위한 클래스
	
	private List<FieldError> fieldErrors;

	
	public ApiException(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	
	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}
