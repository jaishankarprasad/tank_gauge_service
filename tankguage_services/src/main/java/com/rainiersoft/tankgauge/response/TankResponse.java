package com.rainiersoft.tankgauge.response;

import java.util.List;

import com.rainiersoft.tankgauge.entity.TankData;

public class TankResponse {

	List<TankData> list;
	String message;
	boolean status;

	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public List<TankData> getList() {
		return list;
	}
	public void setList(List<TankData> list) {
		this.list = list;
	}
}
