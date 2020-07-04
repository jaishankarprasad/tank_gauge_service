package com.rainiersoft.tankgauge.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rainiersoft.tankgauge.dao.ReportManagementDAO;
import com.rainiersoft.tankgauge.pojo.ReportInputs;
import com.rainiersoft.tankgauge.response.ReportResponse;


@Path("/rest")
public class ReportManagementResources 
{
	private static final Logger LOG = LoggerFactory.getLogger(ReportManagementResources.class);

	@Autowired
	ReportManagementDAO reportManagementDAO;

	@Path("/getReports")
	@POST
	@Consumes({"application/json"})
	@Produces({"application/json"})	
	public Response getReports(ReportInputs reportInputs)	
	{
		LOG.info("Entered into getReports resource class method....");

		ReportResponse reportResponse = new ReportResponse();	
		
		reportResponse.setPath("C:\\Users\\Madhu\\rpt_tank_inventory.pdf");
		reportResponse.setMessage("Success");
		reportResponse.setStatus(true);
		return Response.status(Response.Status.OK).entity(reportResponse).build();
	}

}
