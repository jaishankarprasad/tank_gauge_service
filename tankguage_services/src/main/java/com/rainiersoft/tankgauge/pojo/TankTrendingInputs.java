package com.rainiersoft.tankgauge.pojo;

import java.util.List;

public class TankTrendingInputs {

	String startDate;
	String endDate;
	
	List<InputID> idList;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<InputID> getIdList() {
		return idList;
	}

	public void setIdList(List<InputID> idList) {
		this.idList = idList;
	}
}
