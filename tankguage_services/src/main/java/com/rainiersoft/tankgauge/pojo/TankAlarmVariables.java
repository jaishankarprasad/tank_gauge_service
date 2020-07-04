package com.rainiersoft.tankgauge.pojo;

import java.util.Date;

public class TankAlarmVariables 
{

	int alarmId;

	int propertyId;

	String alarmType;

	boolean alarmAcknowledge;

	int tankId;
	
	String propertyName;

	int propertyValue;

	Date lastUpdated;
	
	String tankName;

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

	public int getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(int propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getTankName() {
		return tankName;
	}

	public void setTankName(String tankName) {
		this.tankName = tankName;
	}
}

