package com.rainiersoft.tankgauge.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TK_METADATA")
public class TankMetaData
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Tank_ID")
	int tankId;

	@Column(name="Name")
	String name;

	@Column(name="Site")
	String site;

	@Column(name="Latitude")
	BigDecimal latitude;

	@Column(name="Longitude")
	BigDecimal longitude;

	@Column(name="Location")
	String location;

	@Column(name="ImageURL")
	String imageURL;

	@Column(name="Device_ID")
	int devicedId;

	@Column(name="Product_Type")
	String productType;

	@Column(name="Status")
	String status;

	@Column(name="Poll_Interval")
	String pollInterval;

	public String getPollInterval() {
		return pollInterval;
	}

	public void setPollInterval(String pollInterval) {
		this.pollInterval = pollInterval;
	}

/*	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="Tank_ID")*/
	
	@OneToMany(targetEntity=TankProperty.class, mappedBy="tankId", fetch=FetchType.EAGER)
	List<TankProperty> tankPropertyList;


	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}



	public int getDevicedId() {
		return devicedId;
	}

	public void setDevicedId(int devicedId) {
		this.devicedId = devicedId;
	}


	public List<TankProperty> getTankPropertyList() {
		return tankPropertyList;
	}

	public void setTankPropertyList(List<TankProperty> tankPropertyList) {
		this.tankPropertyList = tankPropertyList;
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

	public int getTankId() {
		return tankId;
	}

	public void setTankId(int tankId) {
		this.tankId = tankId;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
