package com.rainiersoft.tankgauge.dao;

import java.util.List;

import com.rainiersoft.tankgauge.entity.TankData;
import com.rainiersoft.tankgauge.pojo.ReportInputs;

public interface ReportManagementDAO {

	public List<TankData> getReports(ReportInputs reportInputs);
}
