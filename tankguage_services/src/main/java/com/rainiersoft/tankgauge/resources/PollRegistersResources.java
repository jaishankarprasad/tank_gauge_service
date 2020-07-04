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

import com.rainiersoft.tankgauge.dao.PollRegistersDAO;
import com.rainiersoft.tankgauge.entity.PollRegisters;
import com.rainiersoft.tankgauge.response.PollRegistersResponse;

@Path("/rest")
public class PollRegistersResources 
{
	private static final Logger LOG = LoggerFactory.getLogger(PollRegistersResources.class);

	@Autowired
	PollRegistersDAO pollRegistersDAO;
	
	@Path("/getAllPollRegisters")
	@GET
	@Consumes({"application/json"})
    @Produces({"application/json"})
	public Response getAllPollRegisters()
	{
		LOG.info("In getAllPollRegisters Recources Class");	
		
		List<PollRegisters> pollRegistersList = pollRegistersDAO.getAllPollRegisters();
		
		PollRegistersResponse pollRegistersResponse = new PollRegistersResponse();
		
		if(pollRegistersList != null)
		{
			pollRegistersResponse.setStatus(true);
			pollRegistersResponse.setMessage("Success");
			pollRegistersResponse.setList(pollRegistersList);		
		}
		else
		{
			pollRegistersResponse.setStatus(false);
			pollRegistersResponse.setMessage("Failure");		
		}

		return Response.status(Response.Status.OK).entity(pollRegistersResponse).build();
//	    return Response.status(Response.Status.NO_CONTENT).entity(pollRegistersResponse).build();
	}
	
	@Path("getPollRegistersbyId")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Response getPollRegistersbyId(@QueryParam("id") int propertyId)
	{
		LOG.info("In getPollRegistersbyId Recources Class");	
	
		PollRegisters pollRegisters = new PollRegisters();
		pollRegisters =pollRegistersDAO.getPollRegistersbyId(propertyId);
		
		List<PollRegisters> pollRegistersList = Arrays.asList(pollRegisters);
		
		PollRegistersResponse pollRegistersResponse = new PollRegistersResponse();
		
		if(pollRegistersList != null)
		{
			pollRegistersResponse.setStatus(true);
			pollRegistersResponse.setMessage("Success");
			pollRegistersResponse.setList(pollRegistersList);
		
		}
		else
		{
			pollRegistersResponse.setStatus(false);
			pollRegistersResponse.setMessage("Failure");
		}

		return Response.status(Response.Status.OK).entity(pollRegistersResponse).build();
//	    return Response.status(Response.Status.NO_CONTENT).entity(pollRegistersResponse).build();
	
	}
	
}
