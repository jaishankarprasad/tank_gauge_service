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

import com.rainiersoft.tankgauge.dao.TankDataTypeDAO;
import com.rainiersoft.tankgauge.entity.TankDataType;

@Repository
public class TankDataTypeDAOImpl implements TankDataTypeDAO
{
	private static final Logger LOG = LoggerFactory.getLogger(TankDataTypeDAOImpl.class);
	
	SessionFactory sessionFactory;
	Transaction txn;
	
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
	public List<TankDataType> getAllTankDataTypeDetails() 
	{
		LOG.info("In getAllTankDataTypeDetails Implementation Method");
		
		Session session = null;
		List<TankDataType> list = null;
		try
		{
			session = sessionFactory.openSession();
			list = session.createQuery("from TankDataType").list();		
			session.close();
		}

		catch(HibernateException he)
		{
			LOG.error("Exception In getAllTankDataTypeDetails Implementation Method");
			he.printStackTrace();
		}
		
		return list;
	}
	
	public TankDataType getTankDataTypeDetailsbyId(int propertyId) 
	{
		LOG.info("In getTankDataTypeDetailsbyId Implementation Method");
		
		Session session = null;
		
		TankDataType tankDataType = null;

		try
		{
			 session = sessionFactory.openSession();		
			 tankDataType = (TankDataType) session.get(TankDataType.class, propertyId);
			 session.close();
		}
		catch(HibernateException he)
		{
			LOG.error("Exception In getTankDataTypeDetailsbyId Implementation Method");
			he.printStackTrace();
		}
		
		return tankDataType;
	}
	
}
