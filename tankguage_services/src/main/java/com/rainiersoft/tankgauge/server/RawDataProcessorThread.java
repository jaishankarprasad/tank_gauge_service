package com.rainiersoft.tankgauge.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.rainiersoft.tankgauge.api.OpcClientImpl;
import com.rainiersoft.tankgauge.core.DBConnector;
import com.rainiersoft.tankgauge.core.DeviceConfig;
import com.rainiersoft.tankgauge.core.Tag;
import com.rainiersoft.tankgauge.core.TagType;
import com.rainiersoft.tankgauge.exception.RSException;
import com.rainiersoft.tankgauge.entity.TankData;
import com.rainiersoft.tankgauge.entity.TankProperty;
import com.rainiersoft.tankgauge.pojo.TankRegister;
import com.rainiersoft.tankgauge.util.PropertyLoader;
import com.rainiersoft.tankgauge.util.RSContants;
import com.rainiersoft.tankgauge.util.RSUtil;

import javafish.clients.opc.component.OpcGroup;
import javafish.clients.opc.component.OpcItem;
import javafish.clients.opc.variant.Variant;

public class RawDataProcessorThread extends Thread 
{
	final static Logger logger = Logger.getLogger(RawDataProcessorThread.class);
	private int pollInterval = 60*1000;	 
	DBConnector dbCon = null;
	Connection con = null;

	//OpcClientImpl opcClientimpl = null;
	
	String propFile="tankgauge.properties";
	long processingTimeInMilliSeconds = 0;

	PropertyLoader pl= null;

