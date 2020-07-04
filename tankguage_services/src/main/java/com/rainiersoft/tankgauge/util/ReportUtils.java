package com.rainiersoft.tankgauge.util;

import java.util.HashMap;

public class ReportUtils {
	
	public String propertyFile = "InventoryReport.properties";
	
	PropertyLoader pl=new PropertyLoader();
	
	public HashMap<String,String> propertyCategoryMapper = new HashMap<String,String>();
	
	public ReportUtils()
	{
		pl.loadProperties(propertyFile);
	}
	
	public String getCategoryByPropertyName(String propertyName)
	{	
		return pl.getValue(propertyName);
	}

}
