package com.jeeves.sample.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.jeeves.sample.enums.ErrorTypeEnum;
import com.jeeves.sample.enums.StatusEnum;

@Component
public class ErrorResponseUtils {

	public ResponseEntity<Object> errorResponse(ErrorTypeEnum error,
			HttpStatus status, Exception e) {
		ResponseEntity<Object> response = null;
		switch (error) {
		case MOBILE_LENGTH_ERROR:
			response = ResponseEntity.status(status).body(
					new ApiResponse(StatusEnum.failure,
							"Mobile Number Should be of 10 digits"));
			break;
		case MOBILE_EMAIL_NOT_UNIQUE:
			response = ResponseEntity.status(status).body(
					new ApiResponse(StatusEnum.failure,
							"Mobile Number and EmailId Should be unique"));
			break;
		case MOBILE_NOT_UNIQUE:
			response = ResponseEntity.status(status).body(
					new ApiResponse(StatusEnum.failure,
							"Mobile Number Should be unique"));
			break;
		case EMAIL_NOT_UNIQUE:
			response = ResponseEntity.status(status).body(
					new ApiResponse(StatusEnum.failure,
							"EmailId Should be unique"));
			break;
		case NO_DATA_FOUND:
			response = ResponseEntity.status(status).body(
					new ApiResponse(StatusEnum.failure, "Not data Found"));
			break;
		case UNKNOWN:
			response = ResponseEntity.status(status).body(
					new ApiResponse(StatusEnum.failure, e.getMessage()));
			break;
		default:
			break;
		}

		return response;

	}
}
