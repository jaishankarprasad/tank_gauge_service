package com.rainiersoft.tankgauge.pojo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.rainiersoft.tankgauge.util.PropertyLoader;

public class ReportPropertiesLoader {

	private static Logger logger =Logger.getLogger(ReportPropertiesLoader.class);

	public HashMap<String,String> mapper = new HashMap<String,String>();
	
	public void loadProperties (String propFile) 
	{
		try 
		{
			System.out.println(new File(".").getAbsolutePath());
			File file = new File(propFile);
			System.out.println(file.getAbsolutePath());
			FileInputStream fileInput = new FileInputStream(file);
			load(fileInput);
			fileInput.close();
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

		if (null == mapper || null == searchKey)
		{
			return null;
		}

		return mapper.get(searchKey);
	}

	public static void main(String[] args) {

		String propFile="InventoryReport.properties";
		ReportPropertiesLoader pl = new ReportPropertiesLoader();
		pl.loadProperties(propFile);
		String name =pl.getValue("GOV");
		System.out.println(name);
	}

	private void load(FileInputStream input)
	{
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
		String line = null;
		try 
		{
			line = bufferedReader.readLine();


			while( line != null)
			{
				String [] token = line.split("=");
				
				if(token != null && token.length == 2)
				{
					System.out.println(token[0]);
					System.out.println(token[1]);
					
					mapper.put(token[0], token[1]);
				}
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}


	}

}
