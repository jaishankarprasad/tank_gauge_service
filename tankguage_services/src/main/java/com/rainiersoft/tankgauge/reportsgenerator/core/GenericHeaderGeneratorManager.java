package com.rainiersoft.tankgauge.reportsgenerator.core;

import java.util.HashMap;
import java.util.Map;

public interface GenericHeaderGeneratorManager {
	
	public Map<String, String> headerKeys = new HashMap<String, String>();
	
	public void setHeaders();
	public Map<String, String> getHeaders();
	
}
