package com.rainiersoft.tankgauge.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="devicedetails")
public class DeviceDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="devicename")
	private String deviceName;

	@Id
	@Column(name="deviceid")
	private int deviceid;

	@Column(name="protocol")
	private String protocol;

	@Column(name="rstype")
	private String rstype;
	
	@Column(name="comport")
	private String comport;
	
	@Column(name="ipaddress")
	private String ipaddress;
	
	@Column(name="baudrate")
	private String baudrate;
	
	@Column(name="parity")
	private String parity;
	
	@Column(name="stopbits")
	private String stopbits;
	
	@Column(name="databits")
	private String databits;
	
	@Column(name="refreshInterval")
	private int refreshInterval;
	
/*	@Column(name="tcpIP")
	private String tcpIP;*/
	
	@Column(name="port")
	private String port;

	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public int getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(int deviceid) {
		this.deviceid = deviceid;
	}

	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getRstype() {
		return rstype;
	}
	public void setRstype(String rstype) {
		this.rstype = rstype;
	}
	public String getComport() {
		return comport;
	}
	public void setComport(String comport) {
		this.comport = comport;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public String getBaudrate() {
		return baudrate;
	}
	public void setBaudrate(String baudrate) {
		this.baudrate = baudrate;
	}
	public String getParity() {
		return parity;
	}
	public void setParity(String parity) {
		this.parity = parity;
	}
	public String getStopbits() {
		return stopbits;
	}
	public void setStopbits(String stopbits) {
		this.stopbits = stopbits;
	}
	public String getDatabits() {
		return databits;
	}
	public void setDatabits(String databits) {
		this.databits = databits;
	}
	public int getRefreshInterval() {
		return refreshInterval;
	}
	public void setRefreshInterval(int refreshInterval) {
		this.refreshInterval = refreshInterval;
	}
/*	public String getTcpIP() {
		return tcpIP;
	}
	public void setTcpIP(String tcpIP) {
		this.tcpIP = tcpIP;
	}*/
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}


}
