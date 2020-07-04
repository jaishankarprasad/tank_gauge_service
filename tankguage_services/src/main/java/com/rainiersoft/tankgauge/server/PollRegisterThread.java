package com.rainiersoft.tankgauge.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.rainiersoft.tankgauge.api.OpcClientImpl;
import com.rainiersoft.tankgauge.core.DBConnector;
import com.rainiersoft.tankgauge.core.DeviceConfig;
import com.rainiersoft.tankgauge.core.Tag;
import com.rainiersoft.tankgauge.core.TagType;
import com.rainiersoft.tankgauge.exception.RSException;
import com.rainiersoft.tankgauge.pojo.TankRegister;
import com.rainiersoft.tankgauge.util.PropertyLoader;
import com.rainiersoft.tankgauge.util.RSContants;
import com.rainiersoft.tankgauge.util.RSUtil;

import javafish.clients.opc.component.OpcGroup;
import javafish.clients.opc.component.OpcItem;
import javafish.clients.opc.variant.Variant;

public class PollRegisterThread extends Thread 
{
	final static Logger logger = Logger.getLogger(PollRegisterThread.class);
	private int pollInterval = 1000*30;	 
	DBConnector dbCon = null;
	Connection con = null;
	PropertyLoader pl= null;
	OpcClientImpl opcClientimpl = null;
//	OpcClientImpl opcClientimpl=new OpcClientImpl();
	String propFile="tankgauge.properties";
	long processingTimeInMilliSeconds = 0;


	public PollRegisterThread ()
	{
		super.setName("PollRegisterThread");	
		dbCon = DBConnector.getInstance();
		con = dbCon.getConnection();
		System.out.println("DB Connection Success");
		pl=new PropertyLoader();
		pl.loadProperties(propFile);
	}

	private String getTime ()
	{
		Timestamp ts = new Timestamp (new java.util.Date().getTime());
		return ts.toString();
	}

	@Override
	public void run()
	{
		logger.info("Initating Poll Register THREAD ....- START "+Thread.currentThread().getName()+":"+getTime());

		pollInterval=Integer.parseInt(pl.getValue("POLL_INTERVAL"));
		System.out.println(pollInterval);

		while(true) 
		{
			try
			{

				long startMillSeonds = Calendar.getInstance().getTimeInMillis();

				doProcessing();

				long endMilliSeconds = Calendar.getInstance().getTimeInMillis();

				processingTimeInMilliSeconds = endMilliSeconds - startMillSeonds;

				if ( (pollInterval - processingTimeInMilliSeconds) > 0 )
					Thread.sleep(pollInterval - processingTimeInMilliSeconds);

			}
			catch (InterruptedException e)
			{
				logger.error(e);
			}

		}
	}


	//	public List<TankRegister> getConfiguredRegisters(String deviceName)
	public List<TankRegister> getConfiguredRegisters()
	{

		if (!dbCon.isConnectionAlive())
		{
			con = dbCon.reConnect();
			logger.info("DB Connection is not Alive!....Reconnecting");
		}

		List<TankRegister> listTags = new ArrayList();
		try
		{
			PreparedStatement ps = con.prepareStatement("select Tank_ID, Register_StartAddress, Register_Count, Property_Type, Property_ID from TK_POLLREGISTERS ");

			ResultSet rs=ps.executeQuery();

			TankRegister register = null ;

			while(rs.next())
			{
				register = new TankRegister ();
				register.setTankId(rs.getInt("Tank_ID"));
				register.setStartAddress(rs.getInt("Register_StartAddress"));
				register.setNoOfRegisters(rs.getInt("Register_Count"));
				register.setDataType((String)rs.getString("Property_Type"));
				register.setPropertyId(rs.getInt("Property_ID"));

				listTags.add(register);
			}

		}

		catch (SQLException e)
		{
			e.printStackTrace();
			logger.error(e);
		}

		return listTags;		
	}

