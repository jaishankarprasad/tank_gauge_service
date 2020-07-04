package com.testcase.tanktrend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rainiersoft.tankgauge.dao.impl.TankMetaDataDAOImpl;
import com.rainiersoft.tankgauge.dao.impl.TankTrendDAOImpl;
import com.rainiersoft.tankgauge.entity.TankData;
import com.rainiersoft.tankgauge.pojo.InputID;
import com.rainiersoft.tankgauge.pojo.TankTrendingInputs;

public class TankTrendTest
{

	public static void main(String[] args) 
	{
		List<ArrayList<TankData>> valuesList = null;
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

		TankMetaDataDAOImpl tankTrendDAO = (TankMetaDataDAOImpl)ac.getBean("tankMetaDataDAOImpl");

		int nn = tankTrendDAO.getTankIdByTankName("Tank-K");
		
		System.out.println(nn);
	}
}
