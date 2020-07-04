package com.rainiersoft.tankgauge.license;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.rainiersoft.tankgauge.license.*;


public class TestLic 
{
	public String txt = "5TYZG-3XCLX-N3SRT-ZNWAB-L6WJA";
	
	final static Logger logger=Logger.getLogger(TestLic.class);

    private static final String licFile = System.getProperty("user.home") + File.separator + "rslicense.txt";

	
	public static void main (String args[]) throws Exception
	{
		RSLicenseCore licGen = new RSLicenseCore ();

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
 
		
		RSLicenseFileGenerator rsfileGenerator  = new RSLicenseFileGenerator (licFile);
		
		rsfileGenerator.GenerateLicenseByDate (date1);
		logger.info("\nLicense tested");
		System.out.println("\nLicense tested");
		
		RSLicenseValidator validator = new RSLicenseValidator(licFile);
		validator.isValidNoOfDevices(5);
		logger.info("\nLicense validated");
		System.out.println("\nLicense validated");
		
		

	}
	

}
