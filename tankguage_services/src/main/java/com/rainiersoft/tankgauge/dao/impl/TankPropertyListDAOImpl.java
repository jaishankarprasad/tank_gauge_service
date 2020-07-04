package com.rainiersoft.tankgauge.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rainiersoft.tankgauge.dao.TankPropertyListDAO;
import com.rainiersoft.tankgauge.entity.TankProperty;

@Repository
public class TankPropertyListDAOImpl implements TankPropertyListDAO
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
	public List<TankProperty> getPropertyListbyTankId(int tankId)
	{
		Session session= null;
		List<TankProperty> propertyList = null;
		try
		{
		 session = sessionFactory.openSession();
		 propertyList = (List<TankProperty>) session.createQuery
				 ("from TankProperty where tankId=:tankId").setParameter("tankId", tankId).list();
		 session.close();
		}
		catch(HibernateException he)
		{
			he.printStackTrace();
		}
		return propertyList;
	}
}
