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

import com.rainiersoft.tankgauge.dao.TankDataTypeDAO;
import com.rainiersoft.tankgauge.entity.TankDataType;
import com.rainiersoft.tankgauge.response.TankDataTypeResponse;

@Path("/rest")
public class TankDataTypeResources 
{
	private static final Logger LOG = LoggerFactory.getLogger(TankDataTypeResources.class);

	@Autowired
	TankDataTypeDAO tankDataTypeDAO;

	@Path("/getAllTankDataTypeDetails")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Response getAllTankDataTypeDetails()
	{
		LOG.info("In getAllTankDataTypeDetails Resources Method");

		List<TankDataType> tankDataTypeList = tankDataTypeDAO.getAllTankDataTypeDetails();

		TankDataTypeResponse tankDataTypeResponse = new TankDataTypeResponse();

		if(tankDataTypeList != null)
		{
			tankDataTypeResponse.setStatus(true);
			tankDataTypeResponse.setMessage("Success");
			tankDataTypeResponse.setList(tankDataTypeList);
			return Response.status(Response.Status.OK).entity(tankDataTypeResponse).build();
		}
		tankDataTypeResponse.setStatus(false);
		tankDataTypeResponse.setMessage("Failure");
		return Response.status(Response.Status.NO_CONTENT).entity(tankDataTypeResponse).build();

	}

	@Path("/getTankDataTypeDetailsbyId")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Response getTankDataTypeDetailsbyId(@QueryParam("id") int propertyId)
	{
		LOG.info("In getTankDataTypeDetailsbyId Resources Method");

		TankDataType tankDataType = tankDataTypeDAO.getTankDataTypeDetailsbyId(propertyId);

		List<TankDataType> tankDataTypeList = Arrays.asList(tankDataType);

		TankDataTypeResponse tankDataTypeResponse = new TankDataTypeResponse();

		if(tankDataTypeList != null)
		{
			tankDataTypeResponse.setStatus(true);
			tankDataTypeResponse.setMessage("Success");
			tankDataTypeResponse.setList(tankDataTypeList);
			
		}
		else
		{
			tankDataTypeResponse.setStatus(false);
			tankDataTypeResponse.setMessage("Failure");
		}

//		return Response.status(Response.Status.NO_CONTENT).entity(tankDataTypeResponse).build();
		return Response.status(Response.Status.OK).entity(tankDataTypeResponse).build();
	}
}
