package com.rainiersoft.tankgauge.dao.impl;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rainiersoft.tankgauge.dao.TotalTankDetailsDAO;
import com.rainiersoft.tankgauge.entity.TankAlarmDetails;
import com.rainiersoft.tankgauge.entity.TankData;
import com.rainiersoft.tankgauge.entity.TankMetaData;
import com.rainiersoft.tankgauge.pojo.DataAndAlarm;
import com.rainiersoft.tankgauge.pojo.TotalTankDetails;


@Repository
public class TotalTankDetailsDAOImpl implements TotalTankDetailsDAO
{
	private static final Logger LOG = LoggerFactory.getLogger(TotalTankDetailsDAOImpl.class);

	SessionFactory sessionFactory = null;

	Transaction txn = null;

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

	public List<TotalTankDetails> getTotalTankDetailsList() 
	{	
		LOG.info("IN getTotalTankDetailsList METHOD");

		List<TotalTankDetails> totalTankDetailsList = new ArrayList<TotalTankDetails>();
		TotalTankDetails totalTankDetails = new TotalTankDetails();
		List<TankMetaData> tankMetaDataList = null; 
		tankMetaDataList = getAllTankMetaData();

		for(TankMetaData tankMetaData : tankMetaDataList)
		{
			TankMetaData tankMetaData2 = new TankMetaData();

			tankMetaData2 = tankMetaData;

			int tankId = tankMetaData2.getTankId();

			String status = tankMetaData2.getStatus();

			if(status.equalsIgnoreCase("true"))
			{
				totalTankDetails = getTotalTankDetailsbyId(tankId);

				totalTankDetailsList.add(totalTankDetails);
			}
		}
		return totalTankDetailsList;
	}


	@SuppressWarnings("unchecked")
	public List<TankMetaData> getAllTankMetaData() 
	{

		LOG.info("IN getAllTankMetaData METHOD");
		Session session = null ;
		List<TankMetaData> list = null;
		try
		{
			session = sessionFactory.openSession();
			list = session.createQuery("from TankMetaData").list();
			session.close();
			for(TankMetaData tankMetaData : list)
			{
				TankMetaData tankMetaData1 = new TankMetaData();
				tankMetaData1 = tankMetaData;
				tankMetaData1.setTankPropertyList(null);
			}
		}
		catch(HibernateException he)
		{
			LOG.error("Exception Occured IN getAllTankMetaData METHOD"+he);
			he.printStackTrace();
		}

		return list;
	}


	public TotalTankDetails getTotalTankDetailsbyId(int tankId) 
	{	
		LOG.info("IN getTotalTankDetailsbyId METHOD");

		TotalTankDetails totalTankDetails = new TotalTankDetails();

		TankMetaData tankMetaData = new TankMetaData();	 
		List<DataAndAlarm> dataAndAlarmList = null;

		tankMetaData = getTankMetaDatabyId(tankId);

		dataAndAlarmList = getDataAndAlarmList(tankId);

		if(dataAndAlarmList != null && tankMetaData != null)
		{
			totalTankDetails.setTankId(tankId);
			totalTankDetails.setName(tankMetaData.getName());
			totalTankDetails.setSite(tankMetaData.getSite());
			totalTankDetails.setLatitude(tankMetaData.getLatitude());
			totalTankDetails.setLongitude(tankMetaData.getLongitude());
			totalTankDetails.setLocation(tankMetaData.getLocation());
			totalTankDetails.setImageURL(tankMetaData.getImageURL());
			totalTankDetails.setDataAndAlarm(dataAndAlarmList);
			totalTankDetails.setDevicedId(tankMetaData.getDevicedId());
			totalTankDetails.setProductType(tankMetaData.getProductType());
			totalTankDetails.setStatus(tankMetaData.getStatus());
			totalTankDetails.setPollInterval(tankMetaData.getPollInterval());
		}    

		return totalTankDetails;
	}

