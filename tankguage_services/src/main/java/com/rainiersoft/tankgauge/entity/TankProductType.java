package com.rainiersoft.tankgauge.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TK_PRODUCTTYPE")
public class TankProductType 
{
	@Id
	@Column(name="ProductType_id")
	int productTypeId;
	
	@Column(name="Product_Name")
	String productName;
	
	@Column(name="Product_Measure")
	String productMeasure;

	public int getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(int productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductMeasure() {
		return productMeasure;
	}

	public void setProductMeasure(String productMeasure) {
		this.productMeasure = productMeasure;
	}

}
