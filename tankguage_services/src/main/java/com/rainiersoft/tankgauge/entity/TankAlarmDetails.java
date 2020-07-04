package com.rainiersoft.tankgauge.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TK_ALARM")
public class TankAlarmDetails {
	
	@Id
	@Column(name="Alarm_ID")
	int alarmId;
	
	@Column(name="Property_ID")
	int propertyId;
	
	@Column(name="Alarm_Type")
	String alarmType;
	
	@Column(name="Alarm_Acknowledge")
	boolean alarmAcknowledge;

	@Column(name="Tank_ID")
	int tankId;
	
	@Column(name="Property_Name")
	String propertyName;
	
	@Column(name="Property_Value")
	int propertyValue;
	
	@Column(name="Last_Updated")
	Date lastUpdated;

	//getters and setters
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
}
