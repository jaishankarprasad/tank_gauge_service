package com.rainiersoft.tankgauge.dao.impl;

import java.util.List;

import javax.transaction.Transaction;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rainiersoft.tankgauge.dao.TankEventDAO;
import com.rainiersoft.tankgauge.entity.TankEvent;

@Repository
public class TankEventDAOImpl implements TankEventDAO
{
	private static final Logger LOG = LoggerFactory.getLogger(TankEventDAOImpl.class);

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

	public TankEvent getTankEventbyId(int tankId) 
	{
		LOG.info("In getTankEventbyId Implementation method");
		TankEvent tankEvent = null;
		try
		{
			Session session = sessionFactory.openSession();		
			tankEvent = (TankEvent) session.get(TankEvent.class, tankId);			
			session.close();	
		}
		catch(HibernateException he)
		{
			LOG.error("Error Occured In getTankEventbyId Implementation method"+he);
			he.printStackTrace();			
		}

		return tankEvent;
	}

	@SuppressWarnings("unchecked")
	public List<TankEvent> getAllTankEvents() 
	{
		LOG.info("In getAllTankEvents Implementation method");
		List<TankEvent> tankEventList = null;
		try
		{
			Session session = sessionFactory.openSession();	     
			tankEventList = session.createQuery("from TankEvent").list(); 
		}
		catch(HibernateException he)
		{
			LOG.error("Error Occured In getAllTankEvents Implementation method"+he);
			he.printStackTrace();		
		}

		return tankEventList;
	}

}
