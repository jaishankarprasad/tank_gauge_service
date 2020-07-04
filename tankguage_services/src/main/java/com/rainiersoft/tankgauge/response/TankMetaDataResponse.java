package com.rainiersoft.tankgauge.response;

import java.util.List;

import com.rainiersoft.tankgauge.entity.TankMetaData;

public class TankMetaDataResponse {

	List<TankMetaData> list;
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
	
	public List<TankMetaData> getList() {
		return list;
	}
	public void setList(List<TankMetaData> list) {
		this.list = list;
	}
}
