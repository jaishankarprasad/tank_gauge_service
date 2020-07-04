package com.rainiersoft.tankgauge.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rainiersoft.tankgauge.dao.impl.DeviceDetailsDAOImpl;
import com.rainiersoft.tankgauge.entity.DeviceDetails;
import com.rainiersoft.tankgauge.response.DeviceDetailsResponse;

@Path("/rest")
@Component
public class DeviceDetailsResources {

	private static final Logger LOG = LoggerFactory.getLogger(DeviceDetailsResources.class);

	@Autowired
	DeviceDetailsDAOImpl deviceInfoDAOImpl;

	@Path("/getDevicedetails")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})

	public Response getDevicedetailsbyId(@QueryParam("id") int id)
	{
		System.out.println("Entered into getDevicedetailsbyId resource class method........");
		LOG.info("Entered into getDevicedetailsbyId resource class method........");

		DeviceDetails deviceDetails =new DeviceDetails();
		deviceDetails = deviceInfoDAOImpl.getDeviceDetailsbyId(id);
		if(deviceDetails != null)
		{
			return Response.status(Response.Status.OK).entity(deviceDetails).build();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}


	@Path("/insertDevicedetails")
	@POST
	@Consumes({"application/json"})
	@Produces({"application/json"})

	public DeviceDetailsResponse insertDevicedetails(DeviceDetails deviceInfo)
	{
		System.out.println("Entered into insertDevicedetails resource class method........");
		LOG.info("Entered into insertDevicedetails resource class method........");

		boolean res = deviceInfoDAOImpl.insertDeviceDetails(deviceInfo);
		DeviceDetailsResponse ddr = new DeviceDetailsResponse();

		if(res == true)
		{
			ddr.setStatus(true);
			ddr.setMessage("Device inserted Successfully");
		}
		else
		{
			ddr.setStatus(false);
			ddr.setMessage("Device insertion failed"); 
		}
		return ddr;
	}


	@Path("/deleteDevice")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})

	public DeviceDetailsResponse deleteDevicebyId(@QueryParam("id") int id)
	{
		System.out.println("Entered into deleteDevicebyId resource class method........");
		LOG.info("Entered into deleteDevicebyId resource class method........");

		boolean res = deviceInfoDAOImpl.deleteDeviceDetailsbyId(id);
		DeviceDetailsResponse ddr = new DeviceDetailsResponse();

		if(res == true)
		{
			ddr.setStatus(true);
			ddr.setMessage("Device deleted Successfully");
		}
		else
		{
			ddr.setStatus(false);
			ddr.setMessage("Device deletion failed"); 
		}
		return ddr;
	}

	@Path("/updateDevice")
	@POST
	@Consumes({"application/json"})
	@Produces({"application/json"})

	public DeviceDetailsResponse updateDevice(DeviceDetails deviceInfo)
	{
		LOG.info("Entered into updateDevice resource class method........");

		boolean res = deviceInfoDAOImpl.updateDeviceDetails(deviceInfo);
		DeviceDetailsResponse ddr = new DeviceDetailsResponse();

		if(res == true)
		{
			ddr.setStatus(true);
			ddr.setMessage("Device updated Successfully");
		}
		else
		{
			ddr.setStatus(false);
			ddr.setMessage("Device updation failed"); 
		}
		return ddr;
	}	

	@Path("/getAllDevices")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})	
	public Response getAllDevices()	
	{
		LOG.info("Entered into getAllDevices resource class method........");
		List<DeviceDetails> list = deviceInfoDAOImpl.getAllDevices();

		if(list != null)
		{
			return Response.status(Response.Status.OK).entity(list).build();
		}
		return Response.status(Response.Status.NO_CONTENT).entity(list).build();
	}
}
