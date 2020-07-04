/*package com.rainiersoft.tankgauge.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.rainiersoft.tankgauge.core.Tag;
import com.rainiersoft.tankgauge.core.TagType;
import com.rainiersoft.tankgauge.exception.RSException;
import com.rainiersoft.tankgauge.util.RSContants;
import com.rainiersoft.tankgauge.util.RSUtil;

import javafish.clients.opc.component.OpcGroup;
import javafish.clients.opc.component.OpcItem;

import javafish.clients.opc.variant.Variant;

public class OpcClientTest
{
	OpcClientImpl opcClientimpl;

	String localhost="localhost";
	//	String progId="Intellution.IntellutionGatewayOPCServer";
	String progId="FactoryTalk Gateway";
	String clientHandle="JOPC2";
	public final String bayPrefix ="Bay";
	public HashMap<String, OpcItem> plcItemMap = new HashMap<String, OpcItem>();

	public static void main(String[] args)
	{
		OpcClientTest opcclientTest=new OpcClientTest();
		// opcclientTest.testReadItemIGS();
		//opcclientTest.testConnect();
		//opcclientTest.testConnect2();
		//opcclientTest.testReadItem();
			for(int i=0;i<10;i++)
		{
			 opcclientTest.testReadItemIGS();
		}
		Date date = new Date();
		long timeMilli = date.getTime();

		System.out.println("At Starting of Reading Tags::"+timeMilli);

		opcclientTest.populateItems();

		Date date1 = new Date();	   
		long afterinserting = date1.getTime();

		System.out.println("After Reading Tags::"+afterinserting);
		// opcclientTest.testWriteItem();
		//opcclientTest.testReadGroup();
	}
	public void testConnect()
	{

		//below line is Commented on 02/07/2018 by Madhu for closing error			
		//opcClientimpl=OpcClientImpl.getInstance();

		opcClientimpl=new OpcClientImpl();

		try 
		{
			opcClientimpl.setAdapter(RSContants.ADAPTER_TYPE_IGS);
			opcClientimpl.Connect(localhost, progId, clientHandle);
			System.out.println("Connected::IGS");

		} 
		catch (RSException e) 
		{

			e.printStackTrace();
		}

	}
	public void testConnect2() 
	{
		String comPort="COM3";
		//below line is Commented on 02/07/2018 by Madhu for closing error			
		//opcClientimpl=OpcClientImpl.getInstance();

		opcClientimpl=new OpcClientImpl();

		try {
			opcClientimpl.Connect(comPort);
			System.out.println("Connected::MODBUS");
		} catch (RSException e) {

			e.printStackTrace();
		}
	}
	public  void testReadItem ()
	{

		//below line is Commented on 02/07/2018 by Madhu for closing error			
		//opcClientimpl=OpcClientImpl.getInstance();

		opcClientimpl=new OpcClientImpl();

		OpcClientImpl.initialize();

		OpcItem item = new OpcItem("40003", true, "");
		item.setValue(new Variant (0));
		OpcGroup group = new OpcGroup("group1", true, 500, 0.0f);

		group.addItem(item);

		try
		{

			opcClientimpl.setAdapter(RSContants.ADAPTER_TYPE_MODBUS);
		} catch (RSException e1) {
			e1.printStackTrace();
		}

		try
		{
			String comPort = "COM3";
			opcClientimpl.Connect(comPort);				
			System.out.println("connected ModBus");				
		} 
		catch (RSException e1) 
		{
			e1.printStackTrace();
		}

		try 
		{
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			String beforeinserting=dtf.format(now);
			System.out.println("At Starting of Inserting Tags::"+beforeinserting);

			OpcItem responseItem = opcClientimpl.ReadItem(group,item);
			DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now1 = LocalDateTime.now();
			String afterinserting=dtf1.format(now1);
			System.out.println("After Inserting Tags::"+afterinserting);
			System.out.println(responseItem);
			System.out.println(Variant.getVariantName(responseItem.getDataType()) + ": " + responseItem.getValue());

		} 
		catch (RSException e)
		{
			e.printStackTrace();
		}
		OpcClientImpl.deinitialize();
	}

	public  void testReadItemIGS()
	{

		//below line is Commented on 02/07/2018 by Madhu for closing error			
		//opcClientimpl=OpcClientImpl.getInstance();

		opcClientimpl=new OpcClientImpl();

		OpcClientImpl.initialize();
		// OpcItem item = new OpcItem("Channel1.Device1.Global.Bay08_TruckWeight.Weight", true, "");
		OpcItem item = new OpcItem("[TrialTopic]Bay02_PinCodeInfo.Location.DATA", true, "");

		//  item.setValue(new Variant (0.0f));

		OpcGroup group = new OpcGroup("group1", true, 500, 0.0f);


		group.addItem(item);

		try 
		{
			opcClientimpl.setAdapter(RSContants.ADAPTER_TYPE_IGS);

		}
		catch (RSException e1)
		{
			e1.printStackTrace();
		}

		try
		{

			opcClientimpl.Connect(localhost, progId, clientHandle);
			System.out.println("Connected!!"+"\n"+"host::-"+localhost+"\n"+"progId::-"+progId+"\n"+"clientHandle::-"+clientHandle);

		}
		catch (RSException e1)
		{
			e1.printStackTrace();
		}

		try 
		{
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			    LocalDateTime now = LocalDateTime.now();
			    String beforeinserting=dtf.format(now);
			    System.out.println("At Starting of Inserting Tags::"+beforeinserting);


			OpcItem responseItem = opcClientimpl.ReadItem(group,item);

			DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime now1 = LocalDateTime.now();
				String afterinserting=dtf1.format(now1);
				System.out.println("After Inserting Tags::"+afterinserting);
			System.out.println(responseItem);
			System.out.println(Variant.getVariantName(responseItem.getDataType()) + ": " + responseItem.getValue());

		} 
		catch (RSException e)
		{
			e.printStackTrace();
		}
		OpcClientImpl.deinitialize();
	}



	public void testWriteItem() 
	{
		//below line is Commented on 02/07/2018 by Madhu for closing error			
		//opcClientimpl=OpcClientImpl.getInstance();

		opcClientimpl=new OpcClientImpl();

		OpcClientImpl.initialize();

		OpcItem item1 = new OpcItem("[TrialTopic]Bay01_PinCodeInfo.Capacity", true, "");
		//OpcItem item2 = new OpcItem("Channel1.Device2.Tag1", true, "");

		OpcGroup group = new OpcGroup("Group1", true, 500,0.0f);


		group.addItem(item1);


		try 
		{
			opcClientimpl.Connect(localhost, progId, clientHandle);
		} 
		catch (RSException e)
		{
			e.printStackTrace();
		}
		System.out.println("OPC is connected...");

		Variant varin1 = new Variant(4444.22f);

		item1.setValue(varin1);

		try
		{
			opcClientimpl.WriteItem(group,item1);
		}
		catch (RSException e)
		{
			e.printStackTrace();
		}

		//   System.out.println("WRITE ITEM IS: " + itemRead);
		//System.out.println("VALUE TYPE: " + Variant.getVariantName(itemRead.getDataType()));

		OpcClientImpl.deinitialize();
	}  


	public void testWriteGroup()
	{
		//below line is Commented on 02/07/2018 by Madhu for closing error			
		//opcClientimpl=OpcClientImpl.getInstance();

		opcClientimpl=new OpcClientImpl();

		OpcClientImpl.initialize();

		OpcItem item1 = new OpcItem("Channel1.Device1.Tag1", true, "");
		OpcItem item2 = new OpcItem("Channel1.Device1.Tag2", true, "");

		OpcGroup group = new OpcGroup("Group1", true, 500, 0.0f);

		group.addItem(item1);
		group.addItem(item2);
		try 
		{
			opcClientimpl.setAdapter(RSContants.ADAPTER_TYPE_IGS);
		} 
		catch (RSException e1) 
		{
			e1.printStackTrace();
		}

		try
		{
			opcClientimpl.Connect(localhost, progId, clientHandle);
		} 
		catch (RSException e)
		{

			e.printStackTrace();
		}
		System.out.println("OPC is connected...");

		Variant varin1 = new Variant(1443);
		Variant varin2 = new Variant(1443);
		item1.setValue(varin1);
		item2.setValue(varin2);
		try {
			opcClientimpl.WriteItem(group,item2);
		} catch (RSException e)
		{
			e.printStackTrace();
		}
		//   System.out.println("WRITE ITEM IS: " + itemRead);
		//System.out.println("VALUE TYPE: " + Variant.getVariantName(itemRead.getDataType()));

		OpcClientImpl.deinitialize();
	}  
	private String getCompteTagName (String tagName, int bayNumber)
	{
		String tagPrefix ="[TrialTopic]"+bayPrefix+ String.format("%02d", bayNumber)+ "_";
		// read pin code and bay number from plc.
		return (tagPrefix + tagName);
	}
	private Tag getReadTag (String tagName, int bayNumber)
	{
		TagType tagType = new TagType((String)IOCLUtil.ioclTagMap.get(tagName));
		String tagCompleteName = getCompteTagName(tagName, bayNumber);
		Tag tag = new Tag(tagCompleteName, "", tagType, tagCompleteName); 

		return tag;

	}
	private OpcItem getOpcItem (String tagName, int bayNumber)
	{
		OpcItem item = new OpcItem(getCompteTagName(tagName, bayNumber), true, "");
		Tag tag = getReadTag (tagName, bayNumber);

		int varType = RSUtil.getVariantType(tag.getTagType().getName());

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
		case Variant.VT_BSTR:
		{
			item.setValue(new Variant(""));
			break;			    	
		}
		case Variant.VT_BOOL:
		{
			item.setValue(new Variant(false));
			break;	
		}
		default :
			item.setValue(new Variant(0));

		}


		return item;		



	}
	private OpcItem[] generateOpcItems ()
	{
		ArrayList<OpcItem> items = new ArrayList <OpcItem>();

		for (int i=1; i<= 8; i++ )
		{
			items.add(getOpcItem (IOCLUtil.PINCODE_VALIDATION_REQ, i));
			items.add(getOpcItem (IOCLUtil.PINCODE_VALIDATION_REQ, i));
			items.add(getOpcItem (IOCLUtil.PINCODE_VALIDATION_BAYNUMBER, i));
			items.add(getOpcItem (IOCLUtil.PINCODE_VALIDATION_PINCODE, i));
			items.add(getOpcItem (IOCLUtil.PINCODE_PINCODESTATUS, i));
			items.add(getOpcItem (IOCLUtil.PINCODE_FANNO, i));
			items.add(getOpcItem (IOCLUtil.PINCODE_PINCODE, i));
			items.add(getOpcItem (IOCLUtil.PINCODE_PRESETVALUE, i));
			items.add(getOpcItem (IOCLUtil.PINCODE_TRUCKNO, i));
			items.add(getOpcItem (IOCLUtil.PINCODE_TRANSPORTERNAME, i));
			items.add(getOpcItem (IOCLUtil.PINCODE_LOCATION, i));
			items.add(getOpcItem (IOCLUtil.PINCODE_CAPACITY, i));
			items.add(getOpcItem (IOCLUtil.PINCODE_TAREWEIGHT, i));
			items.add(getOpcItem (IOCLUtil.PINCODE_TRUCKSTATUS, i));
			items.add(getOpcItem (IOCLUtil.PINCODE_TRUCKNO_LEN, i));
			items.add(getOpcItem (IOCLUtil.PINCODE_TRANSPORTERNAME_LEN, i));
			items.add(getOpcItem (IOCLUtil.PINCODE_LOCATION_LEN, i));
			items.add(getOpcItem (IOCLUtil.STATUS_CHANGE, i));
			items.add(getOpcItem (IOCLUtil.STATUS_BAYNUMBER, i));
			items.add(getOpcItem (IOCLUtil.STATUS_PINCODE, i));
			items.add(getOpcItem (IOCLUtil.STATUS_STATUS, i));
			items.add(getOpcItem (IOCLUtil.TRUCKWEIGHT_REQ, i));
			items.add(getOpcItem (IOCLUtil.TRUCKWEIGHT_WEIGHT, i));
		}


		return items.toArray(new OpcItem[items.size()]);
	}


	public void populateItems()
	{
		opcClientimpl=new OpcClientImpl();
		OpcClientImpl.initialize();

		OpcItem [] items = generateOpcItems ();

		OpcGroup group = new OpcGroup("Group1", true, 4000, 0.0f);

		for (int i=0; i< items.length; i++)
		{
			System.out.println("Item Name "+ items[i].getItemName());
			group.addItem(items[i]);

		}

		try 
		{
			//Thread.sleep(100);
			opcClientimpl.Connect(localhost, progId, clientHandle);  
			System.out.println("FactoryTalk Connection Success");
			System.out.println("Connected!!"+"\n"+"host::-"+localhost+"\n"+"progId::-"+progId+"\n"+"clientHandle::-"+clientHandle);

			OpcGroup currentGroup =  opcClientimpl.ReadGroup(group);
			if (null != currentGroup)
			{
				items = currentGroup.getItemsAsArray();
				for (int i=0; i< items.length; i++)
				{
					System.out.println("Item Name "+ items[i]);
					plcItemMap.put(items[i].getItemName(), items[i]);

				}
			}
		}
		catch (RSException e) 
		{
			e.printStackTrace();
		}
		  catch (InterruptedException e1) 
		  {
		    e1.printStackTrace();
		  }
	}
}

*/