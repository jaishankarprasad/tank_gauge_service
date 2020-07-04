package com.rainiersoft.tankgauge.pojo;

public class TankRegister
{
	int tankId;
	
	int startAddress;
	
	int noOfRegisters;
	
	Object value;
	
	String dataType;
	
	int propertyId;

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

	public int getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(int startAddress) {
		this.startAddress = startAddress;
	}

	public int getNoOfRegisters() {
		return noOfRegisters;
	}

	public void setNoOfRegisters(int noOfRegisters) {
		this.noOfRegisters = noOfRegisters;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
}
