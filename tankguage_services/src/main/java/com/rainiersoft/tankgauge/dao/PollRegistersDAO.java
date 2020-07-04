package com.rainiersoft.tankgauge.dao;

import java.util.List;

import com.rainiersoft.tankgauge.entity.PollRegisters;

public interface PollRegistersDAO {

	public List<PollRegisters> getAllPollRegisters();
	
	public PollRegisters getPollRegistersbyId(int propertyId);
}
