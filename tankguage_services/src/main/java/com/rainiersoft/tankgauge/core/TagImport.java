package com.rainiersoft.tankgauge.core;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.rainiersoft.tankgauge.util.PropertyLoader;


public class TagImport 
{
	final static Logger logger=Logger.getLogger(TagImport.class);
	public static final HashMap<String, String> dataTypeMap = new HashMap<String, String>();
	
	static 
 	{
		dataTypeMap.put("INT", "16bit Int");
		dataTypeMap.put("DINT", "32bit Int");
		dataTypeMap.put("REAL", "32bit Float");
		dataTypeMap.put("DOUBLE", "64bit Float");
		dataTypeMap.put("BOOL", "1bit boolean");
		dataTypeMap.put("STRING", "String"); 		
 	}
	
	public boolean GenerateOnlyFile = true;
    public String TemplateFile = "";
    public String TableName="";
    public String DeviceName="";
    public String OperationColumn="";
    public int deviceID = 18;
    public String SQLFile="";
    
    DBConnector dbCon =null;   
    PropertyLoader props = new PropertyLoader ();
    
    public TagImport ()
    {
    	
    }
    
    public void loadConfig (String propFile)
    {
    	props.loadProperties(propFile);
    	GenerateOnlyFile = Boolean.parseBoolean("GenerateOnlyFile");
    	TemplateFile=props.getValue("TemplateFile");
    	TableName=props.getValue("TableName");
    	DeviceName = props.getValue("DeviceName");
    	OperationColumn = props.getValue("OperationColumn");
        deviceID = Integer.parseInt(props.getValue("deviceID"));
    	SQLFile = props.getValue("SQLFile");
    	
    }

    private String GenerateInsertLine(String tagId, String tagName, String tagType)
    {
    	StringBuffer line = new StringBuffer ("insert into");
    	line.append(" ");
    	line.append(TableName);
    	line.append("(");
    	line.append("operation");
    	line.append(",");
    	line.append("deviceid");
    	line.append(",");
    	line.append("tagtype");
    	line.append(",");
    	line.append("devicename");
    	line.append(",");
    	line.append("tagid");
    	line.append(",");
    	line.append("id");
    	line.append(",");
    	line.append("tagname");
    	line.append(")");

    	line.append("values");
    	line.append("(");
    	line.append("'"+OperationColumn+"'");
    	
    	line.append(",");
    	line.append(deviceID);
    	line.append(",");
    	line.append("'"+dataTypeMap.get(tagType)+"'");
    	line.append(",");
    	line.append("'"+DeviceName+"'");
    	line.append(",");
    	line.append("'"+tagId+"'");
    	line.append(",");
    	line.append(0);
    	line.append(",");
    	line.append("'"+tagName+"'");
    	line.append(")");
    	line.append(";");
    	
    	return line.toString();
   	
    }
    private void GenerateSQLFile (String templateFile)
    {
    	File file = new File(templateFile);
		FileReader fileReader = null;
		FileWriter fileWriter = null;
		BufferedWriter bw = null;
		BufferedReader bufferedReader = null; 
		
		try
		{
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
		 
			while ((line = bufferedReader.readLine()) != null)
			{
				String []tokens = line.split(",");
				if (null != tokens && tokens.length != 3)
				{
					line = GenerateInsertLine (tokens[0], tokens[1], tokens[2]);
					stringBuffer.append(line);
					stringBuffer.append("\n");
				} 
			}
		
			System.out.println(stringBuffer.toString());
			logger.info(stringBuffer.toString());
    	
    	    if (stringBuffer.length() > 0  && SQLFile != null)
    	    {
     		  
    	    	fileWriter = new FileWriter(SQLFile);
 		       bw = new BufferedWriter(fileWriter);
		       bw.write(stringBuffer.toString());
    		
    	    }
 
		} 
		catch (IOException e1) 
		{
			logger.error(e1);
 			e1.printStackTrace();
		}
		finally 
		{
			try
			{
				fileReader.close();
				bw.close();
				fileWriter.close();
				
			}
			catch (Exception e)
			{
				logger.error(e);
	 			e.printStackTrace();
			}
		}
	
    }
    
    private void insertIntoDB ( )
    {
    	File file = new File(SQLFile);
		FileReader fileReader = null;
		BufferedReader bufferedReader = null; 
		PreparedStatement ps1 = null;
		
		 dbCon = DBConnector.getInstance();
		Connection con= dbCon.getConnection(); 
		
		try
		{
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String line;
		 
			while ((line = bufferedReader.readLine()) != null)
			{
			 
				System.out.println("Executing.."+line);
				logger.info("Executing.."+line);
				ps1 = con.prepareStatement(line);
			} 
		}
		catch (IOException e1) 
		{
			logger.error(e1);
 			e1.printStackTrace();
		}
		catch (SQLException e)
		{
			logger.error(e);
			e.printStackTrace();
		}
		finally 
		{
			try
			{
				fileReader.close();
				ps1.close();
				dbCon.close();
				
			}
			catch (Exception e)
			{
				logger.error(e);
				e.printStackTrace();
			}
		}
	
    }
    
	public static void main(String[] args)
	{
		
		TagImport tagImport = new TagImport ();
		
		tagImport.loadConfig(args[0]);
		
		
		tagImport.GenerateSQLFile (tagImport.TemplateFile);
		
		if (tagImport.GenerateOnlyFile == false)
		{
			tagImport.insertIntoDB();
		}
				
	}
}
