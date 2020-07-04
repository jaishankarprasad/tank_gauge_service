package com.testcase.device;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rainiersoft.tankgauge.dao.impl.DeviceDetailsDAOImpl;
import com.rainiersoft.tankgauge.entity.DeviceDetails;

public class DevicesTest {

	public static void main(String[] args) {

		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

		DeviceDetailsDAOImpl dev = (DeviceDetailsDAOImpl)ac.getBean("deviceInfoDAOImpl");
		
		List<DeviceDetails> list = dev.getAllDevices();
			
		DeviceDetails[] deviceArray = new DeviceDetails[list.size()];
		
		int i = 0;
		
		for (DeviceDetails deviceInfo : list) 
		{
		  deviceArray[i] = (DeviceDetails) deviceInfo;
		  System.out.println(deviceArray[i].getDeviceName());
		  i++;
		}
		
		System.out.println(list.size());
		
		System.out.println(list);
	}

}
