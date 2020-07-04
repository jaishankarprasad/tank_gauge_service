package com.rainiersoft.tankgauge.pojo;

import java.math.BigDecimal;
import java.util.List;

public class TotalTankDetails {

	int tankId;
	String name;
	String site;	
	BigDecimal latitude;
	BigDecimal longitude;
	String location;	
	String imageURL;		
	int devicedId;
	String productType;
	String status;
	String pollInterval;
	int propertyId;
	
	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public String getPollInterval() {
		return pollInterval;
	}

	public void setPollInterval(String pollInterval) {
		this.pollInterval = pollInterval;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String string) {
		this.status = string;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	List<DataAndAlarm> dataAndAlarm;
	
	public List<DataAndAlarm> getDataAndAlarm() {
		return dataAndAlarm;
	}

	public void setDataAndAlarm(List<DataAndAlarm> dataAndAlarm) {
		this.dataAndAlarm = dataAndAlarm;
	}

	public int getTankId() {
		return tankId;
	}

	public void setTankId(int tankId) {
		this.tankId = tankId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public int getDevicedId() {
		return devicedId;
	}

	public void setDevicedId(int devicedId) {
		this.devicedId = devicedId;
	}
}
