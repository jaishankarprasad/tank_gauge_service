package com.rainiersoft.tankgauge.reportsgenerator.interfaces;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

public interface GenericSQLReportsResultsetProcessor {

	public List<String[]> processSQLResultset(ResultSet sqlResultset, Properties repConfigProperties);
	
}