	private List<DataAndAlarm> getDataAndAlarmList(int tankId) 
	{
		LOG.info("In getDataAndAlarmList Implementation Class");

		DataAndAlarm dataAndAlarm = null;

		List<DataAndAlarm> dataAndAlarmList = new ArrayList<DataAndAlarm>();

		List<TankData> tankRealDataList = getTankDetailsListbyId(tankId);

		for(TankData tankRealData : tankRealDataList)
		{	
			TankData tankRealData1 = new TankData();

			tankRealData1 = tankRealData;

			TankAlarmDetails tankAlarmDetails = null;

			String propertyName = tankRealData1.getPropertyName();

			//boolean isAlarmQualified = tankRealData1.isAlarmQualified();

			//if(isAlarmQualified == true)
			{
				tankAlarmDetails = getTankAlarmbyIdAndPropertyName(tankId,propertyName);

				if(tankAlarmDetails != null && tankRealData1 != null)
				{
					dataAndAlarm = new DataAndAlarm();
					dataAndAlarm.setAlarmId(tankAlarmDetails.getAlarmId());
					dataAndAlarm.setAlarmType(tankAlarmDetails.getAlarmType());
					dataAndAlarm.setPropertyId(tankAlarmDetails.getPropertyId());
					dataAndAlarm.setPropertyValue(tankRealData1.getPropertyValue());
					dataAndAlarm.setPropertyName(tankRealData1.getPropertyName());
					dataAndAlarm.setTankId(tankRealData1.getTankId());
					dataAndAlarm.setAlarmAcknowledge(tankAlarmDetails.isAlarmAcknowledge());
					dataAndAlarm.setDataId(tankRealData1.getDataId());
					dataAndAlarm.setAlarmQualified(tankRealData1.isAlarmQualified());
					dataAndAlarm.setRegisterValue(tankRealData1.getRegisterValue());
					dataAndAlarm.setLowThresholdQualified(tankRealData1.isLowThresholdQualified());
					dataAndAlarm.setHighThresholdQualified(tankRealData1.isHighThresholdQualified());
					dataAndAlarm.setCriticalThresholdQualified(tankRealData1.isCriticalThresholdQualified());
					dataAndAlarm.setLastUpdated(tankAlarmDetails.getLastUpdated());
				}

			}
			dataAndAlarmList.add(dataAndAlarm);
		}

		return dataAndAlarmList;		
	}

	public TankAlarmDetails getTankAlarmbyIdAndPropertyName(int tankId, String propertyName) 
	{
		Session session = null;
		TankAlarmDetails tankAlarmDetails = new TankAlarmDetails();
		try
		{
			session = sessionFactory.openSession();

			tankAlarmDetails = (TankAlarmDetails) session.createQuery
					("from TankAlarmDetails where tankId=:tankId and propertyName=:propertyName").setParameter("tankId", tankId).setParameter("propertyName", propertyName).uniqueResult();
			session.close();
		}
		catch(HibernateException he)
		{
			LOG.error("Error occured in getTankAlarmbyIdAndPropertyName"+he);
			he.printStackTrace();
		}
		return tankAlarmDetails;	
	}

	@SuppressWarnings("unchecked")
	public List<TankAlarmDetails> getTankAlarmListbyId(int tankId) 
	{
		LOG.info("In getTankAlarmListbyId Implementation Class");
		Session session = null;	
		List<TankAlarmDetails> tankAlarmList = null;		
		try
		{
			session =sessionFactory.openSession();
			tankAlarmList = session.createQuery("from TankAlarmDetails where tankId =:tankId").setParameter("tankId",tankId).list();
			session.close();
			//return tankAlarmList;
		}
		catch(HibernateException he)
		{
			LOG.error("Error Occured In getTankAlarmListbyId Implementation Class"+he);
			he.printStackTrace();
		}

		return tankAlarmList;
	}


	@SuppressWarnings("unchecked")
	public List<TankData> getTankDetailsListbyId(int tankId) 
	{	
		List<TankData> tankDetailsList = null;
		LOG.info("IN getTankDetailsListbyId METHOD");
		Session session = null;	
		try
		{
			session=getSessionFactory().openSession();					
			tankDetailsList = (List<TankData>) session.createQuery("from TankData where tankId=:tankId").setParameter("tankId", tankId).list(); 			
			session.close();
		}
		catch(HibernateException he)
		{
			LOG.error("Exception occured in IN getTankDetailsListbyId METHOD"+he);
			he.printStackTrace();
		}

		return tankDetailsList;
	}

	public TankMetaData getTankMetaDatabyId(int tankId) 

	{	
		LOG.info("IN getTankMetaDatabyId METHOD");
		Session session = null;	
		TankMetaData tankMetaData = null;

		try
		{
			session=getSessionFactory().openSession();						
			tankMetaData = (TankMetaData)session.get(TankMetaData.class, tankId); 		
			session.close();
		}
		catch(HibernateException he)
		{
			LOG.error("Exception occured IN getTankMetaDatabyId METHOD"+he);
			he.printStackTrace();
		}
		return tankMetaData;
	}	

	@SuppressWarnings("unchecked")
	public List<TotalTankDetails> getAllTotalTanks() 
	{
		LOG.info("IN getAllTanks METHOD");
		List<TotalTankDetails> list = null;
		try		
		{
			Session session = sessionFactory.openSession();			
			list = session.createQuery("from TankData").list();			
			session.close();
		}
		catch(HibernateException he)
		{
			LOG.error("Exception occured in IN getAllTanks METHOD");
			he.printStackTrace();
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<TankData> getAllTanks() 
	{
		LOG.info("IN getAllTanks METHOD");
		List<TankData> list = null;
		try		
		{
			Session session = sessionFactory.openSession();			
			list = session.createQuery("from TankData").list();			
			session.close();
		}
		catch(HibernateException he)
		{
			LOG.error("Exception occured in IN getAllTanks METHOD");
			he.printStackTrace();
		}
		return list;
	}
}

