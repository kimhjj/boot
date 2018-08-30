package com.kimhj.helloboot.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kimhj.helloboot.response.error.ApiError;

public class ApiResponse {
	
	private String status;
	private List<ApiError> error;
	
	
	public ApiResponse() {
		this.status = "OK";
		this.error = new ArrayList<>();
	}
	
	public ApiResponse(ApiError error) {
		this.status = "Failed";
		this.error = Arrays.asList(error);
	}
	
	public ApiResponse(List<ApiError> error) {
		this.status = "Failed";
		this.error = error;
	}
	
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<ApiError> getError() {
		return error;
	}
	
	public void setError(List<ApiError> error) {
		this.error = error;
	}
}
