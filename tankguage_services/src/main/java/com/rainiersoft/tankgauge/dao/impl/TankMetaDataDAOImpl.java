package com.rainiersoft.tankgauge.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rainiersoft.tankgauge.dao.TankMetaDataDAO;
import com.rainiersoft.tankgauge.entity.PollRegisters;
import com.rainiersoft.tankgauge.entity.TankAlarmDetails;
import com.rainiersoft.tankgauge.entity.TankMetaData;
import com.rainiersoft.tankgauge.entity.TankProperty;

@Repository
public class TankMetaDataDAOImpl implements TankMetaDataDAO
{
	private static final Logger LOG = LoggerFactory.getLogger(TankMetaDataDAOImpl.class);

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

	public boolean insertTankMetaDataAndProperty(TankMetaData tankMetaData)
	{
		boolean tankMetaDataInsertion = false;
		boolean tankPropertyInsertion = false;
		@SuppressWarnings("unused")
		boolean tankAlarmInsertion = false;
		boolean totalTankInfoDeleted = false;

		TankAlarmDetails tankAlarmDetails = null;
		List<TankProperty> tankPropertyList = tankMetaData.getTankPropertyList();

		boolean tankExists = tankExistCheck(tankMetaData);	

		if(tankExists == true)
		{
			//	int tankId = getTankIdByTankName(tankMetaData.getName());

			//	totalTankInfoDeleted = deleteAllTankInfoByTankId(tankId);		
		}	

		if(totalTankInfoDeleted == true)
		{
			tankMetaDataInsertion =insertTankMetaData(tankMetaData);	

			int tankId =getTankId();

			if(tankMetaDataInsertion == true)
			{			
				tankPropertyInsertion = insertTankProperty(tankPropertyList,tankId);

				int maxResults = tankPropertyList.size();

				List<TankProperty> tankPropertyInsertedList = getLatestInsertedTankPropertyList(maxResults);

				for(TankProperty tankProperty : tankPropertyInsertedList)
				{
					TankProperty tankProperty1 = new TankProperty();
					tankProperty1 = tankProperty;				
					boolean  isAlarmEnabled = tankProperty1.isAlarmEnabled();

					if(isAlarmEnabled == true)
					{
						tankAlarmDetails = new TankAlarmDetails();
						tankAlarmDetails.setPropertyName(tankProperty1.getPropertyName());
						tankAlarmDetails.setTankId(tankId);
						tankAlarmDetails.setPropertyId(tankProperty1.getPropertyId());
						tankAlarmDetails.setAlarmAcknowledge(true);
						tankAlarmDetails.setAlarmType("HighThresh");

						tankAlarmInsertion = insertTankAlarm(tankAlarmDetails);
					}
				}

			}
			else
			{
				return tankMetaDataInsertion;
			}

		}


		return tankPropertyInsertion;
	}

	public boolean deleteAllTankInfoByTankId(int tankId) 
	{
		boolean tankDeleted = false;

		LOG.info("Inside deleteAllTankInfoByTankId method");

		boolean metaDataDeleted = deleteTankMetaDataByTankId(tankId);
		boolean propertyDeleted = deleteTankPropertyByTankId(tankId);
		boolean pollRegistersDeleted = deletePollRegistersByTankId(tankId);

		if(metaDataDeleted == true && propertyDeleted == true && pollRegistersDeleted == true)
		{
			tankDeleted = true;
		}

		return tankDeleted;
	}


	public int getTankIdByTankName(String name) 
	{
		LOG.info("Inside getTankIdByTankName method Implementation class");
		Session session = null;
		int tankId;
		TankMetaData tankMetaData = new TankMetaData();
		try
		{
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			Query query = session.createQuery("from TankMetaData where name=:name").setParameter("name", name);	
			tankMetaData = (TankMetaData) query.uniqueResult();
			tankId = tankMetaData.getTankId();
			txn.commit();
			session.close();
			return tankId;
		}
		catch(HibernateException he)
		{
			LOG.error("Error Occured inside getTankIdByTankName method Implementation class"+he);
			he.printStackTrace();
			return 0;
		}


	}

