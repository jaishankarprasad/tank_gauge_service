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

import com.rainiersoft.tankgauge.dao.DeviceDetailsDAO;
import com.rainiersoft.tankgauge.entity.DeviceDetails;

@Repository
public class DeviceDetailsDAOImpl implements DeviceDetailsDAO
{
	private static final Logger LOG = LoggerFactory.getLogger(DeviceDetailsDAOImpl.class);

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


	public boolean insertDeviceDetails(DeviceDetails deviceInfo) 
	{
		LOG.info("IN DeviceDetailsDAOImpl Class insertDeviceDetails METHOD");
		System.out.println("IN DeviceDetailsDAOImpl Class insertDeviceDetails METHOD");
		Session session = null;	
	    session=getSessionFactory().openSession();
		txn= session.beginTransaction();	

		try
		{
			session.saveOrUpdate(deviceInfo);	
			txn.commit();
			LOG.info("Insertion Succeeded");
			System.out.println("Insertion Succeeded");
			session.close();
			return true;
		}		
		catch(HibernateException he) 
		{
			LOG.error("Exception During Insertion::"+he);
			System.out.println("Exception During Insertion::"+he);
			he.printStackTrace();
			session.close();
			return false;
		}		
	}

	public DeviceDetails getDeviceDetailsbyId(int deviceid) 
	{
		LOG.info("IN GETDEVICEINFO METHOD");
		Session session = null;	
		DeviceDetails deviceDetails = null;
		try
		{
			session=getSessionFactory().openSession();						
		    deviceDetails = (DeviceDetails)session.get(DeviceDetails.class, deviceid); 		
			session.close();
		}
		catch(HibernateException he)
		{
			LOG.error("Error Occured IN GETDEVICEINFO METHOD"+he);
			he.printStackTrace();
		}
		
		return deviceDetails;
	}

	public boolean updateDeviceDetails(DeviceDetails deviceInfo)
	{
		Session session = null;	
		
		LOG.info("Inside UPDATE method");
		try
		{
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			session.update(deviceInfo);
			txn.commit();
			LOG.info("Update of Device Succeeded");
			session.close();
			return true;
		}
		catch(HibernateException e)
		{
			LOG.error("Exception occured while updating device");
			session.close();
			e.printStackTrace();
			return false;
		}		
	}

	public boolean deleteDeviceDetailsbyId(int deviceid)
	{
		LOG.info("IN deleteDeviceDetails METHOD");
		Session session = null;	
		try
		{
			session=sessionFactory.openSession();
			txn= session.beginTransaction();			
			DeviceDetails deviceInfo = (DeviceDetails) session.byId(DeviceDetails.class).load(deviceid);
			session.delete(deviceInfo);
			txn.commit();
			LOG.info("Deletion of Device Succeeded");
			session.close();
			return true;
		}
		catch(HibernateException e)
		{
			LOG.error("Exception During Deletion of Device::"+e);
			session.close();
			e.printStackTrace();
			return false;
		}		
	}

	@SuppressWarnings("unchecked")
	public List<DeviceDetails> getAllDevices()
	{
		
		LOG.info("In getAllDevices Method");
		List<DeviceDetails> list = null;
		Session session = null;
		try
		{
		    session = sessionFactory.openSession();
			list = session.createQuery("from DeviceDetails").list();			
			session.close();
			
		}
		catch(HibernateException he)
		{
			LOG.error("Exception During Deletion of Device::"+he);
			System.out.println("Exception During Deletion of Device::"+he);
			he.printStackTrace();
			session.close();
		}
		return list;		
	}

}
