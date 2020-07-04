package com.rainiersoft.tankgauge.dao.impl;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rainiersoft.tankgauge.dao.TankDataDAO;
import com.rainiersoft.tankgauge.entity.TankData;
import com.rainiersoft.tankgauge.entity.TankMetaData;
import com.rainiersoft.tankgauge.pojo.DashBoard;
import com.rainiersoft.tankgauge.pojo.PropertyDetails;


@Repository
public class TankDataDAOImpl implements TankDataDAO
{
	private static final Logger LOG = LoggerFactory.getLogger(TankDataDAOImpl.class);

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

	//for fetching all the TankDetails
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

	//for fetching the individual TankDetails based on id
	public TankData getTankDetailsbyId(int tankId) 
	{	
		TankData tankDetails = null;
		LOG.info("IN getTankDetailsbyId METHOD");
		Session session = null;	
		try
		{
			session=getSessionFactory().openSession();					
			tankDetails = (TankData)session.get(TankData.class, tankId); 			
			session.close();
		}
		catch(HibernateException he)
		{
			LOG.error("Exception occured in IN getTankDetailsbyId METHOD");
			he.printStackTrace();
		}

		return tankDetails;
	}

	@SuppressWarnings("unchecked")
	public Set<Integer> getTankIds()
	{
		List<Integer> idList = new ArrayList<Integer>();
		Set<Integer> items = null;
		//Set<Integer> idList = new HashSet<>();

		//dashBoard.

		LOG.info("IN getPropertyValuesForDashBoard METHOD");
		Session session = null;	
		try
		{
			session=getSessionFactory().openSession();					
			idList = (List<Integer>) session.createQuery("select tankId from TankData").list();	

			items = new HashSet<Integer>(idList); 
			session.close();
		}
		catch(HibernateException he)
		{
			LOG.error("Exception occured in IN getPropertyValuesForDashBoard METHOD");
			he.printStackTrace();
		}
		return items;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public List<DashBoard> getPropertyValuesForDashBoard()
	{
		List<DashBoard> dashBoardDetails = new ArrayList<DashBoard>();			
		Set<Integer> ids = getTankIds();
		Session session = null;	
		List<Integer> idsList = new ArrayList<Integer>();
		idsList.addAll(ids);

		List<PropertyDetails> listProps = new ArrayList<PropertyDetails>();

		for(int id1 : idsList)
		{	
			DashBoard dashBoard = new DashBoard();	
			int tankId = id1;
			String tankName = getTankNamebyId(tankId);
			try
			{
				session=getSessionFactory().openSession();					

				listProps = (List<PropertyDetails>)session.createQuery("select propertyName, propertyValue from TankData where tankId=:tankId").setParameter("tankId", tankId).list();
				
				dashBoard.setTankId(tankId);
				dashBoard.setTankName(tankName);
				dashBoard.setPropsList(listProps);

				dashBoardDetails.add(dashBoard);

				session.close();
			}
			catch (HibernateException e)
			{
				e.printStackTrace();
			}

		}
		return dashBoardDetails;
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

}

