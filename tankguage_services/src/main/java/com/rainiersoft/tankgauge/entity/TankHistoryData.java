package com.rainiersoft.tankgauge.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="TK_HISTORY_DATA")
public class TankHistoryData implements Serializable
{
	@Id
	@Column(name="TkHIstory_Id")
	int tkHistoryId;

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

	@Column(name="Reserve1")
	String reserve1;

	@Column(name="Reserve2")
	String reserve2;

	@Column(name="Reserve3")
	String reserve3;

	
	public int getTkHistoryId() {
		return tkHistoryId;
	}

	public void setTkHistoryId(int tkHistoryId) {
		this.tkHistoryId = tkHistoryId;
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

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public String getReserve1() {
		return reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public String getReserve2() {
		return reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public String getReserve3() {
		return reserve3;
	}

	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}

}