	public RawDataProcessorThread ()
	{
		super.setName("RawDataProcessorThread");	
		dbCon = DBConnector.getInstance();
		con = dbCon.getConnection();
		
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
		logger.info("Initating Raw data processor THREAD ....- START "+Thread.currentThread().getName()+":"+getTime());
		
		pollInterval=Integer.parseInt(pl.getValue("PROCESS_INTERVAL"));
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


	public List<TankRegister> getConfiguredRegisters(String deviceName)
	{
		List<TankRegister> listTags = new ArrayList();

		if (!dbCon.isConnectionAlive())
		{
			con = dbCon.reConnect();
			logger.info("DB Connection is not Alive!....Reconnecting");
		}
		try
		{

			PreparedStatement ps=con.prepareStatement("select Tank_ID, Register_StartAddress, Register_Count, Property_Type from TK_POLLREGISTERS ");

			ResultSet rs=ps.executeQuery();

			TankRegister register = null ;

			while(rs.next())
			{
				register = new TankRegister ();
				register.setTankId(rs.getInt("Tank_ID"));
				register.setStartAddress(rs.getInt("Register_StartAddress"));
				register.setNoOfRegisters(rs.getInt("Register_Count"));
				register.setDataType((String)rs.getString("Property_Type"));

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


	private TankData populateTankDataEntry(int tank_id, int property_id, String rawRegisterValue )
	{
		TankData tk_dataItem = new TankData ();

		TankProperty tk_property = new TankProperty();


		if (!dbCon.isConnectionAlive())
		{
			con = dbCon.reConnect();
			logger.info("DB Connection is not Alive!....Reconnecting");
		}


		try
		{

			PreparedStatement ps=con.prepareStatement("select * from TK_PROPERTY where Property_ID ="+property_id+""); 	

			ResultSet rs=ps.executeQuery();

			while(rs.next())
			{
				tk_property.setTankId(rs.getInt("Tank_ID"));
				tk_property.setPropertyName(rs.getString("Property_Name"));
				tk_property.setPropertyDisplayName(rs.getString("Property_DisplayName"));
				tk_property.setPropertyType(rs.getString("Property_Type"));
				tk_property.setPropertyId(rs.getInt("Property_ID"));
				tk_property.setRegisterStartAddress(rs.getInt("Register_StartAddress"));
				tk_property.setRegisterCount(rs.getInt("Register_Count"));
				tk_property.setSuffix(rs.getString("Suffix"));
				tk_property.setPrefix(rs.getString("Prefix"));
				tk_property.setAlarmEnabled(rs.getBoolean("Alarm_Enabled"));
				tk_property.setNormalStart(rs.getString("Normal_Start"));
				tk_property.setNormalStart(rs.getString("Normal_End"));
				tk_property.setLowThreshold(rs.getString("Low_Threshold"));
				tk_property.setHighThreshold(rs.getString("High_Threshold"));
				tk_property.setCriticalThreshold(rs.getString("Critical_Threshold"));
				tk_property.setScalingType(rs.getString("Scaling_Type"));
				tk_property.setScalingValue(rs.getInt("Scaling_Value"));
			}

			
		}
		catch(SQLException sqe)
		{
			sqe.printStackTrace();
		}


		tk_dataItem.setTankId(tk_property.getTankId());
		tk_dataItem.setPropertyName(tk_property.getPropertyName());
		// Changed for setting propertyid into tankdata
		tk_dataItem.setPropertyId(tk_property.getPropertyId());

		double before_scale_val = Double.parseDouble(rawRegisterValue);
		double after_scale_val = 0;

		tk_dataItem.setPropertyValue(""+before_scale_val);

		if (tk_property.getScalingType() != null && tk_property.getScalingType().length() > 0)
		{
			if (tk_property.getScalingType().equals("Adding"))
			{
				after_scale_val = before_scale_val + tk_property.getScalingValue();
			}

			else if (tk_property.getScalingType().equals("Multiply"))
			{
				after_scale_val = before_scale_val * tk_property.getScalingValue();
			}

			tk_dataItem.setPropertyValue(""+after_scale_val);
		}

		tk_dataItem.setRegisterValue(rawRegisterValue);

		if (tk_property.isAlarmEnabled())
		{
			if (after_scale_val <= Double.parseDouble(tk_property.getLowThreshold()))
				tk_dataItem.setLowThresholdQualified(true);

			if (after_scale_val >= Double.parseDouble(tk_property.getCriticalThreshold()))
				tk_dataItem.setCriticalThresholdQualified(true);

			if (after_scale_val >= Double.parseDouble(tk_property.getHighThreshold()) && after_scale_val < Double.parseDouble(tk_property.getCriticalThreshold()))
				tk_dataItem.setHighThresholdQualified(true);		
		}

		tk_dataItem.setLastUpdated(new Date());

		tk_property = null;
		return tk_dataItem;

	}

	/**
	 * Create the frame.
	 */
	public List<TankData>  fetchTankDataFromRawRegisterData()
	{	


		if (!dbCon.isConnectionAlive())
		{
			con = dbCon.reConnect();
			logger.info("DB Connection is not Alive!....Reconnecting");
		}

		List<TankData> tankDataItems = new ArrayList();
		
		try
		{
			PreparedStatement ps=con.prepareStatement("select Raw_DATA_ID,Tank_ID, Register_ID, Register_StartAddress, Register_Count, Register_Value, Last_Update, Register_Type, Property_ID from TK_RAWDATA where Processed = '"+0+"' "); 	

			ResultSet rs=ps.executeQuery();

			TankData tk_dataItem =  null ;
			int counter = 0;
			while(rs.next())
			{

				int rawDataId = rs.getInt("Raw_DATA_ID");
				int tk_id = rs.getInt("Tank_ID");
				int prop_id = rs.getInt("Property_ID");
				String value = rs.getString("Register_Value");
				
				java.sql.Timestamp ts = rs.getTimestamp("Last_Update");

				tk_dataItem =populateTankDataEntry (tk_id, prop_id, value);

				tk_dataItem.setLastUpdated(ts);
				
				tk_dataItem.setRawDataId(rawDataId);
				
				tk_dataItem.setPropertyId(prop_id);

				tankDataItems.add(tk_dataItem);


				System.out.println("ROW NUM::"+counter++);
			}	

		}

		catch (SQLException e)
		{
			e.printStackTrace();
			logger.error(e);
		}

		return tankDataItems;

	}

	public void populateTankDataTables()
	{	

		List<TankData> tk_dataItemsList = fetchTankDataFromRawRegisterData();

		if (!dbCon.isConnectionAlive())
		{
			con = dbCon.reConnect();
			logger.info("DB Connection is not Alive!....Reconnecting");
		}


		try
		{
			PreparedStatement ps = con.prepareStatement("insert into TK_DATA (Tank_ID, Property_Name, Property_Value, Alarm_Qualified, Register_Value, Low_ThresholdQualified, High_ThresholdQualified, Critical_ThresholdQualified, Last_Updated, Property_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			PreparedStatement ps2 = con.prepareStatement("insert into TK_HISTORY_DATA (Tank_ID, Property_Name, Property_Value, Alarm_Qualified, Register_Value, Low_ThresholdQualified, High_ThresholdQualified, Critical_ThresholdQualified, Last_Updated, Property_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			int rowsinserted = 0;
			int rowsinserted2 = 0;


			for(int row = 0; row < tk_dataItemsList.size(); row++)
			{
				TankData tk_DataItem = tk_dataItemsList.get(row);

				java.util.Calendar calendar = java.util.Calendar.getInstance();
				java.sql.Timestamp startDate = new java.sql.Timestamp(calendar.getTimeInMillis());

				ps.setInt(1, tk_DataItem.getTankId());
				ps.setString(2, tk_DataItem.getPropertyName());

				ps.setString(3, tk_DataItem.getPropertyValue());

				ps.setBoolean(4, tk_DataItem.isAlarmQualified());
				ps.setString(5, tk_DataItem.getRegisterValue());
				ps.setBoolean(6, tk_DataItem.isLowThresholdQualified());
				ps.setBoolean(7, tk_DataItem.isHighThresholdQualified());
				ps.setBoolean(8, tk_DataItem.isCriticalThresholdQualified());
				ps.setTimestamp(9,  startDate);
				ps.setInt(10, tk_DataItem.getPropertyId());

				ps2.setInt(1, tk_DataItem.getTankId());
				ps2.setString(2, tk_DataItem.getPropertyName());

				ps2.setString(3, tk_DataItem.getPropertyValue());

				ps2.setBoolean(4, tk_DataItem.isAlarmQualified());
				ps2.setString(5, tk_DataItem.getRegisterValue());
				ps2.setBoolean(6, tk_DataItem.isLowThresholdQualified());
				ps2.setBoolean(7, tk_DataItem.isHighThresholdQualified());
				ps2.setBoolean(8, tk_DataItem.isCriticalThresholdQualified());
				ps2.setTimestamp(9,  startDate);
				ps2.setInt(10, tk_DataItem.getPropertyId());
				//System.out.println(tk_DataItem.toString());
				
				rowsinserted += ps.executeUpdate();
				rowsinserted2 += ps2.executeUpdate();

			}

			
			System.out.println(" Data Rrows inserted"+rowsinserted);			     
			System.out.println(" Data History Rows inserted"+rowsinserted2);			     


		}
		catch (SQLException e)
		{
			e.printStackTrace();
			logger.error(e);
		}
		markProcessedUpdated(tk_dataItemsList);
		populateAlarmTable(tk_dataItemsList);
		tk_dataItemsList = null;
		System.out.println("Processing of Raw Registers Completed");
	}

	public void populateAlarmTable( List<TankData> tk_dataItemsList )
	{	

		//List<TankData> tk_dataItemsList = fetchTankDataFromRawRegisterData();

		if (!dbCon.isConnectionAlive())
		{
			con = dbCon.reConnect();
			logger.info("DB Connection is not Alive!....Reconnecting");
		}


		try
		{

			PreparedStatement ps=con.prepareStatement("insert into TK_ALARM (Alarm_Type, Alarm_Acknowledge, Tank_ID, Property_Name, Property_Value, Last_Updated) values (?, ?, ?, ?, ?, ?)");

			for(int row = 0; row < tk_dataItemsList.size(); row++)
			{
				TankData tk_DataItem = tk_dataItemsList.get(row);

				if (tk_DataItem.isAlarmQualified() == false)
					continue;

				java.util.Calendar calendar = java.util.Calendar.getInstance();
				java.sql.Timestamp startDate = new java.sql.Timestamp(calendar.getTimeInMillis());

				if (tk_DataItem.isLowThresholdQualified())
					ps.setString(1, "Low");

				else if (tk_DataItem.isHighThresholdQualified())
					ps.setString(1, "High");	

				else if (tk_DataItem.isCriticalThresholdQualified())
					ps.setString(1, "Critical");	

				ps.setBoolean(2,  false);
				ps.setInt(3,  tk_DataItem.getTankId());


				ps.setString(4, tk_DataItem.getPropertyName());

				ps.setString(5, tk_DataItem.getPropertyValue());

				ps.setTimestamp(6,  startDate);

				int rowsinserted = ps.executeUpdate();

				System.out.println(" rows inserted"+rowsinserted);			     
			}
			

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			logger.error(e);
		}

	}


	public boolean cleanTankData( )
	{	
		if (!dbCon.isConnectionAlive())
		{
			con = dbCon.reConnect();
			logger.info("DB Connection is not Alive!....Reconnecting");
		}

		try
		{
			PreparedStatement ps=con.prepareStatement("delete from TK_DATA ");
			PreparedStatement ps2=con.prepareStatement("delete from TK_RAWDATA where processed = 1 ");
			
			int tkDataRowsDeleted = ps.executeUpdate();
			
			int tkRawDataRowsDeleted = ps2.executeUpdate();
			
			logger.info("TK_DATA_ROWS DELETED = "+tkDataRowsDeleted);
			
			logger.info("TK_RAWDATA_ROWS DELETED = "+tkRawDataRowsDeleted);
			

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			logger.error(e);
			return false;
		}

		return true;
	}


	public boolean markProcessedUpdated(List tk_dataItemsList)
	{
		boolean updated = false;


		if (!dbCon.isConnectionAlive())
		{
			con = dbCon.reConnect();
		}

		try
		{
			for(int row = 0; row < tk_dataItemsList.size(); row++)
			{
				TankData tk_DataItem = (TankData) tk_dataItemsList.get(row);

				PreparedStatement ps = con.prepareStatement("update TK_RAWDATA set Processed = '"+1+"' where Raw_DATA_ID='"+tk_DataItem.getRawDataId()+"' ");

				int rowsinserted = ps.executeUpdate();					
			}
			updated = true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			logger.error(e);
			updated = false;
		}
		return updated ;
	}

	private  void  doProcessing() throws InterruptedException
	{
		logger.debug("Retreiving the Tag Informatoin from PLC ..- START "+Thread.currentThread().getName()+":"+getTime());

		//		fetchTankDataFromRawRegisterData ();

		if (cleanTankData() == true)
		{
			populateTankDataTables();
		}

		//populateAlarmTable();
	}

/*	public OpcClientImpl getOpcConnectHandle (String deviceName)
	{

		if (null == opcClientimpl)
		{


			opcClientimpl=OpcClientImpl.getInstance();

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

					opcClientimpl.Connect(dc.getTcpIP(),dc.getPort());
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

		System.out.println(responseItem);
		System.out.println(Variant.getVariantName(responseItem.getDataType())+"  "+responseItem.getValue());


		return responseItem;
	}
*/
	public static void main (String args[])
	{
		RawDataProcessorThread pthread = new RawDataProcessorThread ();
		pthread.start();
	}

}
