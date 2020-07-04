package com.rainiersoft.tankgauge.dao;

import org.springframework.stereotype.Component;

import com.rainiersoft.tankgauge.entity.*;
import com.rainiersoft.tankgauge.response.*;

@Component
public interface LoginDAO {
	
	public LoginResponse getLoginCredentials(Login login);
}
