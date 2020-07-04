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

import com.rainiersoft.tankgauge.dao.impl.TankEventDAOImpl;
import com.rainiersoft.tankgauge.entity.TankEvent;
import com.rainiersoft.tankgauge.response.TankEventResponse;


@Path("/rest")
public class TankEventResources 
{
	private static final Logger LOG = LoggerFactory.getLogger(TankEventResources.class);

	@Autowired
	TankEventDAOImpl tankEventDAOImpl;

	@Path("/getAllTankEvents")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})	
	public Response getAllTankEvents()	
	{
		System.out.println("Entered into getAllTankEvents resource class method........");
		LOG.info("Entered into getAllTankEvents resource class method........");

		List<TankEvent> list = tankEventDAOImpl.getAllTankEvents();
		TankEventResponse tankEventResponse = new TankEventResponse();		

		if(list != null)
		{
			tankEventResponse.setList(list);
			tankEventResponse.setStatus(true);
			tankEventResponse.setMessage("Success");

		}
		else
		{
			tankEventResponse.setStatus(false);
			tankEventResponse.setMessage("No Content");
		}

		return Response.status(Response.Status.OK).entity(tankEventResponse).build();

//		return Response.status(Response.Status.NO_CONTENT).entity(tankEventResponse).build();
	}

	@SuppressWarnings("unchecked")
	@Path("/getTankEventbyId")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})

	public Response getTankEventbyId(@QueryParam("id") int tankId)
	{
		System.out.println("Entered into getTankEventbyId resource class method........");
		LOG.info("Entered into getTankEventbyId resource class method........");

		TankEvent tankEvent =new TankEvent();
		tankEvent = tankEventDAOImpl.getTankEventbyId(tankId);

		@SuppressWarnings("rawtypes")
		List tankDetailsList = Arrays.asList(tankEvent);


		TankEventResponse tankEventResponse = new TankEventResponse();

		if(tankEvent != null)
		{
			tankEventResponse.setList(tankDetailsList);
			tankEventResponse.setStatus(true);
			tankEventResponse.setMessage("Success");
		}
		else
		{
			tankEventResponse.setStatus(false);
			tankEventResponse.setMessage("No Content");
		}
		
		return Response.status(Response.Status.OK).entity(tankEventResponse).build();
//		return Response.status(Response.Status.NO_CONTENT).entity(tankEventResponse).build();
	}

}//End of Resources Class

