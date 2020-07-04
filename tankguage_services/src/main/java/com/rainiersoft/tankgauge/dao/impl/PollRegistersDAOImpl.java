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

import com.rainiersoft.tankgauge.dao.PollRegistersDAO;
import com.rainiersoft.tankgauge.entity.PollRegisters;

@Repository
public class PollRegistersDAOImpl implements PollRegistersDAO
{
	private static final Logger LOG = LoggerFactory.getLogger(PollRegistersDAOImpl.class);

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
	public List<PollRegisters> getAllPollRegisters() 
	{
		LOG.info("In getAllPollRegisters Implementation Method");
		Session session = null;
		List<PollRegisters> list = null;
		try
		{
			session = sessionFactory.openSession();
			list = session.createQuery("from PollRegisters").list();
			session.close();		
		}
		catch(HibernateException he)
		{
			LOG.error("Error Occured In getAllPollRegisters Implementation Method"+he);
			he.printStackTrace();
		}
		return list;
	}

	public PollRegisters getPollRegistersbyId(int propertyId) 
	{
		LOG.info("In getPollRegistersbyId Implementation Method");
		Session session = null;
		PollRegisters pollRegisters = null;
		try
		{
			session = sessionFactory.openSession();
			pollRegisters = (PollRegisters) session.get(PollRegisters.class, propertyId);
			session.close();		
		}
		catch(HibernateException he)
		{
			LOG.error("Error Occured In getPollRegistersbyId Implementation Method"+he);
			he.printStackTrace();
		}
		return pollRegisters;
	}


}
