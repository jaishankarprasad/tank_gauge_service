package com.rainiersoft.tankgauge.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rainiersoft.tankgauge.dao.AlarmAcknowledgeDAO;
import com.rainiersoft.tankgauge.pojo.AlarmAcknowledgeParams;
import com.rainiersoft.tankgauge.response.AlarmAcknowledgeResponse;

@Path("/rest")
public class AlarmAcknowledgeResources 
{
	private static final Logger LOG = LoggerFactory.getLogger(AlarmAcknowledgeResources.class);

	@Autowired
	AlarmAcknowledgeDAO alarmAcknowledgeDAO;
	
	
	@Path("/alarmAcknowledgeEnable")
	@POST
	@Produces({"application/json"})
	@Consumes({"application/json"})
	
	public Response alarmAcknowledgeEnable(AlarmAcknowledgeParams alarmAcknowledgeParams)
	{
		boolean isEnabled = false;
		boolean isEventUpdated = false;
		AlarmAcknowledgeResponse alarmAcknowledgeResponse = new AlarmAcknowledgeResponse();
		LOG.info("In alarmAcknowledgeEnable Resource Class");
		
		int userId = alarmAcknowledgeParams.getUserId();
		int tankId = alarmAcknowledgeParams.getTankId();
		int alarmId = alarmAcknowledgeParams.getAlarmId();
		String userName = alarmAcknowledgeParams.getUserName();
		
		isEventUpdated = alarmAcknowledgeDAO.updateTankEvent(userId,tankId,userName);
		
		if(isEventUpdated == true)
		{
			isEnabled = alarmAcknowledgeDAO.alarmAcknowledgeEnable(alarmId);
			
			if(isEnabled == true)
			{
				alarmAcknowledgeResponse.setStatus(true);
				alarmAcknowledgeResponse.setMessage("Success");
			}
		}
		else
		{
			alarmAcknowledgeResponse.setStatus(false);
			alarmAcknowledgeResponse.setMessage("Failed");
		}
		
		return Response.status(Response.Status.OK).entity(alarmAcknowledgeResponse).build();
	}
}