	@SuppressWarnings("unchecked")
	private List<TankProperty> getLatestInsertedTankPropertyList(int maxResults) 
	{
		LOG.info("Inside getLatestInsertedTankPropertyList method Implementation class");
		Session session = null;
		List<TankProperty> tankPropertyList = null;
		try
		{
			session = sessionFactory.openSession();
			tankPropertyList =
					(List<TankProperty>) session.createQuery("from TankProperty order by propertyId DESC").setMaxResults(maxResults).list();
		}
		catch(HibernateException he)
		{
			LOG.error("Error Occured Inside getLatestInsertedTankPropertyList method Implementation class"+he);
			he.printStackTrace();

		}
		return tankPropertyList;
	}

	//Configuration of Tanks Into MetaData
	public boolean insertTankMetaData(TankMetaData tankMetaData) 
	{
		LOG.info("IN TankMetaDataDAOImpl Class insertTankMetaData Method");
		Session session = null;		
		tankMetaData.setTankPropertyList(null);

		try
		{
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			session.save(tankMetaData);	    
			txn.commit();
			LOG.info("Insertion of TankMetaData Successs");
			session.close();		   
			return true;			
		}		
		catch(HibernateException he)
		{
			LOG.error("Exception Occured While inserting tags in insertTankMetaData Method");
			he.printStackTrace();
			return false;
		}

	}


	public boolean tankExistCheck(TankMetaData tankMetaData)
	{
		List<TankMetaData> list = getAllTankMetaData();

		for(TankMetaData tankMetaData1 : list)
		{
			TankMetaData tankMetaData2 = new TankMetaData();
			tankMetaData2 = tankMetaData1;

			if(tankMetaData2.getName().equalsIgnoreCase(tankMetaData.getName()))
			{
				return true;
			}
		}
		return false;
	}

	public boolean insertTankProperty(List<TankProperty> tankPropertyList,int tankId)
	{
		LOG.info("Entered into insertTankProperty Implementation Class");
		Session session = null;
		try
		{
			session = sessionFactory.openSession();
			txn = session.beginTransaction();

			for(TankProperty tankProperty :tankPropertyList )
			{
				TankProperty tankProperty1 = new TankProperty();
				tankProperty1 = tankProperty;					
				tankProperty1.setTankId(tankId);

				session.save(tankProperty1);

				int propertyId = tankProperty1.getPropertyId();
				System.out.println("propertyId::"+propertyId);

				PollRegisters pollRegisters = new PollRegisters();
				pollRegisters.setPropertyId(propertyId);
				pollRegisters.setPropertyType(tankProperty1.getPropertyType());
				pollRegisters.setRegisterCount(tankProperty1.getRegisterCount());
				pollRegisters.setRegisterStartAddress(tankProperty1.getRegisterStartAddress());
				pollRegisters.setTankId(tankProperty1.getTankId());

				session.save(pollRegisters);

				LOG.info("Insertion of TankProperty Successs");								
			}
			txn.commit();
			session.close();
			return true;
		}
		catch(HibernateException he)
		{
			LOG.error("Exception Occured While inserting tags in insertTankProperty Method");
			he.printStackTrace();
			return false;	
		}
	}

	public boolean updateTankProperty(List<TankProperty> tankPropertyList,int tankId)
	{
		LOG.info("Entered into updateTankProperty Implementation Class");
		Session session = null;
		try
		{
			session = sessionFactory.openSession();
			txn = session.beginTransaction();

			for(TankProperty tankProperty :tankPropertyList )
			{
				TankProperty tankProperty1 = new TankProperty();
				tankProperty1 = tankProperty;

				if(tankProperty1.getPropertyId() == 0)
				{
					tankProperty1.setTankId(tankId);

					session.saveOrUpdate(tankProperty1);

					int propertyId = tankProperty1.getPropertyId();
					System.out.println("propertyId::"+propertyId);

					PollRegisters pollRegisters = new PollRegisters();
					pollRegisters.setPropertyId(propertyId);
					pollRegisters.setPropertyType(tankProperty1.getPropertyType());
					pollRegisters.setRegisterCount(tankProperty1.getRegisterCount());
					pollRegisters.setRegisterStartAddress(tankProperty1.getRegisterStartAddress());
					pollRegisters.setTankId(tankProperty1.getTankId());

					session.saveOrUpdate(pollRegisters);

					LOG.info("Insertion of New TankProperty Successs");								
				}
				else
				{
					tankProperty1.setTankId(tankId);

					session.saveOrUpdate(tankProperty1);

					int propertyId = tankProperty1.getPropertyId();
					System.out.println("propertyId::"+propertyId);

					PollRegisters pollRegisters = new PollRegisters();
					pollRegisters.setPropertyId(propertyId);
					pollRegisters.setPropertyType(tankProperty1.getPropertyType());
					pollRegisters.setRegisterCount(tankProperty1.getRegisterCount());
					pollRegisters.setRegisterStartAddress(tankProperty1.getRegisterStartAddress());
					pollRegisters.setTankId(tankProperty1.getTankId());

					session.saveOrUpdate(pollRegisters);

					LOG.info("Updation of TankProperty Successs");								
				}

			}
			txn.commit();
			session.close();
			return true;
		}
		catch(HibernateException he)
		{
			LOG.error("Exception Occured While updating tags in updateTankProperty Method");
			he.printStackTrace();
			return false;	
		}

	}

