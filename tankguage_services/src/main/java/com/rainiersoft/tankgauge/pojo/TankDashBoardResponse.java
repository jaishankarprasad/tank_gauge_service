package com.rainiersoft.tankgauge.pojo;

import java.util.List;

public class TankDashBoardResponse 
{
	String message;
	boolean status;
	List<DashBoard> dashBoardValues;


	public List<DashBoard> getDashBoardValues() {
		return dashBoardValues;
	}
	public void setDashBoardValues(List<DashBoard> dashBoardValues) {
		this.dashBoardValues = dashBoardValues;
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
