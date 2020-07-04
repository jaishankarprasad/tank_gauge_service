package com.rainiersoft.tankgauge.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rainiersoft.tankgauge.dao.ReportManagementDAO;
import com.rainiersoft.tankgauge.entity.TankData;
import com.rainiersoft.tankgauge.pojo.ReportInputs;

@Repository
public class ReportManagementDAIOImpl implements ReportManagementDAO
{
	private static final Logger LOG = LoggerFactory.getLogger(ReportManagementDAIOImpl.class);

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
	public List<TankData> getReports(ReportInputs reportInputs) 
	{
		LOG.info("In getReports Implementation Method");
		Session session = null;
		List<TankData> tankDetailsList = null;
		String fromDate = reportInputs.getStartDate();
		String toDate = reportInputs.getEndDate();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate;
		Date endDate ;	
		try 
		{
			startDate = formatter.parse(fromDate);
			endDate = formatter.parse(toDate);
			session = sessionFactory.openSession();
			tankDetailsList = (List<TankData>) session.createQuery
					("from TankData where lastUpdated BETWEEN :start AND :end").setParameter("start", startDate).setParameter("end", endDate).list();
			session.close();
		}
		catch(HibernateException he)
		{
			he.printStackTrace();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return tankDetailsList;
	}

}
