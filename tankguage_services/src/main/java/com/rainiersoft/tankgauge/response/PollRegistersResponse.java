package com.rainiersoft.tankgauge.response;

import java.util.List;

import com.rainiersoft.tankgauge.entity.PollRegisters;


public class PollRegistersResponse
{

	List<PollRegisters> list;	
	String message;
	boolean status;	
	
	public List<PollRegisters> getList() {
		return list;
	}
	public void setList(List<PollRegisters> list) {
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
