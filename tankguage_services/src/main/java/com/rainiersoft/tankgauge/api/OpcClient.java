package com.rainiersoft.tankgauge.api;

import com.rainiersoft.tankgauge.exception.*;

import de.re.easymodbus.modbusclient.ModbusClient;
import javafish.clients.opc.component.OpcItem;
import javafish.clients.opc.component.OpcGroup;

public interface OpcClient
{

	public void setAdapter(int adapterType) throws RSException;
	
	public void Connect (String programID, String remoteMachine,String serverClientHandle) throws RSException;                                                                                                                                                                          
	
	public void Connect (String comPort) throws RSException;
	
	public void Connect (String ipAddress, int Port) throws RSException;
	
	public void unconnect () throws RSException;
	
	public OpcItem ReadItem (OpcGroup group,OpcItem item) throws RSException;
	
	public OpcGroup ReadGroup(OpcGroup group) throws RSException;

	public void WriteItem(OpcGroup group,OpcItem item) throws RSException;
	 
	public void WriteGroup(OpcGroup group, OpcItem item) throws RSException;
	
	public boolean isConnected() throws RSException;
	 ///
	
//	public void addGroup(OpcGroup group)throws rsException;
//	
//	public void registerGroups()throws rsException;
	
	/*
	public boolean isConnected()
	
	public boolean Available(int timeout)
	
	public String getipAddress()
	
	public void setipAddress(String ipAddress)
	
	public int getPort()
	
	public void setPort(int port)
	
	public boolean getUDPFlag()
	
	public void setUDPFlag(boolean udpFlag)
	
	
	public int getConnectionTimeout()
	
	public void setConnectionTimeout(int connectionTimeout)
	
        
    public void setSerialFlag(boolean serialflag)
   
    public boolean getSerialFlag()
  
    
    public void setUnitIdentifier(byte unitIdentifier)
    
    
    public byte getUnitIdentifier()
   
    
    
    public void setLogFileName(String logFileName)
   
    public void setSerialPort(String serialPort)
   
    public String getSerialPort()
  
    public void setBaudrate(int baudrate)
    
    public int getBaudrate  ()
    
    public void setParity(Parity parity)
   
    public Parity getParity()
    
    public void setStopBits(StopBits stopBits)
  
    public StopBits getStopBits()
    
    public void addReveiveDataChangedListener(ReceiveDataChangedListener toAdd) 
   
    public void addSendDataChangedListener(SendDataChangedListener toAdd) 
    */
   
}
