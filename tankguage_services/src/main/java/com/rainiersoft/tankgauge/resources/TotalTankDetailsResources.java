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

import com.rainiersoft.tankgauge.dao.impl.TotalTankDetailsDAOImpl;
import com.rainiersoft.tankgauge.pojo.TotalTankDetails;
import com.rainiersoft.tankgauge.response.TotalTankResponse;

@Path("/rest")
public class TotalTankDetailsResources 
{
	private static final Logger LOG = LoggerFactory.getLogger(TotalTankDetailsResources.class);

	@Autowired
	TotalTankDetailsDAOImpl totalTankDetailsDAOImpl;
	

	@Path("/getAllTotalTanks")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})	
	public Response getAllTotalTanks()	
	{
		LOG.info("Entered into getAllTotalTanks resource class method........");

		List<TotalTankDetails> list = totalTankDetailsDAOImpl.getTotalTankDetailsList();
		TotalTankResponse totalTankankResponse = new TotalTankResponse();		

		if(list != null)
		{
			totalTankankResponse.setList(list);
			totalTankankResponse.setStatus(true);
			totalTankankResponse.setMessage("Success");
		}
		else
		{
			totalTankankResponse.setStatus(false);
			totalTankankResponse.setMessage("No Content");
		}		

		return Response.status(Response.Status.OK).entity(totalTankankResponse).build();			

		//return Response.status(Response.Status.NO_CONTENT).entity(totalTankankResponse).build();
	}

	@SuppressWarnings("unchecked")
	@Path("/getTotalTankDetailsbyId")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Response getTotalTankDetailsbyId(@QueryParam("id") int tankId)
	{
		LOG.info("Entered into getTotalTankDetails resource class method........");

		TotalTankDetails totalTankDetails =new TotalTankDetails();
		totalTankDetails = totalTankDetailsDAOImpl.getTotalTankDetailsbyId(tankId);

		@SuppressWarnings("rawtypes")
		List tankDetailsList = Arrays.asList(totalTankDetails);

		TotalTankResponse totalTankResponse = new TotalTankResponse();

		if(totalTankDetails != null)
		{
			totalTankResponse.setList(tankDetailsList);
			totalTankResponse.setStatus(true);
			totalTankResponse.setMessage("Success");
			//return Response.status(Response.Status.OK).entity(totalTankResponse).build();
		}
		else
		{
			totalTankResponse.setStatus(false);
			totalTankResponse.setMessage("No Content");
		}
		return Response.status(Response.Status.OK).entity(totalTankResponse).build();
		//return Response.status(Response.Status.NO_CONTENT).entity(totalTankResponse).build();
	}
}