	public boolean updateTankMetaData(TankMetaData tankMetaData) 
	{
		LOG.info("IN TankMetaDataDAOImpl Class updateTankMetaData Method");
		Session session = null;		
		tankMetaData.setTankPropertyList(null);

		int tankId = tankMetaData.getTankId();

		try
		{
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			
			session.update(tankMetaData);	    
			txn.commit();
			LOG.info("Updation of TankMetaData Successs");
			session.close();		   
			return true;			
		}		
		catch(HibernateException he)
		{
			LOG.error("Exception Occured While updating tags in updateTankMetaData Method");
			he.printStackTrace();
			return false;
		}

	}

	//for fetching all the TankDetails
	@SuppressWarnings("unchecked")
	public List<TankMetaData> getAllTankMetaData() 
	{
		LOG.info("IN getAllTankMetaData METHOD");
		Session session = null ;
		List<TankMetaData> list = null;
		try
		{
			session = sessionFactory.openSession();
			list = session.createQuery("from TankMetaData").list();
			session.close();
			for(TankMetaData tankMetaData : list)
			{
				TankMetaData tankMetaData1 = new TankMetaData();
				tankMetaData1 = tankMetaData;
				tankMetaData1.setTankPropertyList(null);
			}
		}
		catch(HibernateException he)
		{
			LOG.error("Exception Occured IN getAllTankMetaData METHOD"+he);
			he.printStackTrace();
		}
		return list;
	}

	//for fetching the individual TankDetails based on id
	public TankMetaData getTankMetaDatabyId(int tankId) 

	{	
		LOG.info("IN getTankMetaDatabyId METHOD");
		Session session = null;	
		TankMetaData tankMetaData = null;

		try
		{
			session=getSessionFactory().openSession();						
			tankMetaData = (TankMetaData)session.get(TankMetaData.class, tankId); 	
			//tankMetaData.setTankPropertyList(null);
			session.close();
		}
		catch(HibernateException he)
		{
			LOG.error("Exception occured IN getTankMetaDatabyId METHOD"+he);
			he.printStackTrace();
		}
		return tankMetaData;
	}
	public int getTankId()
	{
		LOG.info("IN getTankId METHOD");
		TankMetaData tankMetaData = null;
		try
		{
			Session session = sessionFactory.openSession();

			tankMetaData =  (TankMetaData) session.
					createQuery("from TankMetaData ORDER BY tankId DESC").setMaxResults(1).uniqueResult();
			session.close();
		}
		catch(HibernateException he)
		{
			LOG.error("Exception occured IN getTankId METHOD"+he);
			he.printStackTrace();
		}

		int tankId = tankMetaData.getTankId();
		return tankId;
	}	

	public boolean insertTankAlarm(TankAlarmDetails tankAlarmDetails)
	{
		LOG.info("Inside insertTankAlarm method Implementation class");
		Session session = null;
		try
		{
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			session.save(tankAlarmDetails);
			txn.commit();
			session.close();
			return true;
		}
		catch(HibernateException he)
		{
			LOG.error("Error Occured inside insertTankAlarm method Implementation class"+he);
			he.printStackTrace();
			return false;
		}
	}

