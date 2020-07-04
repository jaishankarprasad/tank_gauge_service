package com.rainiersoft.tankgauge.response;

import java.util.ArrayList;
import java.util.List;

import com.rainiersoft.tankgauge.pojo.PropertyValue;
import com.rainiersoft.tankgauge.pojo.TankPropertyInfo;

public class TankTrendResponse
{


	String message;
	boolean status;
	List<TankPropertyInfo> tankPropsList;
	List<ArrayList<PropertyValue>> propDataList;
//	List<PropertyValueList> propDataList;

	//List<PropertyValue> value;


	public String getMessage() {
		return message;
	}
	public List<ArrayList<PropertyValue>> getPropDataList() {
		return propDataList;
	}
	public void setPropDataList(List<ArrayList<PropertyValue>> propDataList) {
		this.propDataList = propDataList;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public List<TankPropertyInfo> getTankPropsList() {
		return tankPropsList;
	}
	public void setTankPropsList(List<TankPropertyInfo> tankPropsList) {
		this.tankPropsList = tankPropsList;
	}

}
