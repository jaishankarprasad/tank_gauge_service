package com.rainiersoft.tankgauge.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rainiersoft.tankgauge.dao.InventoryObjectDAO;
import com.rainiersoft.tankgauge.entity.TankHistoryData;
import com.rainiersoft.tankgauge.entity.TankMetaData;
import com.rainiersoft.tankgauge.pojo.InventoryObjectRequest;
import com.rainiersoft.tankgauge.pojo.InventoryReportPropertyItem;
import com.rainiersoft.tankgauge.pojo.ReportPropertiesLoader;
import com.rainiersoft.tankgauge.response.InventoryReportResponse;

@Repository
public class InventoryObjectDAOImpl implements InventoryObjectDAO
{
	SessionFactory sessionFactory;
	Transaction txn ;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public Transaction getTxn() {
		return txn;
	}
	public void setTxn(Transaction txn) {
		this.txn = txn;
	}


	@SuppressWarnings("unchecked")
	public List<InventoryReportResponse> getInventoryObject(InventoryObjectRequest inventoryObjectRequest) 
	{

		ReportPropertiesLoader reportUtils = new ReportPropertiesLoader();
		reportUtils.loadProperties("D:\\InventoryReport.properties");
		Session session= null;
		String productType = inventoryObjectRequest.getProductType();
		String lastUpdatedString = inventoryObjectRequest.getLastUpdated();

		//Timestamp lastUpdated = Timestamp.valueOf(lastUpdatedString);

		List<TankMetaData> tkMDList = getTankMDList(productType);

		List<InventoryReportResponse> inventoryObjectList = new ArrayList<InventoryReportResponse>();

		List<TankHistoryData> tankHistoryList = new ArrayList<TankHistoryData>();

		InventoryReportResponse inventoryObject = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date dat = null;
		try 
		{
			dat = sdf.parse(lastUpdatedString);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}

		String lastUpdated = sdf.format(dat);
		try
		{
			for(TankMetaData tk : tkMDList)
			{
				TankMetaData tkm = new TankMetaData();
				//	TankHistoryData tankHistoryData = null;

				tkm = tk;
				int tankId = tkm.getTankId();

				TankHistoryData tankHistoryData2 = new TankHistoryData();

				session = sessionFactory.openSession();

				//	select tank_id,Property_Name,Property_value,last_updated from tk_history_data where last_updated= "2018-12-28 19:56:51"  and tank_ID in (SELECT tank_ID FROM tankguage.tk_metadata where product_type = "petrol" and status = "true") 

				//CONVERT(DATE_FORMAT(last_Updated,'%Y-%m-%d-%H:%i:00'),DATETIME) = :lastUpdated

				List<Object[]> list = (List<Object[]>)session.createQuery
						("select DISTINCT propertyName,propertyValue from TankHistoryData where tankId =:tankId and DATE_FORMAT(lastUpdated, '%Y-%m-%d %H:%i')=:lastUpdated")
						.setParameter("tankId", tankId).setParameter("lastUpdated", lastUpdated).list();

				TankHistoryData tankHistoryData = null;


				tankHistoryList = new ArrayList<TankHistoryData>();

				if(list!=null)
				{
					for(Object [] ob:list)
					{
						String propertyName=(String) ob[0];
						String propertyValue=(String) ob[1];

						tankHistoryData = new TankHistoryData();
						tankHistoryData.setPropertyName(propertyName);
						tankHistoryData.setPropertyValue(propertyValue);
						tankHistoryList.add(tankHistoryData);
					}
				}

				inventoryObject = new InventoryReportResponse();
				List<InventoryReportPropertyItem> inventoryReportPropertyItems = new ArrayList<InventoryReportPropertyItem>();

				inventoryObject.setTankId(tankId);
				inventoryObject.setReportGeneratedOn(dat);
				inventoryObject.setTankName(tkm.getName());	
				HashMap<String, List<InventoryReportPropertyItem>> map = inventoryObject.getMap();

				tankHistoryData2 = new TankHistoryData();

				InventoryReportPropertyItem inventoryReportPropertyItem = null;

				for(TankHistoryData tankHistoryData1 : tankHistoryList )
				{
					tankHistoryData2 = tankHistoryData1;

					if(tankHistoryData2 != null)
					{

						String category = reportUtils.getValue(tankHistoryData2.getPropertyName());
						//String category = "DATA";
						inventoryReportPropertyItems = map.get(category);

						if(inventoryReportPropertyItems == null )
						{
							inventoryReportPropertyItems = new ArrayList<InventoryReportPropertyItem>();

						}

						inventoryReportPropertyItem = new InventoryReportPropertyItem();
						inventoryReportPropertyItem.setPropertyName(tankHistoryData2.getPropertyName());

						inventoryReportPropertyItem.setCategory(category);

						inventoryReportPropertyItem.setPropertyValue(tankHistoryData2.getPropertyValue());
						inventoryReportPropertyItems.add(inventoryReportPropertyItem);
						
						map.put(category, inventoryReportPropertyItems);

					}
				}


				inventoryObject.setMap(map);

				session.close();
				inventoryObjectList.add(inventoryObject);
			}

		}
		catch(HibernateException he)
		{
			he.printStackTrace();
		}

		return inventoryObjectList;
	}


	@SuppressWarnings("unchecked")
	public List<TankMetaData> getTankMDList(String productType)
	{
		Session session= null;
		List<TankMetaData> tankMDList = new ArrayList<TankMetaData>();

		@SuppressWarnings("unused")
		TankMetaData tkMD = null;

		try
		{
			session = sessionFactory.openSession();			
			tankMDList =  (List<TankMetaData>) session.createQuery
					("from  TankMetaData where productType = :productType")
					.setParameter("productType", productType).list();

			session.close();
			//tankMDList.add(tkMD);
		}

		catch(HibernateException he)
		{
			he.printStackTrace();
		}

		return tankMDList;
	}

}