	private List<Tag> getTagsFromRegisters (List<TankRegister> registerLlist)
	{
		TankRegister register = null;

		List<Tag> listTags = new ArrayList();

		for(int row = 0; row < registerLlist.size(); row++)
		{

			register = (TankRegister)registerLlist.get(row);

			Tag tag = new Tag (""+register.getStartAddress(), ""+register.getValue(), new TagType(register.getDataType()), ""+register.getStartAddress(), register.getNoOfRegisters());
			tag.setExtra1(""+register.getTankId());
			listTags.add(tag);

		}

		return listTags;
	}


	private TankRegister getTankRegister (List<TankRegister> registerLlist, int startAddress, int tankId)
	{
		TankRegister register = null;

		for(int row = 0; row < registerLlist.size(); row++)
		{

			register = (TankRegister)registerLlist.get(row);

			if (register.getTankId() == tankId && register.getStartAddress() == startAddress)
			{
				return register;
			}


		}

		return register;
	}
	
	
	/**
	 * Populate raw registers
	 */
	public void populateRawRegisters()
	{	

		List<TankRegister> registersList = getConfiguredRegisters();

		List<Tag> listTags = getTagsFromRegisters (registersList);
		
		String devName = null;

		if (!dbCon.isConnectionAlive())
		{
			con = dbCon.reConnect();
			logger.info("DB Connection is not Alive!....Reconnecting");
		}
		
		if (null ==  opcClientimpl)
		{
			if (null != registersList && registersList.size() > 0)
			{
				String tankId = ""+registersList.get(0).getTankId();
				devName = getDeviceNameForTankID(tankId); 
				opcClientimpl = getOpcConnectHandle (devName);
			}
			 
		}

		try
		{
			PreparedStatement ps=con.prepareStatement("insert into TK_RAWDATA (Tank_ID, Register_ID, Register_Count, Register_StartAddress, Register_Value, Last_Update, Register_Type, Property_ID) values (?, ?, ?, ?, ?, ?, ?,?)");


			java.util.Calendar calendar = java.util.Calendar.getInstance();
			java.sql.Timestamp startDate = new java.sql.Timestamp(calendar.getTimeInMillis());

			int rowsinserted = 0;

			for(int row = 0; row < listTags.size(); row++)
			{
				Tag tag = listTags.get(row);

				String deviceName = getDeviceNameForTankID(tag.getExtra1());
				
				if (!deviceName.equalsIgnoreCase(devName))
				{
					 opcClientimpl = getOpcConnectHandle (deviceName);
					 devName = deviceName;
					
				}

				OpcItem item = readFromDevice(opcClientimpl, devName, tag.getName(),tag.getId(),tag.getTagType().getName());

				if (null == item)
					continue;

				Variant tagvalue=item.getValue();


				TankRegister tk_register = getTankRegister (registersList, Integer.parseInt(tag.getId()), Integer.parseInt(tag.getExtra1()));

				ps.setInt(1, Integer.parseInt(tag.getExtra1()));
				ps.setString(2, tag.getName());
				ps.setInt(3, tag.getNoOfRegisters());
				ps.setString(4, tag.getId());
				ps.setString(5, tagvalue.toString());
				ps.setTimestamp(6, startDate);
				ps.setString(7,  tag.getTagType().getName());
				ps.setInt(8,  tk_register.getPropertyId());

				rowsinserted += ps.executeUpdate();
				tag = null;
				item = null;
				tk_register = null;
				tagvalue = null;
				deviceName = null;

			}
			listTags = null;
			registersList = null;
			System.out.println(" Raw registeres inserted"+rowsinserted);		




		}
		catch (SQLException e)
		{
			e.printStackTrace();
			logger.error(e);
		}

	}

	public String getDeviceNameForTankID(String tankId)
	{
		String devName = null;


		if (!dbCon.isConnectionAlive())
		{
			con = dbCon.reConnect();
			logger.info("DB Connection is not Alive!....Reconnecting");
		}

		try
		{
			PreparedStatement ps = con.prepareStatement("SELECT devicename FROM Tankguage.devicedetails where deviceid in (select deviceid from TK_METADATA where tank_ID='"+tankId+"')");

			ResultSet rs=ps.executeQuery();



			while(rs.next())
			{
				devName = rs.getString("deviceName");
			}

		}

		catch (SQLException e)
		{
			e.printStackTrace();
			logger.error(e);
		}

		return devName;
	}

