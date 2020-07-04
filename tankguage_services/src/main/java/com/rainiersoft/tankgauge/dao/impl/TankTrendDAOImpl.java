package com.rainiersoft.tankgauge.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.rainiersoft.tankgauge.dao.TankTrendDAO;
import com.rainiersoft.tankgauge.entity.TankData;
import com.rainiersoft.tankgauge.entity.TankHistoryData;
import com.rainiersoft.tankgauge.entity.TankMetaData;
import com.rainiersoft.tankgauge.entity.TankProperty;
import com.rainiersoft.tankgauge.pojo.DaysCalculator;
import com.rainiersoft.tankgauge.pojo.InputID;
import com.rainiersoft.tankgauge.pojo.PropertyDetails;
import com.rainiersoft.tankgauge.pojo.PropertyHistoryInfo;
import com.rainiersoft.tankgauge.pojo.PropertyIn;
import com.rainiersoft.tankgauge.pojo.PropertyValue;
import com.rainiersoft.tankgauge.pojo.TabularInputs;
import com.rainiersoft.tankgauge.pojo.TankPropertyInfo;
import com.rainiersoft.tankgauge.pojo.TankTrendingInputs;

@Repository
public class TankTrendDAOImpl implements TankTrendDAO 
{
	private static final Logger LOG = LoggerFactory.getLogger(TankTrendDAOImpl.class);

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

