package com.rainiersoft.tankgauge.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rainiersoft.tankgauge.dao.LoginDAO;
import com.rainiersoft.tankgauge.entity.Login;
import com.rainiersoft.tankgauge.response.*;

@Repository
public class LoginDAOImpl implements LoginDAO{

	private static final Logger LOG = LoggerFactory.getLogger(LoginDAOImpl.class);

	SessionFactory sessionFactory;

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

	Transaction txn;	

	public LoginResponse getLoginCredentials(Login login)
	{		
		LOG.info("In getLoginCredentials Method");		
		Login loginRecord = null;
		LoginResponse loginR = null;
		try
		{
			Session session = getSessionFactory().openSession();
			txn = session.beginTransaction();	

			loginRecord=(Login) session.byId(Login.class).load(login.getUsername());

			loginR = new LoginResponse();

			if(loginRecord != null)
			{
				loginR.setUsername(loginRecord.getUsername());
				loginR.setPassword(loginRecord.getPassword());
				loginR.setRoleid(loginRecord.getRoleid());
			}
			else
			{
				loginR = null;
			}	
			session.close();

		}
		catch(HibernateException he)
		{
			LOG.error("Error Occured in LoginAuthentication Process"+he);
			he.printStackTrace();
		}
		return loginR;
	}

}
