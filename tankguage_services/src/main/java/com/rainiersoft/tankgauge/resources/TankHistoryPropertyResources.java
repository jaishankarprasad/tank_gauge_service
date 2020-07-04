package com.rainiersoft.tankgauge.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rainiersoft.tankgauge.dao.TankHistoryPropertyDAO;
import com.rainiersoft.tankgauge.entity.TankHistoryData;
import com.rainiersoft.tankgauge.response.TankHistoryPropertyResponse;

@Path("/rest")
public class TankHistoryPropertyResources 
{
	private static final Logger LOG = LoggerFactory.getLogger(TankHistoryPropertyResources.class);

	@Autowired
	TankHistoryPropertyDAO tankHistoryPropertyDAO;


	@Path("/getAllTankHistoryProperty")
	@GET
	@Produces({"application/json"})
	@Consumes({"application/json"})
	public Response getAllTankHistoryProperty()
	{
		LOG.info("In getAllTankHistoryProperty Resources Method");

		List<TankHistoryData> tankHistoryPropertyList = 
				tankHistoryPropertyDAO.getAllTankHistoryProperty();

		TankHistoryPropertyResponse tankHistoryPropertyResponse = new TankHistoryPropertyResponse();

		if(tankHistoryPropertyList != null)
		{
			tankHistoryPropertyResponse.setList(tankHistoryPropertyList);
			tankHistoryPropertyResponse.setStatus(true);
			tankHistoryPropertyResponse.setMessage("Succcess");
		}
		else
		{
			tankHistoryPropertyResponse.setStatus(false);
			tankHistoryPropertyResponse.setMessage("Failed");
		}
		return Response.status(Response.Status.OK).entity(tankHistoryPropertyResponse).build();
	}
}
