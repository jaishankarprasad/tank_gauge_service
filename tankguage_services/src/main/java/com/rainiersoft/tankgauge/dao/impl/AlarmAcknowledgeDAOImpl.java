package com.rainiersoft.tankgauge.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rainiersoft.tankgauge.dao.AlarmAcknowledgeDAO;
import com.rainiersoft.tankgauge.entity.TankAlarmDetails;
import com.rainiersoft.tankgauge.entity.TankEvent;

@Repository
public class AlarmAcknowledgeDAOImpl implements AlarmAcknowledgeDAO
{
	private static final Logger LOG = LoggerFactory.getLogger(AlarmAcknowledgeDAOImpl.class);

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


	public boolean alarmAcknowledgeEnable(int alarmId) 
	{
		LOG.info("In alarmAcknowledgeEnable Implementation Class");
		Session session = null;
		TankAlarmDetails tankAlarmDetails = null;
		boolean isEnabled = false;
		try
		{
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			tankAlarmDetails = (TankAlarmDetails) session.get(TankAlarmDetails.class,alarmId);
			tankAlarmDetails.setAlarmAcknowledge(true);
			session.update(tankAlarmDetails);
			txn.commit();
			session.close();
			isEnabled = true;
		}
		catch(HibernateException he)
		{
			LOG.error("Error Occured In alarmAcknowledgeEnable Implementation Class");
			he.printStackTrace();
		}
		return isEnabled;
	}
	public boolean updateTankEvent(int userId,int tankId,String userName) 
	{
		LOG.info("In updateTankEvent Implementation Class");

		Session session = null;
		TankEvent tankEvent = null;
		boolean isEventUpdated = false;
		try
		{
			tankEvent = new TankEvent();
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			tankEvent.setUserId(userId);
			tankEvent.setUser(userName);
			tankEvent.setEventDescription("AlarmAcknowledge Enable Process");
			tankEvent.setTankId(tankId);
			session.save(tankEvent);
			txn.commit();
			session.close();
			isEventUpdated = true;
		}
		catch(HibernateException he)
		{
			LOG.error("Error Occured In updateTankEvent Implementation Class");
			he.printStackTrace();
		}
		return isEventUpdated;
	}

}
