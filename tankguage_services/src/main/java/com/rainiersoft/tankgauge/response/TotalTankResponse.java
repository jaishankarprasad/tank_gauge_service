package com.rainiersoft.tankgauge.response;

import java.util.List;

import com.rainiersoft.tankgauge.pojo.TotalTankDetails;

public class TotalTankResponse 
{

	List<TotalTankDetails> list;
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
	
	public List<TotalTankDetails> getList() {
		return list;
	}
	public void setList(List<TotalTankDetails> list) {
		this.list = list;
	}

}
