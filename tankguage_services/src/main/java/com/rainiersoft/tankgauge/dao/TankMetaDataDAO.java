package com.rainiersoft.tankgauge.dao;

import java.util.List;

import com.rainiersoft.tankgauge.entity.TankMetaData;
public interface TankMetaDataDAO 
{
	public boolean insertTankMetaDataAndProperty(TankMetaData tankMetaData);
	
	public TankMetaData getTankMetaDatabyId(int tankId);
	
	public List<TankMetaData> getAllTankMetaData();
	
	public boolean updateTankMetaDataAndProperty(TankMetaData tankMetaData); 
	
	public boolean tankExistCheck(TankMetaData tankMetaData);
	
	//public boolean updateTankProperty(TankProperty tankProperty);
	
	public boolean deleteTankMetaDataByTankId(int tankId);
	
	public boolean deleteTankPropertyByTankId(int tankId);

	
}
