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

import com.rainiersoft.tankgauge.dao.impl.TankDataDAOImpl;
import com.rainiersoft.tankgauge.entity.TankData;
import com.rainiersoft.tankgauge.pojo.DashBoard;
import com.rainiersoft.tankgauge.pojo.TankDashBoardResponse;
import com.rainiersoft.tankgauge.response.TankResponse;


@Path("/rest")
public class TankDataResources 
{
	private static final Logger LOG = LoggerFactory.getLogger(TankDataResources.class);

	@Autowired
	TankDataDAOImpl tankDataDAOImpl;

	@Path("/getAllTanks")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})	
	public Response getAllTanks()	
	{
		System.out.println("Entered into getAllTanks resource class method........");
		LOG.info("Entered into getAllTanks resource class method........");

		List<TankData> list = tankDataDAOImpl.getAllTanks();
		TankResponse tankResponse = new TankResponse();		

		if(list != null)
		{
			tankResponse.setList(list);
			tankResponse.setStatus(true);
			tankResponse.setMessage("Success");

		}
		else
		{
			tankResponse.setStatus(false);
			tankResponse.setMessage("No Content");
		}

//		return Response.status(Response.Status.NO_CONTENT).entity(tankResponse).build();
		return Response.status(Response.Status.OK).entity(tankResponse).build();

	}

	@SuppressWarnings("unchecked")
	@Path("/getTankDetailsbyId")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})

	public Response getTankDetailsbyId(@QueryParam("id") int tankId)
	{
		System.out.println("Entered into getTankDetails resource class method........");
		LOG.info("Entered into getTankDetails resource class method........");

		TankData tankDetails =new TankData();
		tankDetails = tankDataDAOImpl.getTankDetailsbyId(tankId);

		@SuppressWarnings("rawtypes")
		List tankDetailsList = Arrays.asList(tankDetails);


		TankResponse tankResponse = new TankResponse();

		if(tankDetails != null)
		{
			tankResponse.setList(tankDetailsList);
			tankResponse.setStatus(true);
			tankResponse.setMessage("Success");
			
		}
		else
		{
			tankResponse.setStatus(false);
			tankResponse.setMessage("No Content");
		}
		
		return Response.status(Response.Status.OK).entity(tankResponse).build();
//		return Response.status(Response.Status.NO_CONTENT).entity(tankResponse).build();
	}

	
	
	@Path("/getTankIds")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})	
	public Response getTankIds()	
	{
		System.out.println("Entered into getTankIds resource class method........");
		LOG.info("Entered into getTankIds resource class method........");

		List<DashBoard> listDB = tankDataDAOImpl.getPropertyValuesForDashBoard();
		TankDashBoardResponse tankDBResponse = new TankDashBoardResponse();		

		if(listDB != null)
		{
			//tankResponse.setList(list);
			tankDBResponse.setStatus(true);
			tankDBResponse.setMessage("Success");
			tankDBResponse.setDashBoardValues(listDB);

		}
		else
		{
			tankDBResponse.setStatus(false);
			tankDBResponse.setMessage("No Content");
		}

//		return Response.status(Response.Status.NO_CONTENT).entity(tankResponse).build();
		return Response.status(Response.Status.OK).entity(tankDBResponse).build();

	}

}