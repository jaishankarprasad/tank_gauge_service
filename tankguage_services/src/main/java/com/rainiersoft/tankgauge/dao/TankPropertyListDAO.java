package com.rainiersoft.tankgauge.dao;

import java.util.List;

import com.rainiersoft.tankgauge.entity.TankProperty;

public interface TankPropertyListDAO {

	public List<TankProperty> getPropertyListbyTankId(int tankId);
}
