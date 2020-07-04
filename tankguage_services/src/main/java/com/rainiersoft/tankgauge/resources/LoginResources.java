package com.rainiersoft.tankgauge.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rainiersoft.tankgauge.dao.LoginDAO;
import com.rainiersoft.tankgauge.entity.Login;
import com.rainiersoft.tankgauge.response.LoginResponse;
import com.rainiersoft.tankgauge.response.ResponseMessage;

@Path("/rest")
@Component
public class LoginResources {
	
	private static final Logger LOG = LoggerFactory.getLogger(LoginResources.class);

	@Autowired
	LoginDAO loginDAO;
	
	@Path("getAuthentication")
	@POST
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public ResponseMessage getAuthentication(Login login)
	{		
		System.out.println("Entered into getAuthentication resource class method........");
		LOG.info("Entered into getAuthentication resource class method........");
		
		LoginResponse loginResponse = (LoginResponse) loginDAO.getLoginCredentials(login);
		
		ResponseMessage res = new ResponseMessage();
		
		if(loginResponse != null)
		{			
			if(loginResponse.getPassword() != null && loginResponse.getPassword().equalsIgnoreCase(login.getPassword()))
			{
				res.setStatus(true);
				res.setMessage("You have Logged in Suucessfully");
				res.setUsername(loginResponse.getUsername());
				res.setRoleid(loginResponse.getRoleid());
			}
			else
			{
				res.setStatus(false);
				res.setMessage("The password you have entered is not correct");
			}
		}
		else
		{
			res.setStatus(false);
			res.setMessage("The username you have entered is not correct");
		}		
		return res;
	}
}