	private  void  doProcessing() throws InterruptedException
	{
		logger.debug("Retreiving the Tag Informatoin from PLC ..- START "+Thread.currentThread().getName()+":"+getTime());

		populateRawRegisters ();


	}

	public OpcClientImpl getOpcConnectHandle (String deviceName)
	{

	  if (null == opcClientimpl)
		{

			opcClientimpl= OpcClientImpl.getInstance();

			DeviceConfig dc = DeviceConfig.getDeviceConfig(deviceName); 
			opcClientimpl.setDeviceConfig(dc);

			try
			{
				if (dc.getProtocol().equals("Modbus"))
				{
					opcClientimpl.setAdapter(RSContants.ADAPTER_TYPE_MODBUS);

					opcClientimpl.Connect(dc.getComport());



				}
				else if (dc.getProtocol().equals("Ethernet"))
				{
					opcClientimpl.setAdapter(RSContants.ADAPTER_TYPE_ETHERNET);

					opcClientimpl.Connect(dc.getTcpIP(),dc.getPort(),dc.getDeviceid());

					dc.getPort();
				}
			}
			catch (RSException rs)
			{
				rs.getStackTrace();
			}
		}

		return opcClientimpl;

	}

	public OpcItem readFromDevice(String deviceName, String tagName,String tagId,String tagType)
	{

		OpcItem item=null;
		OpcGroup group=null;
		OpcItem responseItem = null;
		opcClientimpl=getOpcConnectHandle (deviceName);


		item = new OpcItem(tagId, true, "");        
		group = new OpcGroup("group1", true, 500, 0.0f);
		group.addItem(item);


		int varType = RSUtil.getVariantType(tagType);

		switch (varType)
		{

		case Variant.VT_I2 :
		{
			item.setValue(new Variant(0));
			break;
		}
		case Variant.VT_R4 :
		{
			item.setValue(new Variant(0.0f));
			break;
		}
		case Variant.VT_R8 :
		{
			item.setValue(new Variant(0.0d));
			break;			    	
		}
		default :
			item.setValue(new Variant(0));

		}

		try
		{
			responseItem = opcClientimpl.ReadItem(group,item);
		} 
		catch (RSException e)
		{
			e.printStackTrace();
		}

		System.out.println("Response from PLC: " + responseItem);
		// System.out.println(Variant.getVariantName(responseItem.getDataType())+"  "+responseItem.getValue());


		return responseItem;
	}
	
	public OpcItem readFromDevice(OpcClientImpl opcClientimpl, String deviceName, String tagName,String tagId,String tagType)
	{

		OpcItem item=null;
		OpcGroup group=null;
		OpcItem responseItem = null;

		item = new OpcItem(tagId, true, "");        
		group = new OpcGroup("group1", true, 500, 0.0f);
		group.addItem(item);


		int varType = RSUtil.getVariantType(tagType);

		switch (varType)
		{

		case Variant.VT_I2 :
		{
			item.setValue(new Variant(0));
			break;
		}
		case Variant.VT_R4 :
		{
			item.setValue(new Variant(0.0f));
			break;
		}
		case Variant.VT_R8 :
		{
			item.setValue(new Variant(0.0d));
			break;			    	
		}
		default :
			item.setValue(new Variant(0));

		}

		try
		{
			responseItem = opcClientimpl.ReadItem(group,item);
		} 
		catch (RSException e)
		{
			e.printStackTrace();
		}

		System.out.println("Response from PLC: " + responseItem);
		// System.out.println(Variant.getVariantName(responseItem.getDataType())+"  "+responseItem.getValue());


		return responseItem;
	}



	public static void main (String args[])
	{
		PollRegisterThread pthread = new PollRegisterThread ();
		pthread.start();

	}

}
