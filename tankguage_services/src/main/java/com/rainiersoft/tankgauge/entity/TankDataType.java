package com.rainiersoft.tankgauge.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TK_DATATYPE")
public class TankDataType 
{

	@Id
	@Column(name="DataType_ID")
	int dataTypeId;
	
	@Column(name="Property_Type")
	String propertyType;
	
	@Column(name="DisplayName")
	String displayName;

	@Column(name="Register_Count")
	int registerCount;
	
	public int getRegisterCount() {
		return registerCount;
	}

	public void setRegisterCount(int registerCount) {
		this.registerCount = registerCount;
	}

	public int getDataTypeId() {
		return dataTypeId;
	}

	public void setDataTypeId(int dataTypeId) {
		this.dataTypeId = dataTypeId;
	}



	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
