package com.rainiersoft.tankgauge.entity;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TK_RAWDATA")
public class TankRawData implements Serializable
{
	
	@Id
	@Column(name="Raw_DATA_Id")
	int rawDataId;
	
	public int getRawDataId() {
		return rawDataId;
	}

	public void setRawDataId(int rawDataId) {
		this.rawDataId = rawDataId;
	}
	
	@Column(name="Tank_ID")
	int tankId;
	
	@Column(name="Property_ID")
	int propertyId;

	@Id
	@Column(name="Register_ID")
	int registerID;

	
	@Column(name="Register_Count")
	int registerCount;

	
	@Column(name="Register_StartAddress")
	int registerStartAddress;

	
	@Column(name="Register_Value")
	String registerValue;

	
	@Column(name="Last_Update")
	Date lastUpdate;

	
	@Column(name="Data_Quality")
	boolean dataQuality;

	
	@Column(name="Register_type")
	String registerType;


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



	public int getRegisterID() {
		return registerID;
	}


	public void setRegisterID(int registerID) {
		this.registerID = registerID;
	}


	public int getRegisterCount() {
		return registerCount;
	}


	public void setRegisterCount(int registerCount) {
		this.registerCount = registerCount;
	}


	public int getRegisterStartAddress() {
		return registerStartAddress;
	}

	public void setRegisterStartAddress(int registerStartAddress) {
		this.registerStartAddress = registerStartAddress;
	}


	public String getRegisterValue() {
		return registerValue;
	}


	public void setRegisterValue(String registerValue) {
		this.registerValue = registerValue;
	}


	public Date getLastUpdate() {
		return lastUpdate;
	}


	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	public boolean isDataQuality() {
		return dataQuality;
	}


	public void setDataQuality(boolean dataQuality) {
		this.dataQuality = dataQuality;
	}


	public String getRegisterType() {
		return registerType;
	}


	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}


}
