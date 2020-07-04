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

import com.rainiersoft.tankgauge.dao.TankAlarmDetailsDAO;
import com.rainiersoft.tankgauge.entity.TankAlarmDetails;
import com.rainiersoft.tankgauge.entity.TankMetaData;
import com.rainiersoft.tankgauge.pojo.TankAlarmVariables;


@Repository
public class TankAlarmDetailsDAOImpl implements TankAlarmDetailsDAO
{
	private static final Logger LOG = LoggerFactory.getLogger(TankAlarmDetailsDAOImpl.class);

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

	//for fetching all the TankAlarmDetails

	@SuppressWarnings("unchecked")
	public List<TankAlarmDetails> getTankDetails() 
	{			
		LOG.info("IN getTankDetails METHOD");
		Session session = null;
		List<TankAlarmDetails> list = null;
		try
		{
			session = sessionFactory.openSession();
			list = session.createQuery("from TankAlarmDetails").list();	
			session.close();
		}
		catch(HibernateException he)
		{
			LOG.error("Exception IN getTankDetails METHOD");
			he.printStackTrace();
		}		
		return list;
	}

	public List<TankAlarmVariables> getAllTankAlarmDetails() 
	{			
		List<TankAlarmDetails> list = getTankDetails();

		List<TankAlarmVariables> tankVariablesList = new ArrayList<TankAlarmVariables>(); 

		for(TankAlarmDetails tankAlarmDetails : list)
		{
			TankAlarmVariables tankAlarmVariables2 = new TankAlarmVariables();

			TankAlarmDetails tankAlarmDetails2 = new TankAlarmDetails();

			tankAlarmDetails2 = tankAlarmDetails;
			int tankId = tankAlarmDetails2.getTankId();
			String tankName = getTankNamebyId(tankId);
			if(tankName != null && tankAlarmDetails2 != null && tankAlarmVariables2 != null)
			{
				tankAlarmVariables2.setTankName(tankName);
				tankAlarmVariables2.setPropertyName(tankAlarmDetails2.getPropertyName());
				tankAlarmVariables2.setPropertyValue(tankAlarmDetails2.getPropertyValue());
				tankAlarmVariables2.setAlarmAcknowledge(tankAlarmDetails2.isAlarmAcknowledge());
				tankAlarmVariables2.setAlarmId(tankAlarmDetails2.getAlarmId());
				tankAlarmVariables2.setAlarmType(tankAlarmDetails2.getAlarmType());
				tankAlarmVariables2.setPropertyId(tankAlarmDetails2.getPropertyId());
				tankAlarmVariables2.setTankId(tankAlarmDetails2.getTankId());
				tankAlarmVariables2.setLastUpdated(tankAlarmDetails2.getLastUpdated());

				tankVariablesList.add(tankAlarmVariables2);
			}

		}	
		return tankVariablesList;
	}

	public String getTankNamebyId(int tankId) 
	{	
		LOG.info("IN getTankNamebyId METHOD");
		Session session = null;	
		String tankName = null;
		TankMetaData tankMetaData = new TankMetaData();
		try
		{
			session=getSessionFactory().openSession();								
			tankMetaData = (TankMetaData)session.get(TankMetaData.class, tankId); 			
			session.close();
		}
		catch(HibernateException he)
		{
			LOG.error("Exception IN getTankNamebyId METHOD");
			he.printStackTrace();
		}
		if(tankMetaData != null)
		{
			tankName = tankMetaData.getName();
		}
		return tankName;
	}


	//for fetching the individual TankAlarmDetails based on id
	public TankAlarmVariables getTankAlarmDetailsbyId(int alarmId) 
	{	
		LOG.info("IN getTankAlarmDetailsbyId METHOD");
		Session session = null;	
		TankAlarmVariables tankAlarmVariables = new TankAlarmVariables();
		TankAlarmDetails tankAlarmDetails = null;
		try
		{
			session=getSessionFactory().openSession();								
			tankAlarmDetails = (TankAlarmDetails)session.get(TankAlarmDetails.class, alarmId); 
			session.close();
		}
		catch(HibernateException he)
		{
			LOG.error("Exception IN getTankAlarmDetailsbyId METHOD");
			he.printStackTrace();
		}
		if(tankAlarmDetails != null)
		{
			int tankId = tankAlarmDetails.getTankId();
			String tankName = getTankNamebyId(tankId);
			tankAlarmVariables.setTankName(tankName);
			tankAlarmVariables.setPropertyName(tankAlarmDetails.getPropertyName());
			tankAlarmVariables.setPropertyValue(tankAlarmDetails.getPropertyValue());
			tankAlarmVariables.setAlarmAcknowledge(tankAlarmDetails.isAlarmAcknowledge());
			tankAlarmVariables.setAlarmId(tankAlarmDetails.getAlarmId());
			tankAlarmVariables.setAlarmType(tankAlarmDetails.getAlarmType());
			tankAlarmVariables.setPropertyId(tankAlarmDetails.getPropertyId());
			tankAlarmVariables.setTankId(tankAlarmDetails.getTankId());
			tankAlarmVariables.setLastUpdated(tankAlarmDetails.getLastUpdated());	
		}

		return tankAlarmVariables;
	}

}
