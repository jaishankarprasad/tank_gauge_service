package com.rainiersoft.tankgauge.resources;

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

import com.rainiersoft.tankgauge.dao.TankPropertyListDAO;
import com.rainiersoft.tankgauge.entity.TankProperty;
import com.rainiersoft.tankgauge.response.TankPropertyResponse;

@Path("/rest")
public class TankPropertyListResources 
{
	private static final Logger LOG = LoggerFactory.getLogger(TankPropertyListResources.class);
	@Autowired
	TankPropertyListDAO tankPropertyListDAO;
	
	@Path("/getPropertyListbyTankId")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})	
	public Response getPropertyListbyTankId(@QueryParam("id") int tankId)	
	{
		LOG.info("Entered into getPropertyListbyTankId resource class method........");

		List<TankProperty> list = tankPropertyListDAO.getPropertyListbyTankId(tankId);
		TankPropertyResponse tankPropertyResponse = new TankPropertyResponse();		

		if(list != null)
		{
			tankPropertyResponse.setList(list);
			tankPropertyResponse.setStatus(true);
			tankPropertyResponse.setMessage("Success");

		}
		else
		{
			tankPropertyResponse.setStatus(false);
			tankPropertyResponse.setMessage("No Content");
		}

		return Response.status(Response.Status.OK).entity(tankPropertyResponse).build();

	}

}
