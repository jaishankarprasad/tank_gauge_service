package com.rainiersoft.tankgauge.pojo;

import java.util.List;

public class DashBoard {
	
  int tankId;
  String tankName;
  List<PropertyDetails> propsList;
  
public int getTankId() {
	return tankId;
}
public void setTankId(int tankId) {
	this.tankId = tankId;
}
public String getTankName() {
	return tankName;
}
public void setTankName(String tankName) {
	this.tankName = tankName;
}
public List<PropertyDetails> getPropsList() {
	return propsList;
}
public void setPropsList(List<PropertyDetails> propsList) {
	this.propsList = propsList;
}


}
