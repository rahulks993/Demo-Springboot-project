package com.jeeves.sample.utils;

import com.jeeves.sample.enums.StatusEnum;

public class StatusResponse {
	private StatusEnum status;

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public StatusResponse(StatusEnum status) {
		super();
		this.status = status;
	}

}