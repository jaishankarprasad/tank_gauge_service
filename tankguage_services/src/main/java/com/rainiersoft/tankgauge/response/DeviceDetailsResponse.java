package com.rainiersoft.tankgauge.response;

public class DeviceDetailsResponse {

	
	String message;
	boolean status;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
}
