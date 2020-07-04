package com.rainiersoft.tankgauge.dao;

import java.util.List;

import com.rainiersoft.tankgauge.entity.TankEvent;
public interface TankEventDAO 
{	
	public TankEvent getTankEventbyId(int tankId);	
	public List<TankEvent> getAllTankEvents();	
}
