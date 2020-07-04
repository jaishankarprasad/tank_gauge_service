package com.rainiersoft.tankgauge.dao;

import java.util.List;

import com.rainiersoft.tankgauge.entity.TankHistoryData;

public interface TankHistoryPropertyDAO 
{
	public List<TankHistoryData>  getAllTankHistoryProperty();
	
	public TankHistoryData getTankHistoryPropertybyId(int propertyId);
}
