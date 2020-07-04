package com.rainiersoft.tankgauge.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rainiersoft.tankgauge.dao.TankHistoryPropertyDAO;
import com.rainiersoft.tankgauge.entity.TankHistoryData;

@Repository
public class TankHistoryPropertyDAOImpl implements TankHistoryPropertyDAO
{
	private static final Logger LOG = LoggerFactory.getLogger(TankHistoryPropertyDAOImpl.class);

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

	@SuppressWarnings("unchecked")
	public List<TankHistoryData> getAllTankHistoryProperty() 
	{
		LOG.info("In getAllTankHistoryProperty Implementation Method");
		Session session = null;
		List<TankHistoryData> tankHistoryPropertyList = null;
		try
		{
			session = sessionFactory.openSession();
			tankHistoryPropertyList = session.createQuery("from TankHistoryProperty").list();	
		}
		catch(HibernateException he)
		{
			LOG.error("Error occured In getAllTankHistoryProperty Implementation Method"+he);
			he.printStackTrace();
		}
		return tankHistoryPropertyList;
	}

	public TankHistoryData getTankHistoryPropertybyId(int propertyId) 
	{
		LOG.info("In getTankHistoryPropertybyId Implementation Method");
		Session session = null;
		TankHistoryData tankHistoryProperty = null;
		try
		{
			session = sessionFactory.openSession();		
			tankHistoryProperty = (TankHistoryData) session.get(TankHistoryData.class, propertyId);			
		}
		catch(HibernateException he)
		{
			LOG.error("Error occured In getTankHistoryPropertybyId Implementation Method"+he);
			he.printStackTrace();
		}
		return tankHistoryProperty;
	}

}
