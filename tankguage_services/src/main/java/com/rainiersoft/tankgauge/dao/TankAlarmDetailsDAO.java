package com.rainiersoft.tankgauge.dao;

import java.util.List;

import com.rainiersoft.tankgauge.pojo.TankAlarmVariables;

public interface TankAlarmDetailsDAO {

	public TankAlarmVariables getTankAlarmDetailsbyId(int alarmId);
	public List<TankAlarmVariables> getAllTankAlarmDetails();
}
