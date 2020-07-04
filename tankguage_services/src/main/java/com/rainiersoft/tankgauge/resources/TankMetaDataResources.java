package com.rainiersoft.tankgauge.resources;

import java.util.Arrays;
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

import com.rainiersoft.tankgauge.dao.TankMetaDataDAO;
import com.rainiersoft.tankgauge.entity.TankMetaData;
import com.rainiersoft.tankgauge.response.TankMetaDataResponse;


@Path("/rest")
public class TankMetaDataResources 
{
	private static final Logger LOG = LoggerFactory.getLogger(TankMetaDataResources.class);

	@Autowired
	TankMetaDataDAO tankMetaDataDAO;

	//Configuration of TANKS
	@Path("/insertTankMetaDataAndProperty")
	@POST
	@Consumes({"application/json"})
	@Produces({"application/json"})	
	public TankMetaDataResponse insertTankMetaDataAndProperty(TankMetaData tankMetaData)
	{
		LOG.info("Entered into insertTankMetaData Method");

		TankMetaDataResponse tr = null;

		boolean tankExists = tankMetaDataDAO.tankExistCheck(tankMetaData);

		if(tankExists != true)
		{
			boolean res = tankMetaDataDAO.insertTankMetaDataAndProperty(tankMetaData);

			tr = new TankMetaDataResponse();

			if(res == true)
			{		
				tr.setStatus(true);
				tr.setMessage("Insertion of TankMetaData Succeeded");
			}
			else
			{			
				tr.setStatus(false);
				tr.setMessage("Insertion of TankMetaData Failed");
			}

		}
		else
		{
			tr = new TankMetaDataResponse();
			tr.setMessage("Tank with Name: "+tankMetaData.getName()+" ,Already Exists..,Try With Update to Change Tank Information!");
		}
		return tr;

	}

	@Path("/updateTankMetaDataAndProperty")
	@POST
	@Consumes({"application/json"})
	@Produces({"application/json"})	
	public TankMetaDataResponse updateTankMetaDataAndProperty(TankMetaData tankMetaData)
	{
		LOG.info("Entered into updateTankMetaDataAndProperty Method");

		TankMetaDataResponse tr = null;

		{
			boolean res = tankMetaDataDAO.updateTankMetaDataAndProperty(tankMetaData);

			tr = new TankMetaDataResponse();

			if(res == true)
			{		
				tr.setStatus(true);
				tr.setMessage("Updation of TankMetaData Succeeded");
			}
			else
			{			
				tr.setStatus(false);
				tr.setMessage("Updation of TankMetaData Failed");
			}

		}

		return tr;

	}


	//Retrieval of Tanks
	@Path("/getAllTankMetaData")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})	
	public Response getAllTankMetaData()	
	{
		LOG.info("Entered into getAllTankMetaData resource class method........");

		List<TankMetaData> list = tankMetaDataDAO.getAllTankMetaData();
		TankMetaDataResponse tankMetaDataResponse = new TankMetaDataResponse();		

		if(list != null)
		{
			tankMetaDataResponse.setList(list);
			tankMetaDataResponse.setStatus(true);
			tankMetaDataResponse.setMessage("Success");
		}
		else
		{
			tankMetaDataResponse.setStatus(false);
			tankMetaDataResponse.setMessage("No Content");
		}

		return Response.status(Response.Status.OK).entity(tankMetaDataResponse).build();
	}

	@SuppressWarnings("unchecked")
	@Path("/getTankMetaDatabyId")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})

	public Response getTankMetaDatabyId(@QueryParam("id") int tankId)
	{
		LOG.info("Entered into getTankMetaDatabyId resource class method........");

		TankMetaData tankMetaData = tankMetaDataDAO.getTankMetaDatabyId(tankId);

		@SuppressWarnings("rawtypes")
		List tankMetaDataList = Arrays.asList(tankMetaData);


		TankMetaDataResponse tankMetaDataResponse = new TankMetaDataResponse();

		if(tankMetaData != null)
		{
			tankMetaDataResponse.setList(tankMetaDataList);
			tankMetaDataResponse.setStatus(true);
			tankMetaDataResponse.setMessage("Success");

		}
		else
		{
			tankMetaDataResponse.setStatus(false);
			tankMetaDataResponse.setMessage("No Content");
		}

		return Response.status(Response.Status.OK).entity(tankMetaDataResponse).build();
	}


	/*	@Path("/updateTankMetaData")
	@POST
	@Consumes({"application/json"})
	@Produces({"application/json"})

	public Response updateTankMetaData(TankMetaData tankMetaData)
	{
		System.out.println("Entered into updateTankMetaData resource class method........");
		LOG.info("Entered into updateTankMetaData resource class method........");

		boolean res = tankMetaDataDAO.updateTankMetaData(tankMetaData);
		TankMetaDataResponse tmdr = new TankMetaDataResponse();

		if(res == true)
		{
			tmdr.setStatus(true);
			tmdr.setMessage("TankMetaData updated Successfully");
		}
		else
		{
			tmdr.setStatus(false);
			tmdr.setMessage("TankMetaData updation failed"); 
		}
		return Response.status(Response.Status.OK).entity(tmdr).build();
	}	


	@Path("/updateTankProperty")
	@POST
	@Consumes({"application/json"})
	@Produces({"application/json"})

	public Response updateTankProperty(TankProperty tankProperty)
	{
		System.out.println("Entered into updateTankProperty resource class method........");
		LOG.info("Entered into updateTankProperty resource class method........");

		boolean res = tankMetaDataDAO.updateTankProperty(tankProperty);
		TankMetaDataResponse tmdr = new TankMetaDataResponse();

		if(res == true)
		{
			tmdr.setStatus(true);
			tmdr.setMessage("TankProperty updated Successfully");
		}
		else
		{
			tmdr.setStatus(false);
			tmdr.setMessage("TankProperty updation failed"); 
		}
		return Response.status(Response.Status.OK).entity(tmdr).build();
	}
	 */	
	/*	
	@Path("/deleteTankProperty")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Response deleteTankProperty(@QueryParam("id")int propertyId)
	{
		System.out.println("Entered into deleteTankProperty resource class method........");
		LOG.info("Entered into deleteTankProperty resource class method........");

		boolean res = tankMetaDataDAO.deleteTankProperty(propertyId);
		TankMetaDataResponse tmdr = new TankMetaDataResponse();

		if(res == true)
		{
			tmdr.setStatus(true);
			tmdr.setMessage("TankProperty deleted Successfully");
		}
		else
		{
			tmdr.setStatus(false);
			tmdr.setMessage("TankProperty deletion failed"); 
		}
		return Response.status(Response.Status.OK).entity(tmdr).build();
	}

	@Path("/deleteTankMetaData")
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Response deleteTankMetaData(@QueryParam("id")int tankId)
	{
		System.out.println("Entered into deleteTankMetaData resource class method........");
		LOG.info("Entered into deleteTankMetaData resource class method........");

		boolean res = tankMetaDataDAO.deleteTankMetaData(tankId);
		TankMetaDataResponse tmdr = new TankMetaDataResponse();

		if(res == true)
		{
			tmdr.setStatus(true);
			tmdr.setMessage("TankMetaData deleted Successfully");
		}
		else
		{
			tmdr.setStatus(false);
			tmdr.setMessage("TankMetaData deletion failed"); 
		}
		return Response.status(Response.Status.OK).entity(tmdr).build();
	}
	 */}


