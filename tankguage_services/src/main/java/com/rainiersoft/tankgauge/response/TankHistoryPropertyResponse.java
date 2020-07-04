package com.rainiersoft.tankgauge.response;

import java.util.List;

import com.rainiersoft.tankgauge.entity.TankHistoryData;

public class TankHistoryPropertyResponse 
{
	List<TankHistoryData> list;
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
	
	public List<TankHistoryData> getList() {
		return list;
	}
	public void setList(List<TankHistoryData> list) {
		this.list = list;
	}

}
