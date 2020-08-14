package com.jeeves.sample.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jeeves.sample.enums.StatusEnum;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

	private StatusEnum status;
	private String reason;
	private String communication;
	public ApiResponse()
	{

	}

	public String getCommunication() {
		return communication;
	}
	public void setCommunication(String communication) {
		this.communication = communication;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	public ApiResponse(StatusEnum status, String reason) {
		super();
		this.status = status;
		this.reason = reason;
	}

}
