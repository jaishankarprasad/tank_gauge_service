package com.rainiersoft.tankgauge.dao;

public interface AlarmAcknowledgeDAO {
	
	public boolean alarmAcknowledgeEnable(int alarmId);

	public boolean updateTankEvent(int userId,int tankId,String userName);

}