	public boolean updateTankAlarm(TankAlarmDetails tankAlarmDetails)
	{
		LOG.info("Inside insertTankAlarm method Implementation class");
		Session session = null;
		try
		{
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			session.saveOrUpdate(tankAlarmDetails);
			txn.commit();
			session.close();
			return true;
		}
		catch(HibernateException he)
		{
			LOG.error("Error Occured inside insertTankAlarm method Implementation class"+he);
			he.printStackTrace();
			return false;
		}
	}
	public boolean deleteTankPropertyByTankId(int tankId)
	{
		Session session = null;	
		LOG.info("Inside deleteTankPropertyByTankId method");
		try
		{
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			TankProperty tankProperty = (TankProperty) session.byId(TankProperty.class).load(tankId);
			session.delete(tankProperty);
			txn.commit();
			session.close();
			return true;
		}
		catch(HibernateException e)
		{
			LOG.error("Exception occured while deleting TankProperty"+e);
			e.printStackTrace();
			return false;
		}	
	}
	public boolean deleteTankMetaDataByTankId(int tankId)
	{
		Session session = null;	
		LOG.info("Inside deleteTankMetaDataByTankId method");
		try
		{
			deleteTankPropertyList(tankId);
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			TankMetaData tankMetaData = (TankMetaData) session.byId(TankMetaData.class).load(tankId);
			session.delete(tankMetaData);
			txn.commit();

			session.close();
			return true;
		}
		catch(HibernateException e)
		{
			LOG.error("Exception occured while deleting TankMetaData"+e);
			e.printStackTrace();
			return false;
		}	
	}

	public boolean deletePollRegistersByTankId(int tankId)
	{
		Session session = null;	
		LOG.info("Inside deletePollRegistersByTankId method");
		try
		{
			deleteTankPropertyList(tankId);
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			PollRegisters pollRegisters = (PollRegisters) session.byId(PollRegisters.class).load(tankId);
			session.delete(pollRegisters);
			txn.commit();
			session.close();
			return true;
		}
		catch(HibernateException e)
		{
			LOG.error("Exception occured while deleting PollRegisters"+e);
			e.printStackTrace();
			return false;
		}	

	}


	@SuppressWarnings("unchecked")
	public boolean deleteTankPropertyList(int tankId)
	{
		Session session = null;	
		LOG.info("Inside deleteTankPropertyList method");
		try
		{
			session = sessionFactory.openSession();

			List<TankProperty> tankPropertyList = session.createQuery("from TankProperty where tankId=:tankId").setParameter("tankId", tankId).list();
			for(TankProperty tankProperty : tankPropertyList)
			{
				txn = session.beginTransaction();
				TankProperty tankProperty2 = new TankProperty();
				tankProperty2 = tankProperty;
				session.delete(tankProperty2);
				txn.commit();
			}
			session.close();
			return true;
		}
		catch(HibernateException e)
		{
			LOG.error("Exception occured while deleting TankPropertyList"+e);
			e.printStackTrace();
			return false;
		}	
	}

	public boolean updateTankMetaDataAndProperty(TankMetaData tankMetaData)
	{

		boolean tankMetaDataUpdation = false;
		boolean tankPropertyUpdation = false;
		boolean tankAlarmInsertion = false;

		TankAlarmDetails tankAlarmDetails = null;
		List<TankProperty> tankPropertyList = tankMetaData.getTankPropertyList();


		tankMetaDataUpdation =updateTankMetaData(tankMetaData);	

		int tankId =tankMetaData.getTankId();

		if(tankMetaDataUpdation == true)
		{			
			tankPropertyUpdation = updateTankProperty(tankPropertyList,tankId);

			int maxResults = tankPropertyList.size();

			List<TankProperty> tankPropertyInsertedList = getLatestInsertedTankPropertyList(maxResults);

			for(TankProperty tankProperty : tankPropertyInsertedList)
			{
				TankProperty tankProperty1 = new TankProperty();
				tankProperty1 = tankProperty;				
				boolean  isAlarmEnabled = tankProperty1.isAlarmEnabled();

				if(isAlarmEnabled == true)
				{
					tankAlarmDetails = new TankAlarmDetails();
					tankAlarmDetails.setPropertyName(tankProperty1.getPropertyName());
					tankAlarmDetails.setTankId(tankId);
					tankAlarmDetails.setPropertyId(tankProperty1.getPropertyId());
					tankAlarmDetails.setAlarmAcknowledge(true);
					tankAlarmDetails.setAlarmType("HighThresh");

					tankAlarmInsertion = insertTankAlarm(tankAlarmDetails);
				}
			}
		}
		else
		{
			return false;
		}

		return true;
	}

}//End of Implementation Class


