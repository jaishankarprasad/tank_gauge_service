package com.rainiersoft.tankgauge.license;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.Key;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

 
public class RSLicense
{

	private String mac = null;
	private String dateStr = null;
	private Date activationDate = null;
	private Date validTill = null;
	private int duration = 30;
	private int noOfDevices = 2;
	private int noOfTags = 5;
	
	public String version = "1.0";
	public String product = "RSAUTOMATION";
	private  String privateKey = "ABC";

	private String keyFile = null;
	
	private String licenseFile = null;
	
	public Date getValidTill() {
		return validTill;
	}
	public void setValidTill(Date validTill) {
		this.validTill = validTill;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getKeyFile() {
		return keyFile;
	}
	public void setKeyFile(String keyFile) {
		this.keyFile = keyFile;
	}
	public String getLicenseFile() {
		return licenseFile;
	}
	public void setLicenseFile(String licenseFile) {
		this.licenseFile = licenseFile;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public Date getActivationDate() {
		return activationDate;
	}
	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getNoOfDevices() {
		return noOfDevices;
	}
	public void setNoOfDevices(int noOfDevices) {
		this.noOfDevices = noOfDevices;
	}

	public int getNoOfTags ()
	{
		return noOfTags;
	}
	
	public void setNoOfTags (int noOfTags)
	{
		this.noOfTags = noOfTags;
	}
	
	
	
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ();
		
		sb.append("version=");
		sb.append(version);
		sb.append(";");
		sb.append("product=");
		sb.append(product);
		sb.append(";");
		sb.append("privateKey=");
		sb.append(privateKey);
		sb.append(";");
		sb.append("mac=");
		sb.append(mac);
		sb.append(";");
		sb.append("noOfDevices=");
		sb.append(noOfDevices);
		sb.append(";");
		sb.append("noOfTags=");
		sb.append(noOfTags);
		sb.append(";");
		sb.append("duration=");
		sb.append(duration);
		sb.append(";");
		sb.append("activationDate=");
		sb.append(getDateString(activationDate));
		sb.append(";");
		
		return sb.toString();  		
	}
	
	private String getCurrentDate ()
	{
		Date date = new Date();
		return getDateString(date);
	}
	
	private String getDateString(Date date )
	{
		if (null == date)
			return getCurrentDate();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		return dateFormat.format(date);		
	}
	
	
 }
