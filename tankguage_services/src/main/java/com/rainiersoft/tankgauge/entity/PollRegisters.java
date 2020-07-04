package com.rainiersoft.tankgauge.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TK_POLLREGISTERS")
public class PollRegisters 
{

	@Column(name="Tank_ID")
	int tankId;
	
	@Id
	@Column(name="Property_ID")
	int propertyId;
	
	@Column(name="Property_Type")
	String propertyType;
	
	@Column(name="Register_StartAddress")
	int registerStartAddress;
	
	@Column(name="Register_Count")
	int registerCount;
	
	@Column(name="Poll_Interval")
	int pollInterval;

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

	public int getPollInterval() {
		return pollInterval;
	}

	public void setPollInterval(int pollInterval) {
		this.pollInterval = pollInterval;
	}
	
}
