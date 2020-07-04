package com.rainiersoft.tankgauge.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TK_DATA")

public class TankData 
{
	@Column(name="Raw_DATA_Id")
	int rawDataId;

	@Id
	@Column(name="Data_ID")
	int dataId;	

	@Column(name="Tank_ID")
	int tankId;

	@Column(name="Property_Name")
	String propertyName;

	@Column(name="Property_Value")
	String propertyValue;

	@Column(name="Alarm_Qualified")
	boolean alarmQualified;

	@Column(name="Register_Value")
	String registerValue;

	@Column(name="Low_ThresholdQualified")
	boolean lowThresholdQualified;

	@Column(name="High_ThresholdQualified")
	boolean highThresholdQualified;

	@Column(name="Critical_ThresholdQualified")
	boolean criticalThresholdQualified;

	@Column(name="Last_Updated")
	Date lastUpdated;

	@Column(name="Property_ID")
	int propertyId;

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}




	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
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

	public void setRegisterValue(String registerValue) {
		this.registerValue = registerValue;
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


	public int getRawDataId() {
		return rawDataId;
	}

	public void setRawDataId(int rawDataId) {
		this.rawDataId = rawDataId;
	}


	/*	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append("rawDataId ="+rawDataId);

		sb.append("\n dataId ="+dataId);

		sb.append("\n tankId = "+tankId);
		sb.append("\n propertyName = "+propertyName);
		sb.append("\n propertyValue = "+propertyValue);
		sb.append("\n alarmQualified = "+alarmQualified);

		sb.append("\n registerValue = "+registerValue);


		sb.append("\n criticalThresholdQualified = "+criticalThresholdQualified);
		sb.append("\n highThresholdQualified = "+highThresholdQualified);
		sb.append("\n lowThresholdQualified = "+lowThresholdQualified);

		sb.append("\n lastUpdated = "+lastUpdated);

		return sb.toString();

	}*/

}
