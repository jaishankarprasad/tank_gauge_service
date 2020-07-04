package com.rainiersoft.tankgauge.license;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

// This class is used to generate the license file for customer.
public class RSLicenseFileGenerator 
{
	final static Logger logger=Logger.getLogger(RSLicenseFileGenerator.class);

    private String licFile = System.getProperty("user.home") + File.separator + "rsautomation.txt";
    
	private RSLicenseCore licCore = null;
    
	
	public String getLicFile() {
		return licFile;
	}

	public void setLicFile(String licFile) {
		this.licFile = licFile;
	}

    public RSLicenseFileGenerator (String file)
    {
    	licFile = file;
    	licCore = new RSLicenseCore ();
    }

    public String GneratePerpetualLicense (String mac) throws Exception
    {
    	this.licCore.getLicObj().setMac(mac);
    	this.licCore.getLicObj().setNoOfTags(-1);
    	return licCore.createLicenseFile();
    }
    
    public String GenerateLicenseByDate(Date validUntil) throws Exception
    {
    	this.licCore.getLicObj().setValidTill(validUntil);
    	return licCore.createLicenseFile();
    }
    
    public String GenerateLicenseByDevices(int noOfDevices, Date validUntil) throws Exception
    {
    	this.licCore.getLicObj().setNoOfDevices(noOfDevices);
    	this.licCore.getLicObj().setValidTill(validUntil);
    	return licCore.createLicenseFile();
    }
	 
	public static void main (String args[])
	{
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		
		Date date = Calendar.getInstance().getTime();
		
	    Date date1 = null;
		try
		{
			date1 = new SimpleDateFormat("yyyy/MM/dd").parse(dateFormat.format(date));
		}
		catch (ParseException e)
		{
			logger.error(e);
			e.printStackTrace();
		}  
					
	}
	

}
