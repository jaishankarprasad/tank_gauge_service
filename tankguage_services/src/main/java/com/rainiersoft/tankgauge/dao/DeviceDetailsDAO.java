package com.rainiersoft.tankgauge.dao;

import java.util.List;

import com.rainiersoft.tankgauge.entity.DeviceDetails;

public interface DeviceDetailsDAO 
{
	public boolean insertDeviceDetails(DeviceDetails deviceDetails);
	
	public DeviceDetails getDeviceDetailsbyId(int deviceid);
	
	public boolean deleteDeviceDetailsbyId(int deviceid);
	
	public boolean updateDeviceDetails(DeviceDetails deviceDetails);
	
	public List<DeviceDetails> getAllDevices();
}
