package com.rainiersoft.tankgauge.response;

import java.util.List;

import com.rainiersoft.tankgauge.pojo.TankAlarmVariables;


public class AlarmResponse {


	List<TankAlarmVariables> list;	
	String message;
	boolean status;	
	
	public List<TankAlarmVariables> getList() {
		return list;
	}
	public void setList(List<TankAlarmVariables> list) {
		this.list = list;
	}
	
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
