package com.kimhj.helloboot.response.error;

public interface ApiErrors {
	
	ApiError MISSING_REQUIRE_ERROR = new ApiError("1000", "%s is mandatory field.");
	ApiError VALIDATION_FAILED = new ApiError("1001", "%s prevent %s policy.");
	ApiError NO_DATA = new ApiError("1002", "Data is empty.");
	ApiError INTERNAL_SERVER_ERROR = new ApiError("9000", "Unknown Error.");
	
}
