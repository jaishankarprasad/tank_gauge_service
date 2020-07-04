package com.rainiersoft.tankgauge.dao;

import java.util.List;

import com.rainiersoft.tankgauge.pojo.TotalTankDetails;

public interface TotalTankDetailsDAO 
{	
	public TotalTankDetails getTotalTankDetailsbyId(int tankId);
	
	public List<TotalTankDetails> getAllTotalTanks();	
	
}
