package com.rainiersoft.tankgauge.pojo;

import java.util.Date;


public class DataAndAlarm {
	
	int dataId;		
	
	int tankId;
	
	String propertyName;
	
	String propertyValue;
	
	boolean alarmQualified;
	
	String registerValue;
	
	boolean lowThresholdQualified;
	
	boolean highThresholdQualified;
	
	boolean criticalThresholdQualified;
	
	int alarmId;
	
	int propertyId;
	
	String alarmType;
	
	boolean alarmAcknowledge;
	
	Date lastUpdated;
	
	int productType;
	

	public int getProductType() {
		return productType;
	}

	public void setProductType(int productType) {
		this.productType = productType;
	}

	public int getDataId() {
		return dataId;
	}

	public void setDataId(int dataId) {
		this.dataId = dataId;
	}

	public int getTankId() {
		return tankId;
	}

	public void setTankId(int tankId) {
		this.tankId = tankId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public boolean isAlarmQualified() {
		return alarmQualified;
	}

	public void setAlarmQualified(boolean alarmQualified) {
		this.alarmQualified = alarmQualified;
	}

	public String getRegisterValue() {
		return registerValue;
	}

	public void setRegisterValue(String d) {
		this.registerValue = d;
	}

	public boolean isLowThresholdQualified() {
		return lowThresholdQualified;
	}

	public void setLowThresholdQualified(boolean lowThresholdQualified) {
		this.lowThresholdQualified = lowThresholdQualified;
	}

	public boolean isHighThresholdQualified() {
		return highThresholdQualified;
	}

	public void setHighThresholdQualified(boolean highThresholdQualified) {
		this.highThresholdQualified = highThresholdQualified;
	}

	public boolean isCriticalThresholdQualified() {
		return criticalThresholdQualified;
	}

	public void setCriticalThresholdQualified(boolean criticalThresholdQualified) {
		this.criticalThresholdQualified = criticalThresholdQualified;
	}

	public int getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(int alarmId) {
		this.alarmId = alarmId;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public boolean isAlarmAcknowledge() {
		return alarmAcknowledge;
	}

	public void setAlarmAcknowledge(boolean alarmAcknowledge) {
		this.alarmAcknowledge = alarmAcknowledge;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}


}
