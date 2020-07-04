package com.rainiersoft.tankgauge.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyLoader 
{
	private Properties properties = null;
    private static Logger logger =Logger.getLogger(PropertyLoader.class);
	public void loadProperties (String propFile) 
	{
		try 
		{
			System.out.println(new File(".").getAbsolutePath());
			File file = new File(propFile);
			System.err.println(file.getAbsolutePath());
			FileInputStream fileInput = new FileInputStream(file);
			//already declared
		    properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			Enumeration enuKeys = properties.keys();
			while (enuKeys.hasMoreElements())
			{
				String key = (String) enuKeys.nextElement();
				System.out.println(key);
				String value = properties.getProperty(key);
				System.out.println(value);
			}
		} 
		catch (FileNotFoundException e)
		{
			logger.error(e);
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	public String getValue (String searchKey)
	{
		String value = null;
		
		if (null == properties || null == searchKey)
		{
			return null;
		}
		
		Enumeration enuKeys = properties.keys();
		
		while (enuKeys.hasMoreElements())
		{
			String key = (String) enuKeys.nextElement();
			if (searchKey.equalsIgnoreCase(key))
			{
				value = properties.getProperty(key);
				break;
			}
		}		
		return value;
	}
	
	public static void main(String[] args) {
		
		String propFile="InventoryReport.properties";
		PropertyLoader pl=new PropertyLoader();
		pl.loadProperties(propFile);
		String name =pl.getValue("PRODUCT LEVEL");
		System.out.println(name);
	}
}