package com.rainiersoft.tankgauge.dao;

import java.util.ArrayList;
import java.util.List;

import com.rainiersoft.tankgauge.entity.TankData;
import com.rainiersoft.tankgauge.pojo.TabularInputs;
import com.rainiersoft.tankgauge.pojo.PropertyHistoryInfo;
import com.rainiersoft.tankgauge.pojo.PropertyValue;
import com.rainiersoft.tankgauge.pojo.TankPropertyInfo;
import com.rainiersoft.tankgauge.pojo.TankTrendingInputs;

public interface TankTrendDAO 
{
	public List<TankPropertyInfo> getPropertyValuesbyId(TankTrendingInputs tankTrendingInputs);
	public List<ArrayList<TankData>> getTrendValuesbyId(TankTrendingInputs tankTrendingInputs);
	public List<ArrayList<PropertyValue>> getTrendValues(TankTrendingInputs tankTrendingInputs);

	public List<ArrayList<PropertyValue>> getTrendValuesbyHour(TankTrendingInputs tankTrendingInputs);
	
	public List<PropertyHistoryInfo> getTableValuesListForMinutes(TabularInputs tabularInputs) ;

}
