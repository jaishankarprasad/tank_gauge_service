package com.rainiersoft.tankgauge.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.rainiersoft.tankgauge.dao.TankTrendDAO;
import com.rainiersoft.tankgauge.pojo.TabularInputs;
import com.rainiersoft.tankgauge.pojo.PropertyHistoryInfo;
import com.rainiersoft.tankgauge.pojo.PropertyValue;
import com.rainiersoft.tankgauge.pojo.TankPropertyInfo;
import com.rainiersoft.tankgauge.pojo.TankTrendingInputs;
import com.rainiersoft.tankgauge.response.TankR;
import com.rainiersoft.tankgauge.response.TankTrendResponse;

@Path("/rest")
public class TankTrendResources {

	@Autowired
	TankTrendDAO tankTrendDAO;
	
	@Path("/getTrendValuesbyDay")
	@POST
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Response getTrendValuesbyDay(TankTrendingInputs tankTrendingInputs)
	{				
		List<ArrayList<PropertyValue>> valuesList = null;

		List<TankPropertyInfo> tankPropsList = null;

		valuesList = tankTrendDAO.getTrendValues(tankTrendingInputs);

		tankPropsList = tankTrendDAO.getPropertyValuesbyId(tankTrendingInputs);

		TankTrendResponse tankTrendResponse = new TankTrendResponse();
		if(valuesList != null && tankPropsList != null)
		{
			tankTrendResponse.setMessage("Success");
			tankTrendResponse.setStatus(true);
			tankTrendResponse.setPropDataList(valuesList);
			tankTrendResponse.setTankPropsList(tankPropsList);
		}
		else
		{
			tankTrendResponse.setMessage("Failure");
			tankTrendResponse.setStatus(false);
		}
		return Response.status(Response.Status.OK).entity(tankTrendResponse).build();
	}


	@Path("/getTrendValuesbyHour")
	@POST
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Response getTrendValuesbyHour(TankTrendingInputs tankTrendingInputs)
	{				
		List<ArrayList<PropertyValue>> valuesList = null;

		List<TankPropertyInfo> tankPropsList = null;

		valuesList = tankTrendDAO.getTrendValuesbyHour(tankTrendingInputs);

		tankPropsList = tankTrendDAO.getPropertyValuesbyId(tankTrendingInputs);

		TankTrendResponse tankTrendResponse = new TankTrendResponse();
		if(valuesList != null && tankPropsList != null)
		{
			tankTrendResponse.setMessage("Success");
			tankTrendResponse.setStatus(true);
			tankTrendResponse.setPropDataList(valuesList);
			tankTrendResponse.setTankPropsList(tankPropsList);
		}
		else
		{
			tankTrendResponse.setMessage("Failure");
			tankTrendResponse.setStatus(false);
		}
		return Response.status(Response.Status.OK).entity(tankTrendResponse).build();
	}
	@Path("/getPropertyValuesbyId")
	@POST
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Response getPropertyValuesbyId(TankTrendingInputs tankTrendingInputs)
	{				
		List<TankPropertyInfo> tankPropsList = null;

		tankPropsList = tankTrendDAO.getPropertyValuesbyId(tankTrendingInputs);

		TankTrendResponse tankTrendResponse = new TankTrendResponse();
		if(tankPropsList != null)
		{
			tankTrendResponse.setMessage("Success");
			tankTrendResponse.setStatus(true);
			tankTrendResponse.setTankPropsList(tankPropsList);
		}
		else
		{
			tankTrendResponse.setMessage("Failure");
			tankTrendResponse.setStatus(false);
		}
		return Response.status(Response.Status.OK).entity(tankTrendResponse).build();
	}

	@Path("/getTrendValuesInMinutes")
	@POST
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Response getTrendValuesInMinutes(TabularInputs tabularInputs)
	{				
		List<PropertyHistoryInfo> valuesList = null;

		valuesList = tankTrendDAO.getTableValuesListForMinutes(tabularInputs);


		TankR tankR = new TankR();
		if(valuesList != null)
		{
			tankR.setMessage("Success");
			tankR.setStatus(true);
			tankR.setPropDataList(valuesList);
		}
		else
		{
			tankR.setMessage("Failure");
			tankR.setStatus(false);
		}
		return Response.status(Response.Status.OK).entity(tankR).build();
	}

}
