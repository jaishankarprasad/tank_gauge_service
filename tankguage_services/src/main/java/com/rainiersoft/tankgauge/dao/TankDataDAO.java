package com.rainiersoft.tankgauge.dao;

import java.util.List;
import java.util.Set;

import com.rainiersoft.tankgauge.entity.TankData;
import com.rainiersoft.tankgauge.pojo.DashBoard;
public interface TankDataDAO 
{	
	public TankData getTankDetailsbyId(int tankId);
	
	public List<TankData> getAllTanks();
	
	public List<DashBoard> getPropertyValuesForDashBoard();
	
	public Set<Integer> getTankIds();
	
	
}
