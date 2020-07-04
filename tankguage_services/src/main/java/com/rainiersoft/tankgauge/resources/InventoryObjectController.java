package com.rainiersoft.tankgauge.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.rainiersoft.tankgauge.dao.InventoryObjectDAO;
import com.rainiersoft.tankgauge.pojo.InventoryObjectRequest;
import com.rainiersoft.tankgauge.response.InventoryReportResponse;

@Path("/rest")
public class InventoryObjectController
{

	@Autowired
	InventoryObjectDAO inventoryObjectDAO;
	
	@Path("/getInventoryResult")
	@POST
	@Consumes({"application/json"})
	@Produces({"application/json"})
	
	public List<InventoryReportResponse> getInventoryResult(InventoryObjectRequest inventoryObjectRequest)
	{
		List<InventoryReportResponse> inventoryObjectList = inventoryObjectDAO.getInventoryObject(inventoryObjectRequest);
		
		return inventoryObjectList;
	}
	
	
	
/*	@Path("/getMDList")
	@POST
	@Consumes({"application/json"})
	@Produces({"application/json"})
	
	public List<TankMetaData> getMDList(InventoryObjectRequest inventoryObjectRequest)
	{
		List<InventoryObject> inventoryObjectList = inventoryObjectDAO.getTankMDList(inventoryObjectRequest);
		
		return inventoryObjectList;
	}*/
}
