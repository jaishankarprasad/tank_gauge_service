package com.rainiersoft.tankgauge.license;

import java.util.Date;

public class RSLicenseValidator
{

	private RSLicenseCore licCore = null;
	
	private String licenseFile;
	
	
	public String getLicenseFile() {
		return licenseFile;
	}

	public void setLicenseFile(String licenseFile) {
		this.licenseFile = licenseFile;
	}

	public RSLicenseValidator (String licFile)
	{
		this.licenseFile = licFile;
		this.licCore = new RSLicenseCore (licFile);
	}
	
	private RSLicense getLicenseObject () throws Exception
	{
		return licCore.createLicenseObjectFromFile(licenseFile);
	}
	public boolean isValidTags(int tags)  throws Exception
	{
		return getLicenseObject().getNoOfTags() > tags ? true : false; 
 	}
	
	public boolean isValidDuration() throws Exception
	{
		Date currentDate = new Date ();
        return (currentDate.compareTo(getLicenseObject().getValidTill()) > 0) ? true: false ; 		
	}
	
	public boolean isValidNoOfDevices(int devices) throws Exception
	{
		
		return (getLicenseObject().getNoOfDevices() > devices ? true : false);
	
	}
	public boolean isValidMachine(String mac) throws Exception
	{
		return (getLicenseObject().getMac().equalsIgnoreCase(mac)) ? true :false;
 	}
}
