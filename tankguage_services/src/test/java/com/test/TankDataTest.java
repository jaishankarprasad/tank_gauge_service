package com.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rainiersoft.tankgauge.dao.impl.TankMetaDataDAOImpl;
import com.rainiersoft.tankgauge.entity.TankData;
import com.rainiersoft.tankgauge.entity.TankProperty;

public class TankDataTest {

	public static void main(String[] args) 
	{
		@SuppressWarnings("unused")
		List<ArrayList<TankData>> valuesList = null;
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

		TankMetaDataDAOImpl tankMetaDataDAO = (TankMetaDataDAOImpl)ac.getBean("tankMetaDataDAOImpl");
		
		
		List<TankProperty> tankPropertyList = new ArrayList<TankProperty>();
		
		TankProperty tankProperty = new TankProperty();
		
		tankProperty.setPropertyName("Mass");
		
		tankPropertyList.add(tankProperty);
		
		int tankId = 12;
		
		tankMetaDataDAO.insertTankProperty(tankPropertyList, tankId);
		
	}
}