	public List<ArrayList<TankData>> getTrendValuesbyId(TankTrendingInputs tankTrendingInputs) 
	{
		Session session =null;
		DaysCalculator daysCalculator = new DaysCalculator();
		List<InputID> idList = tankTrendingInputs.getIdList();
		String fromDate = null ;
		String toDate = null;
		String nextDate = null;
		TankData tankData = null;

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		@SuppressWarnings("unused")
		Date startDate;
		@SuppressWarnings("unused")
		Date endDate ;	
		Date lastUpdated ;	
		List<TankData> tankDataList = null;

		//for Storing Info of TankData of all propIds and tankIds with Specified StartDate and EndDate
		List<ArrayList<TankData>> listDemo = new ArrayList<ArrayList<TankData>>();

		for(InputID inputID : idList)
		{
			tankDataList = new ArrayList<TankData>();
			fromDate = tankTrendingInputs.getStartDate();
			toDate = tankTrendingInputs.getEndDate();

			//for calculating no of results based on no of days mentioned
			int noOfDaysInBetween = daysCalculator.getDifferenceBetweenDates(fromDate, toDate);

			int tankId = inputID.getTankId();
			String propertyName = inputID.getPropertyName();
			//	String propertyName = getPropertyNamebyId(propertyId);
			try
			{
				startDate = formatter.parse(fromDate);
				endDate = formatter.parse(toDate);
				nextDate = fromDate;

				for(int i=0;i<noOfDaysInBetween;i++)
				{
					tankData = new TankData();
					String propertyValue = null;
					lastUpdated = formatter.parse(nextDate);

					session = sessionFactory.openSession();
					Double propValue = (Double) session.createQuery
							("select avg(propertyValue) from TankData where tankId=:tankId and propertyName=:propertyName and DATE(lastUpdated)=:lastUpdated")
							.setParameter("tankId", tankId).setParameter("propertyName", propertyName).setParameter("lastUpdated", lastUpdated).uniqueResult();

					if(propValue != null)
					{
						propertyValue = propValue.toString();	
						LOG.info("PropertyValue is:::"+propertyValue);
					}
					/*					tankData = (TankData) session.createQuery
							("from TankData where tankId=:tankId and propertyName=:propertyName and DATE(lastUpdated)=:lastUpdated")
							.setParameter("tankId", tankId).setParameter("propertyName", propertyName).setParameter("lastUpdated", lastUpdated).uniqueResult();*/

					/*tankDataList = (List<TankData>) session.createQuery
							("from TankData where tankId=:tankId and propertyName=:propertyName and lastUpdated BETWEEN :start AND :end")
							.setParameter("tankId", tankId).setParameter("propertyName", propertyName).setParameter("start", startDate).setParameter("end", endDate).list();
					 */

					//datewise fetching
					nextDate = daysCalculator.getNextDate(fromDate);
					fromDate = nextDate;
					if(propertyValue != null)
					{
						tankData.setPropertyValue(propertyValue);
					}
					else
					{
						tankData.setPropertyValue("null");
					}

					tankDataList.add(tankData);
					session.close();
				}

			}
			catch(HibernateException he)
			{
				he.printStackTrace();
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
			if(tankDataList != null)
			{
				listDemo.add((ArrayList<TankData>) tankDataList);
			}
		}

		return listDemo;
	}


	@SuppressWarnings("unused")
	private String getPropertyNamebyId(int propertyId)
	{
		Session session = null;
		String propertyName = null;
		TankProperty tankProperty = null;
		try
		{
			session = sessionFactory.openSession();
			tankProperty = (TankProperty) session.get(TankProperty.class, propertyId);
			session.close();
		}
		catch(HibernateException he)
		{
			he.printStackTrace();
		}
		propertyName = tankProperty.getPropertyName();
		return propertyName;
	}

	@SuppressWarnings("unused")
	public List<ArrayList<PropertyValue>> getTrendValues(TankTrendingInputs tankTrendingInputs)
	{	
		List<ArrayList<PropertyValue>> propertyValueList = new ArrayList<ArrayList<PropertyValue>>();

		List<ArrayList<TankData>> listDemo = getTrendValuesbyId(tankTrendingInputs);

		List<PropertyValue> listValues = null;

		PropertyValue propertyValue = null;
		String value = null;
		Date timeStamp = null;
		String dateAt = null;
		String propertyName = null;
		String tankName= null;
		int tankId;

		for(List<TankData> tankDataList : listDemo)
		{	
			listValues = new ArrayList<PropertyValue>();
			List<TankData> tankDataList2 = new ArrayList<TankData>();
			tankDataList2 = tankDataList;
			for(TankData tankData : tankDataList)
			{
				propertyValue = new PropertyValue();
				TankData tankData2 = new TankData();
				tankData2 = tankData;
				if(tankData2 != null)
				{
					value = tankData2.getPropertyValue();
					propertyValue.setValue(value);
				}
				listValues.add(propertyValue);
			}
			propertyValueList.add((ArrayList<PropertyValue>) listValues);
		}
		return propertyValueList;
	}

	public List<TankPropertyInfo> getPropertyValuesbyId(TankTrendingInputs tankTrendingInputs)
	{
		List<InputID> idList = tankTrendingInputs.getIdList();

		List<TankPropertyInfo> tankPropDataList = new ArrayList<TankPropertyInfo>();
		TankPropertyInfo tankPropertyName = null;

		for(InputID id : idList)
		{
			tankPropertyName = new TankPropertyInfo();
			InputID id1 = new InputID();
			id1 = id;
			int tankId = id1.getTankId();
			String propertyName = id1.getPropertyName();

			String tankName	= getTankNamebyId(tankId);
			//String propertyName = getPropertyNamebyId(propertyId);

			String tankName_propertyName = null;
			String str = "-";

			if(tankName != null && propertyName != null)
			{
				tankName_propertyName = tankName.concat(str).concat(propertyName);
			}
			tankPropertyName.setTankName_PropertyName(tankName_propertyName);
			tankPropDataList.add(tankPropertyName);
		}
		return tankPropDataList;
	}


	private String getTankNamebyId(int tankId)
	{
		Session session = null;
		String tankName = null;
		TankMetaData tankMetaData = new TankMetaData();
		try
		{
			session = sessionFactory.openSession();
			tankMetaData = (TankMetaData) session.get(TankMetaData.class, tankId);
			session.close();
		}
		catch(HibernateException he)
		{
			he.printStackTrace();
		}
		if(tankMetaData != null)
		{
			tankName = tankMetaData.getName();
		}
		else
		{
			tankName = "null";
		}
		return tankName;
	}  

	public List<ArrayList<TankData>> getTrendValuesbyHour2(TankTrendingInputs tankTrendingInputs) 
	{
		Session session =null;
		DaysCalculator daysCalculator = new DaysCalculator();
		List<InputID> idList = tankTrendingInputs.getIdList();
		String fromDate = null ;
		String toDate = null;
		String nextDate = null;
		TankData tankData = null;

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH");
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH");

		@SuppressWarnings("unused")
		Date startDate;
		@SuppressWarnings("unused")
		Date endDate ;	
		String lastUpdated ;
		Date newDate = null;
		List<TankData> tankDataList = null;

		//for Storing Info of TankData of all propIds and tankIds with Specified StartDate and EndDate
		List<ArrayList<TankData>> listDemo = new ArrayList<ArrayList<TankData>>();

		for(InputID inputID : idList)
		{
			tankDataList = new ArrayList<TankData>();
			fromDate = tankTrendingInputs.getStartDate();
			toDate = tankTrendingInputs.getEndDate();

			//for calculating no of results based on no of days mentioned
			long noOfHoursInBetween = daysCalculator.getDifferenceBetweenDatesH(fromDate, toDate);

			LOG.info("noOfHoursInBetween"+noOfHoursInBetween);

			int tankId = inputID.getTankId();
			String propertyName = inputID.getPropertyName();
			//String propertyName = getPropertyNamebyId(propertyId);
			try
			{
				nextDate = fromDate;

				for(int i=0;i<noOfHoursInBetween;i++)
				{
					tankData = new TankData();
					String propertyValue = null;
					newDate = formatter.parse(nextDate);
					lastUpdated = formatter1.format(newDate);


					LOG.info("lastUpdated:::"+lastUpdated);

					session = sessionFactory.openSession();
					Double propValue = (Double) session.createQuery
							("select avg(propertyValue) from TankHistoryData where tankId=:tankId and propertyName=:propertyName and DATE_FORMAT(lastUpdated, '%Y-%m-%d %H')=:lastUpdated")
							.setParameter("tankId", tankId).setParameter("propertyName", propertyName).setParameter("lastUpdated", lastUpdated).uniqueResult();

					if(propValue != null)
					{
						propertyValue = propValue.toString();	
						LOG.info("PropertyValue is:::"+propertyValue);
					}

					/*		
					 * 			tankData = (TankData) session.createQuery
							("from TankData where tankId=:tankId and propertyName=:propertyName and DATE(lastUpdated)=:lastUpdated")
							.setParameter("tankId", tankId).setParameter("propertyName", propertyName).setParameter("lastUpdated", lastUpdated).uniqueResult();*/

					/*tankDataList = (List<TankData>) session.createQuery
							("from TankData where tankId=:tankId and propertyName=:propertyName and lastUpdated BETWEEN :start AND :end")
							.setParameter("tankId", tankId).setParameter("propertyName", propertyName).setParameter("start", startDate).setParameter("end", endDate).list();
					 */

					//hourwise fetching

					nextDate = daysCalculator.getNextDateH(fromDate);
					fromDate = nextDate;
					if(propertyValue != null)
					{
						tankData.setPropertyValue(propertyValue);
					}
					else
					{
						tankData.setPropertyValue("null");
					}

					tankDataList.add(tankData);
					session.close();
				}

			}
			catch(HibernateException he)
			{
				he.printStackTrace();
			}
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
			if(tankDataList != null)
			{
				listDemo.add((ArrayList<TankData>) tankDataList);
			}
		}

		return listDemo;
	}
	@SuppressWarnings("unused")
	public List<ArrayList<PropertyValue>> getTrendValuesbyHour(TankTrendingInputs tankTrendingInputs)
	{	
		List<ArrayList<PropertyValue>> propertyValueList = new ArrayList<ArrayList<PropertyValue>>();

		List<ArrayList<TankData>> listDemo = getTrendValuesbyHour2(tankTrendingInputs);

		List<PropertyValue> listValues = null;

		PropertyValue propertyValue = null;
		String value = null;
		Date timeStamp = null;
		String dateAt = null;
		String propertyName = null;
		String tankName= null;
		int tankId;

		for(List<TankData> tankDataList : listDemo)
		{	
			listValues = new ArrayList<PropertyValue>();
			List<TankData> tankDataList2 = new ArrayList<TankData>();
			tankDataList2 = tankDataList;
			for(TankData tankData : tankDataList)
			{
				propertyValue = new PropertyValue();
				TankData tankData2 = new TankData();
				tankData2 = tankData;
				if(tankData2 != null)
				{
					value = tankData2.getPropertyValue();
					propertyValue.setValue(value);	
				}
				listValues.add(propertyValue);
			}
			propertyValueList.add((ArrayList<PropertyValue>) listValues);
		}
		return propertyValueList;
	}

	@SuppressWarnings("unchecked")
	public List<String> getPropertyListByTankId(int tankId)
	{
		Session session = null;
		List<String> propsList = new ArrayList<String>();
		try
		{
			session = sessionFactory.openSession();
			propsList = session.createQuery("select propertyName from TankProperty where tankId=:tankId").setParameter("tankId", tankId).list();
			session.close();
		}

		catch(HibernateException he)
		{
			he.printStackTrace();
		}
		return propsList;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public List<PropertyHistoryInfo> getTableValuesListForMinutes(TabularInputs tabularInputs) 
	{
		Session session =null;
		DaysCalculator daysCalculator = new DaysCalculator();

		int tankId = tabularInputs.getTankId();

		String startDateInput = tabularInputs.getStartDate();
		String endDateInput = tabularInputs.getEndDate();

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parsedStartDate = null;
		Date parsedEndDate = null;
		try 
		{
			parsedStartDate = formatter.parse(startDateInput);
			System.out.println("ParsedStartDate::"+parsedStartDate);

		}
		catch (ParseException e)
		{

			e.printStackTrace();
		}

		String endDateCalculated = daysCalculator.getNextDay(startDateInput);

		if(tabularInputs.getTableType().equalsIgnoreCase("HOUR_WISE"))
		{
			endDateInput = endDateInput;
		}
		else if(tabularInputs.getTableType().equalsIgnoreCase("MIN_WISE"))
		{
			endDateInput = endDateCalculated;
		}

		try 
		{
			parsedEndDate = formatter.parse(endDateInput);

			System.out.println("ParsedEndDate::"+parsedEndDate);
		} 
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		List<PropertyDetails> propertyDetailsList = new ArrayList<PropertyDetails>(); 

		List<PropertyIn> propsInfo ;

		PropertyIn pIn ;

		PropertyHistoryInfo propHisInfo = null ;

		List<PropertyHistoryInfo> totalData = new ArrayList<PropertyHistoryInfo>();

		List<TankHistoryData> tankHistoryDataList ;


		try
		{
			session = sessionFactory.openSession();
			tankHistoryDataList = (List<TankHistoryData>) session.createQuery
					("from TankHistoryData where tankId=:tankId and lastUpdated between :parsedStartDate AND :parsedEndDate" ).setParameter("tankId", tankId)
					.setParameter("parsedStartDate", parsedStartDate).setParameter("parsedEndDate", parsedEndDate).list();

			System.out.println("Size of History Data before AGG::"+tankHistoryDataList.size());

			tankHistoryDataList = aggreagteHistoryData(tankHistoryDataList, tabularInputs.getTableType());

			System.out.println("Size of History Data After AGG::"+tankHistoryDataList.size());


			for (int i=0; i < tankHistoryDataList.size(); i++)
			{
				propHisInfo = new PropertyHistoryInfo();

				if(tabularInputs.getTableType().equalsIgnoreCase("HOUR_WISE"))
				{
					if (isProcessedHour(tankHistoryDataList.get(i), totalData) == true)
					{
						continue;
					}	
				}
				else if (tabularInputs.getTableType().equalsIgnoreCase("MIN_WISE"))
				{
					if (isProcessedMinute(tankHistoryDataList.get(i), totalData) == true)
					{
						continue;
					}	

				}

				propHisInfo.setDateAndTime(tankHistoryDataList.get(i).getLastUpdated().toString());

				//System.out.println("TimeStamp::"+tankHistoryDataList.get(i).getLastUpdated().toString());

				propsInfo = new ArrayList<PropertyIn>();

				for (int j = i; j < tankHistoryDataList.size(); j++)
				{					
					if (tankHistoryDataList.get(i).getTankId() == tankHistoryDataList.get(j).getTankId() )
					{
						if (tabularInputs.getTableType().equalsIgnoreCase("HOUR_WISE"))
						{
							if (  isSameHour(tankHistoryDataList.get(i), tankHistoryDataList.get(j)) == 0)
							{
								pIn = new PropertyIn();
								pIn.setPropertyName(tankHistoryDataList.get(j).getPropertyName());

								pIn.setPropertyValue(tankHistoryDataList.get(j).getPropertyValue());

								propsInfo.add(pIn);								
							}
						}
						else if (tabularInputs.getTableType().equalsIgnoreCase("MIN_WISE"))
						{
							if ( isSameHour( tankHistoryDataList.get(i), tankHistoryDataList.get(j)) == 0 && isSame10Min(tankHistoryDataList.get(i), tankHistoryDataList.get(j)) == 0 )
							{
								pIn = new PropertyIn();
								pIn.setPropertyName(tankHistoryDataList.get(j).getPropertyName());
								pIn.setPropertyValue(tankHistoryDataList.get(j).getPropertyValue());							

								propsInfo.add(pIn);

							}
						}
					}
				}
				if(tankHistoryDataList != null)
				{
					System.out.println("PropsInfoSize at TimeStamp::"+tankHistoryDataList.get(i).getLastUpdated().toString()+"::"+"is"+propsInfo.size());
				}
				propHisInfo.setPropInfo(propsInfo);
				totalData.add(propHisInfo);
			}

			session.close();
		}

		catch(HibernateException he)
		{
			he.printStackTrace();
		}

		if(totalData != null)
		{
			System.out.println("ReturnListSize::"+totalData.size());
		}

		return totalData;
	}


	@SuppressWarnings("deprecation")
	private boolean isProcessedHour(TankHistoryData curItem, List<PropertyHistoryInfo> totalData)
	{
		if (null == totalData || totalData.size() ==0)
			return false;

		for (int i =0; i < totalData.size(); i++)
		{	
			String dt = totalData.get(i).getDateAndTime();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date dateCheck = null;
			try
			{
				dateCheck = sdf.parse(dt);
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			} 
			if (curItem.getLastUpdated().getHours() == dateCheck.getHours())
			{
				return true;
			}
		}

		return false;
	}

	@SuppressWarnings("deprecation")
	private boolean isProcessedMinute(TankHistoryData curItem, List<PropertyHistoryInfo> totalData)
	{
		if (null == totalData || totalData.size() ==0)
			return false;

		for (int i =0; i < totalData.size(); i++)
		{	
			String dt = totalData.get(i).getDateAndTime();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date dateCheck = null;
			try
			{
				dateCheck = sdf.parse(dt);
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			} 
			if (curItem.getLastUpdated().getHours() == dateCheck.getHours())
			{
				if (curItem.getLastUpdated().getMinutes() == dateCheck.getMinutes())
				{
					return true;
				}

			}


			/*			if (curItem.getLastUpdated().toString().equalsIgnoreCase(totalData.get(i).getDateAndTime()))
			{
				return true;
			}
			 */
		}

		return false;
	}


	@SuppressWarnings({ "deprecation", "unused" })
	private int returnProcessBase10Min(TankHistoryData  newItem)
	{
		if (newItem.getLastUpdated().getMinutes() >= 0 && newItem.getLastUpdated().getMinutes() <=9  )
			return 0;

		return  newItem.getLastUpdated().getMinutes() / 10;

	}


	@SuppressWarnings("deprecation")
	private int isSameHour(TankHistoryData curItem, TankHistoryData newItem)
	{

		if ( curItem.getLastUpdated().getHours() == newItem.getLastUpdated().getHours() )
		{
			return 0;
		}

		return  (newItem.getLastUpdated().getHours() - curItem.getLastUpdated().getHours()); 

	}

	@SuppressWarnings("deprecation")
	private int returnBase10Min(TankHistoryData  newItem)
	{
		if (newItem.getLastUpdated().getMinutes() >= 0 && newItem.getLastUpdated().getMinutes() <=9  )
			return 0;

		return  newItem.getLastUpdated().getMinutes() / 10;

	}

	private int isSame10Min(TankHistoryData curItem, TankHistoryData newItem)
	{

		return  (returnBase10Min (newItem) - returnBase10Min(curItem));
	}

	private  boolean isSamePropertyAndTank  (TankHistoryData currItem, TankHistoryData newItem)
	{
		if (currItem.getTankId() == newItem.getTankId())
		{
			if (currItem.getPropertyId() == newItem.getPropertyId())
			{
				return true;
			}
		}

		return false;
	}

	private  TankHistoryData appendObject (TankHistoryData currItem, TankHistoryData newItem)
	{
		if (currItem.getPropertyId() == newItem.getPropertyId())
		{
			Float f1 = Float.parseFloat(currItem.getPropertyValue());
			Float f2 = Float.parseFloat(newItem.getPropertyValue());
			float f = new Float(f1).floatValue() + new Float(f2).floatValue();

			currItem.setPropertyValue(""+f);
		}

		return currItem;

	}

	private  TankHistoryData averagedObject (TankHistoryData currItem, int AvgCnt)
	{
		Float f = Float.parseFloat(currItem.getPropertyValue())/AvgCnt;

		double roundOff = 0;

		if(currItem.getPropertyName().equalsIgnoreCase("PRODUCT LEVEL"))
		{
			int round = Math.round(f);
			currItem.setPropertyValue(""+round);
			return currItem;
		}	
		if(currItem.getPropertyName().equalsIgnoreCase("PRODUCT TEMP"))
		{
			roundOff = Math.round(f * 100.0) / 100.0;
		}
		if(currItem.getPropertyName().equalsIgnoreCase("OBS. Density"))
		{
			roundOff = Math.round(f * 100.0) / 100.0;
		}
		if(currItem.getPropertyName().equalsIgnoreCase("TOV"))
		{
			roundOff = Math.round(f * 100.0) / 100.0;
		}
		if(currItem.getPropertyName().equalsIgnoreCase("GOV"))
		{
			roundOff = Math.round(f * 100.0) / 100.0;
		}
		if(currItem.getPropertyName().equalsIgnoreCase("NOV"))
		{
			roundOff = Math.round(f * 100.0) / 100.0;
		}
		if(currItem.getPropertyName().equalsIgnoreCase("ULLAGE"))
		{
			roundOff = Math.round(f * 100.0) / 100.0;
		}
		if(currItem.getPropertyName().equalsIgnoreCase("GROSS MASS"))
		{
			roundOff = Math.round(f * 100.0) / 100.0;
		}

		currItem.setPropertyValue(""+roundOff);

		return currItem;
	}


	private List<TankHistoryData> groupByHour(List<TankHistoryData> tkHisList)
	{
		List<TankHistoryData> processedList = new ArrayList<TankHistoryData>();

		for(int i = 0; i < tkHisList.size() ; i++)
		{
			TankHistoryData tk = null;			

			if(tkHisList.get(i).getReserve1() != null && tkHisList.get(i).getReserve1().equalsIgnoreCase("Done"))
			{
				continue;
			}

			int AvgCount = 0;
			for (int j = i ; j < tkHisList.size(); j++)
			{
				if (isSamePropertyAndTank (tkHisList.get(i), tkHisList.get(j)))
				{

					if ( isSameHour(tkHisList.get(i), tkHisList.get(j) ) == 0)
					{					
						tk = appendObject (tkHisList.get(i), tkHisList.get(j));

						if(tkHisList.get(i).getTankId() == 49 && tkHisList.get(i).getPropertyId() == 112 )
						{
							System.out.println("AvgCount::"+AvgCount);
							System.out.println("DateandTime::"+tk.getLastUpdated().toString());
						}

						tkHisList.get(j).setReserve1("Done");

						AvgCount++;							
					}
				}				
			}
			processedList.add(averagedObject(tk, AvgCount));
		}

		return processedList;
	}
	private List<TankHistoryData> groupBy10MinWise(List<TankHistoryData> tkHisList)
	{
		List<TankHistoryData> processedList = new ArrayList<TankHistoryData>();

		for(int i = 0; i < tkHisList.size() ; i++)
		{
			TankHistoryData tk = null;

			if(tkHisList.get(i).getReserve1() != null && tkHisList.get(i).getReserve1().equalsIgnoreCase("Done"))
			{
				continue;
			}
			int AvgCount = 0;
			for (int j = i ; j < tkHisList.size(); j++)
			{
				if (isSamePropertyAndTank (tkHisList.get(i), tkHisList.get(j)))
				{
					if ( isSameHour(tkHisList.get(i), tkHisList.get(j) ) == 0)
					{
						if ( isSame10Min(tkHisList.get(i), tkHisList.get(j) ) == 0)
						{					
							tk = appendObject (tkHisList.get(i), tkHisList.get(j));

							tkHisList.get(j).setReserve1("Done");

							AvgCount++;						
						}
					}	
				}
			}
			processedList.add(averagedObject(tk, AvgCount));
		}

		return processedList;
	}

	public List<TankHistoryData> aggreagteHistoryData(List<TankHistoryData> tkHisList,String tableType)
	{

		if (tableType.equalsIgnoreCase("HOUR_WISE" ))
		{
			return groupByHour(tkHisList);
		}
		else if (tableType.equalsIgnoreCase("MIN_WISE"))
		{
			return groupBy10MinWise(tkHisList);
		}
		return null;
	}
}

