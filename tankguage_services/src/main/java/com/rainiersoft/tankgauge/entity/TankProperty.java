package com.rainiersoft.tankgauge.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TK_PROPERTY")
public class TankProperty 
{		
	@Id
	@Column(name="Property_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int propertyId;

	@Column(name="Property_Name")
	public String propertyName;

	@Column(name="Property_DisplayName")
	public String propertyDisplayName;

	@Column(name="Property_Type")
	public String propertyType;	

	@Column(name="Register_StartAddress")
	public int registerStartAddress;

	@Column(name="Register_Count")
	public int registerCount;

	@Column(name="Suffix")
	public String suffix;

	@Column(name="Prefix")
	public String prefix;

	@Column(name="Alarm_Enabled")
	public boolean alarmEnabled;

	@Column(name="Normal_Start")
	public String normalStart;

	@Column(name="Normal_End")
	public String normalEnd;

	@Column(name="Low_Threshold")
	public String lowThreshold;

	@Column(name="High_Threshold")
	public String highThreshold;

	@Column(name="Critical_Threshold")
	public String criticalThreshold;

	@Column(name="Poll_interval")
	public int pollInterval;

	@Column(name="Scaling_Type")
	public String scalingType;

	@Column(name="Scaling_Value")
	public int scalingValue;	

	@Column(name="Tank_ID")
	public int tankId;

	public String getScalingType() {
		return scalingType;
	}

	public void setScalingType(String scalingType) {
		this.scalingType = scalingType;
	}

	public int getScalingValue() {
		return scalingValue;
	}

	public void setScalingValue(int scalingValue) {
		this.scalingValue = scalingValue;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getTankId() {
		return tankId;
	}

	public void setTankId(int tankId) {
		this.tankId = tankId;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyDisplayName() {
		return propertyDisplayName;
	}

	public void setPropertyDisplayName(String propertyDisplayName) {
		this.propertyDisplayName = propertyDisplayName;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public int getRegisterStartAddress() {
		return registerStartAddress;
	}

	public void setRegisterStartAddress(int registerStartAddress) {
		this.registerStartAddress = registerStartAddress;
	}

	public int getRegisterCount() {
		return registerCount;
	}

	public void setRegisterCount(int registerCount) {
		this.registerCount = registerCount;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public boolean isAlarmEnabled() {
		return alarmEnabled;
	}

	public void setAlarmEnabled(boolean alarmEnabled) {
		this.alarmEnabled = alarmEnabled;
	}

	public String getNormalStart() {
		return normalStart;
	}

	public void setNormalStart(String normalStart) {
		this.normalStart = normalStart;
	}

	public String getNormalEnd() {
		return normalEnd;
	}

	public void setNormalEnd(String normalEnd) {
		this.normalEnd = normalEnd;
	}

	public String getLowThreshold() {
		return lowThreshold;
	}

	public void setLowThreshold(String lowThreshold) {
		this.lowThreshold = lowThreshold;
	}

	public String getHighThreshold() {
		return highThreshold;
	}

	public void setHighThreshold(String highThreshold) {
		this.highThreshold = highThreshold;
	}

	public String getCriticalThreshold() {
		return criticalThreshold;
	}

	public void setCriticalThreshold(String criticalThreshold) {
		this.criticalThreshold = criticalThreshold;
	}

	public int getPollInterval() {
		return pollInterval;
	}

	public void setPollInterval(int pollInterval) {
		this.pollInterval = pollInterval;
	}	
}