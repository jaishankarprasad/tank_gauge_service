package com.rainiersoft.tankgauge.pojo;

import java.util.List;

import com.rainiersoft.tankgauge.entity.TankData;

public class ReportInputs {

	private String startDate;

	private String endDate;

	private String reportType;

	List<TankData> tankDataList ;

	public List<TankData> getTankDataList() {
		return tankDataList;
	}

	public void setTankDataList(List<TankData> tankDataList) {
		this.tankDataList = tankDataList;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}	

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

}
