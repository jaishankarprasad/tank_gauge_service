package com.rainiersoft.tankgauge.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DeviceConfig 
{
	private String deviceName;
	private String deviceid;
	private String protocol;
	private String rstype;
	private String comport;
	private String ipaddress;
	private String baudrate;
	private String parity;
	private String stopbits;
	private String databits;
	private int refreshInterval;
	private String tcpIP;
	private int port;
	public void setPort(int port) {
		this.port = port;
	}


	private boolean isHighByteFirst = false;
	
	final static Logger logger = Logger.getLogger(DeviceConfig.class);
	
	
	public String getTcpIP ()
	{
		return tcpIP;
	}
	
	public boolean getIsHighByteFirst()
	{
		return isHighByteFirst;
	}
	
	public void setIsHighByteFirst (boolean isHighByteFirst)
	{
		this.isHighByteFirst = isHighByteFirst;
	}
	
	public void setTcpIP(String tcpIP)
	{
		this.tcpIP = tcpIP;
	}
	
	public int getPort ()
	{
		return port;
	}
	
	
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
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
	
	
	  public static DeviceConfig getDeviceConfig (String deviceName)
	  {
		  
		   DBConnector dbCon = DBConnector.getInstance();
		   
		   Connection con = dbCon.getConnection(); 

			if (!dbCon.isConnectionAlive())
			{
				con = dbCon.reConnect();
				logger.info("DB Connection is not Alive!....Reconnecting");
			}
		   
		   DeviceConfig dc = null;
		    
		    try
		    {
		    	
			PreparedStatement pstm=con.prepareStatement("select deviceid, protocol, rstype, comport, ipaddress,port, baudrate, parity, stopbits, databits, timeinterval from devicedetails where devicename='"+deviceName+"'");
			
		    ResultSet rs = pstm.executeQuery();
	        
	        while(rs.next())
	        {
	        	dc = new DeviceConfig();
	           	dc.setDeviceName(deviceName);
	        	dc.setDeviceid(rs.getString("deviceid"));
	        	dc.setProtocol(rs.getString("protocol"));
	        	dc.setRstype(rs.getString("rstype"));
	        	dc.setComport(rs.getString("comport"));
	        	dc.setBaudrate(rs.getString("baudrate"));
	            dc.setTcpIP(rs.getString("ipaddress"));
	        	dc.setParity(rs.getString("parity"));
	        	dc.setStopbits(rs.getString("stopbits"));
	        	dc.setDatabits(rs.getString("databits"));
	        	dc.setPort(rs.getInt("port"));
	        	dc.setRefreshInterval(rs.getInt("timeinterval"));
	        	
	       }
		    }
		    catch (SQLException sqe)
		    {
		    	sqe.printStackTrace();
		    	logger.error(sqe);
		    	
		    }
		    catch (Exception e)
		    {
		    	e.printStackTrace();
		    	logger.error(e);
		    }
		    
		    return dc;
			
	  }

	 
}
