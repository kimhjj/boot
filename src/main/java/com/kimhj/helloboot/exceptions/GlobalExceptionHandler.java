package com.kimhj.helloboot.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kimhj.helloboot.response.ApiResponse;
import com.kimhj.helloboot.response.error.ApiError;
import com.kimhj.helloboot.response.error.ApiErrors;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

	// 애플리케이션에서 발생하는 모든 runtimeException을 처리하겠다.
	@ExceptionHandler(RuntimeException.class)
	public ApiResponse getApiErrorHandler(RuntimeException e) {
		
		if(e instanceof ApiException) {
			ApiException apiException = (ApiException) e;
			List<FieldError> fieldErrors = apiException.getFieldErrors();
			List<ApiError> errorMessages = 
						fieldErrors.stream()
									.map(error -> {
										String validationType = error.getCode();
										if(validationType.equals("NotEmpty")) {
											ApiError apiError = ApiErrors.MISSING_REQUIRE_ERROR;
											String message = apiError.getMessage();
											String field = error.getField();
											
											message = String.format(message, field);
											return new ApiError(apiError.getCode(), message);
										} else {
											ApiError apiError = ApiErrors.VALIDATION_FAILED;
											String message = apiError.getMessage();
											String field = error.getField();
											String defaultMessage = error.getDefaultMessage();
											
											message = String.format(message
																	, field
																	, validationType +"("+defaultMessage+")");
											return new ApiError(apiError.getCode(), message);
										}
									})
									.collect(Collectors.toList());
			return new ApiResponse(errorMessages);
			
		} else if(e instanceof DataAccessException) {
			return new ApiResponse(ApiErrors.NO_DATA);				// Data is empty.
		}
		
		// else
		return new ApiResponse(ApiErrors.INTERNAL_SERVER_ERROR);	// Unknown Error.
	}
	
}
