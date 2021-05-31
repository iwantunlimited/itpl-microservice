package io.itpl.microservice.pojo;

import java.util.List;

public class SellingPrice {

	private String locationId;
	private String customerCategoryId;
	private double price;
	private boolean applyQtyBased;
	private List<QtyRange> priceList;
	
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getCustomerCategoryId() {
		return customerCategoryId;
	}
	public void setCustomerCategoryId(String customerCategoryId) {
		this.customerCategoryId = customerCategoryId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public boolean isApplyQtyBased() {
		return applyQtyBased;
	}
	public void setApplyQtyBased(boolean applyQtyBased) {
		this.applyQtyBased = applyQtyBased;
	}
	public List<QtyRange> getPriceList() {
		return priceList;
	}
	public void setPriceList(List<QtyRange> priceList) {
		this.priceList = priceList;
	}
	
	
	
}
