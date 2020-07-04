package com.rainiersoft.tankgauge.dao;

import java.util.List;

import com.rainiersoft.tankgauge.entity.TankDataType;

public interface TankDataTypeDAO {
	
	public List<TankDataType> getAllTankDataTypeDetails();
	
	public TankDataType getTankDataTypeDetailsbyId(int propertyId);

}