/*	public boolean updateTankMetaDataAndProperty(TankMetaData tankMetaData)
	{
		boolean tankMetaDataInsertion = false;
		boolean tankPropertyInsertion = false;
		@SuppressWarnings("unused")
		boolean tankAlarmInsertion = false;

		TankAlarmDetails tankAlarmDetails = null;

		List<TankProperty> tankPropertyList = tankMetaData.getTankPropertyList();

		tankMetaDataInsertion =updateTankMetaData(tankMetaData);	

		int tankId =getTankId();

		if(tankMetaDataInsertion == true)
		{			
			tankPropertyInsertion = updateTankProperty(tankPropertyList,tankId);

			int maxResults = tankPropertyList.size();

			List<TankProperty> tankPropertyInsertedList = getLatestInsertedTankPropertyList(maxResults);

			for(TankProperty tankProperty : tankPropertyInsertedList)
			{
				TankProperty tankProperty1 = new TankProperty();
				tankProperty1 = tankProperty;				
				boolean  isAlarmEnabled = tankProperty1.isAlarmEnabled();

				if(isAlarmEnabled == true)
				{
					tankAlarmDetails = new TankAlarmDetails();
					tankAlarmDetails.setPropertyName(tankProperty1.getPropertyName());
					tankAlarmDetails.setTankId(tankId);
					tankAlarmDetails.setPropertyId(tankProperty1.getPropertyId());
					tankAlarmDetails.setAlarmAcknowledge(true);
					tankAlarmDetails.setAlarmType("HighThresh");

					tankAlarmInsertion = updateTankAlarm(tankAlarmDetails);
				}
			}

		}
		else
		{
			return tankMetaDataInsertion;
		}
		return tankPropertyInsertion;
	}


 */
/*	public boolean updateTankMetaData(TankMetaData tankMetaData)
	{
		Session session = null;	
		LOG.info("Inside updateTankMetaData method");
		try
		{
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			session.saveOrUpdate(tankMetaData);
			txn.commit();
			session.close();
			System.out.println("Update of updateTankMetaData Succeeded");
			return true;
		}
		catch(HibernateException e)
		{
			LOG.error("Exception occured while updating TankMetaData"+e);
			e.printStackTrace();
			return false;
		}	
	}

	public boolean updateTankProperty(TankProperty tankProperty)
	{
		Session session = null;	
		LOG.info("Inside updateTankProperty method");
		try
		{
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			session.saveOrUpdate(tankProperty);
			txn.commit();
			session.close();
			return true;
		}
		catch(HibernateException e)
		{
			LOG.error("Exception occured while updating TankProperty"+e);
			e.printStackTrace();
			return false;
		}	
	}
 */

/*		if(tankMetaData.getTankId() == 0)
{
	tankMetaDataInsertion =insertTankMetaData(tankMetaData);	

	int tankId =getTankId();

	if(tankMetaDataInsertion == true)
	{			
		tankPropertyInsertion = insertTankProperty(tankPropertyList,tankId);

		int maxResults = tankPropertyList.size();

		List<TankProperty> tankPropertyInsertedList = getLatestInsertedTankPropertyList(maxResults);

		for(TankProperty tankProperty : tankPropertyInsertedList)
		{
			TankProperty tankProperty1 = new TankProperty();
			tankProperty1 = tankProperty;				
			boolean  isAlarmEnabled = tankProperty1.isAlarmEnabled();

			if(isAlarmEnabled == true)
			{
				tankAlarmDetails = new TankAlarmDetails();
				tankAlarmDetails.setPropertyName(tankProperty1.getPropertyName());
				tankAlarmDetails.setTankId(tankId);
				tankAlarmDetails.setPropertyId(tankProperty1.getPropertyId());
				tankAlarmDetails.setAlarmAcknowledge(true);
				tankAlarmDetails.setAlarmType("HighThresh");

				tankAlarmInsertion = insertTankAlarm(tankAlarmDetails);
			}
		}

	}
 */			


/*String hql = "update TankMetaData set price = :price where id = :id";

Query query = session.createQuery(hql);
query.setParameter("price", 488.0f);
query.setParameter("id", 43l);

int rowsAffected = query.executeUpdate();
if (rowsAffected > 0) {
    System.out.println("Updated " + rowsAffected + " rows.");
}*/


