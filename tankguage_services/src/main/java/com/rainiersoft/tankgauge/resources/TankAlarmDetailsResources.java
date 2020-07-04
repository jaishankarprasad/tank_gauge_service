package com.rainiersoft.tankgauge.resources;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rainiersoft.tankgauge.dao.impl.TankAlarmDetailsDAOImpl;
import com.rainiersoft.tankgauge.pojo.TankAlarmVariables;
import com.rainiersoft.tankgauge.response.AlarmResponse;

@Path("/rest")
public class TankAlarmDetailsResources {

	private static final Logger LOG = LoggerFactory.getLogger(TankAlarmDetailsResources.class);

	@Autowired
	TankAlarmDetailsDAOImpl tankAlarmDetailsDAOImpl;


	@Path("/getAllTankAlarmDetails")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})	
	public Response getAllTankAlarmDetails()	
	{
		System.out.println("Entered into getAllTankAlarmDetails resource class method........");
		LOG.info("Entered into getAllTankAlarmDetails resource class method........");

		List<TankAlarmVariables> list = tankAlarmDetailsDAOImpl.getAllTankAlarmDetails();

		AlarmResponse alarmResponse = new AlarmResponse();


		if(list != null)
		{
			alarmResponse.setList(list);
			alarmResponse.setStatus(true);
			alarmResponse.setMessage("Success");
		}
		else
		{
			alarmResponse.setStatus(false);
			alarmResponse.setMessage("No Content");		
		}

		return Response.status(Response.Status.OK).entity(alarmResponse).build();
	}

	@SuppressWarnings("unchecked")
	@Path("/getTankAlarmDetailsbyId")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Response getTankAlarmDetailsbyId(@QueryParam("id") int alarmId)
	{
		System.out.println("Entered into getTankAlarmDetailsbyId resource class method........");
		LOG.info("Entered into getTankAlarmDetailsbyId resource class method........");

		TankAlarmVariables tankAlarmVariables =new TankAlarmVariables();
		tankAlarmVariables = tankAlarmDetailsDAOImpl.getTankAlarmDetailsbyId(alarmId);

		@SuppressWarnings("rawtypes")
		List tankAlarmDetailsList = Arrays.asList(tankAlarmVariables);

		AlarmResponse alarmResponse = new AlarmResponse();

		if(tankAlarmVariables != null)
		{
			alarmResponse.setList( tankAlarmDetailsList);
			alarmResponse.setMessage("Success");
			alarmResponse.setStatus(true);
		}
		else
		{
			alarmResponse.setMessage("No Content");
			alarmResponse.setStatus(false);
		}

		return Response.status(Response.Status.OK).entity(alarmResponse).build();

		//		return Response.status(Response.Status.NO_CONTENT).entity(alarmResponse).build();
	}

}//End of Resources Class
