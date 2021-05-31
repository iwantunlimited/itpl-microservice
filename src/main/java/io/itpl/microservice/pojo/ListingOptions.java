package io.itpl.microservice.pojo;

import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Map;

public class ListingOptions {
	
	/**
	 * sku = combination of domain ssid + product Variant ssid + seller ssid
	 */
	private String sku;
	private String optionId;
	private ListingProductVariant productVariant;
	/**
	 * Pricing Parameters
	 */
	@Indexed private double mrp;
	@Indexed private double purchasePrice;
	@Indexed private double landingPrice;
	@Indexed private double sellingPrice;
	@Indexed private double discount;
	@Indexed private double netPayablePrice;
	@Indexed private double grossMargine;
	@Indexed private boolean available;
	@Indexed private Map<String,Object> tax;
	private String stockBehaviour;
	/**
	 * Inventory
	 */
	@Indexed private boolean inventory;
	@Indexed private Inventory inventoryDetails;
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getOptionId() {
		return optionId;
	}
	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}
	public ListingProductVariant getProductVariant() {
		return productVariant;
	}
	public void setProductVariant(ListingProductVariant productVariant) {
		this.productVariant = productVariant;
	}
	public double getMrp() {
		return mrp;
	}
	public void setMrp(double mrp) {
		this.mrp = mrp;
	}
	public double getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public double getLandingPrice() {
		return landingPrice;
	}
	public void setLandingPrice(double landingPrice) {
		this.landingPrice = landingPrice;
	}
	public double getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getNetPayablePrice() {
		return netPayablePrice;
	}
	public void setNetPayablePrice(double netPayablePrice) {
		this.netPayablePrice = netPayablePrice;
	}
	public double getGrossMargine() {
		return grossMargine;
	}
	public void setGrossMargine(double grossMargine) {
		this.grossMargine = grossMargine;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public Map<String, Object> getTax() {
		return tax;
	}
	public void setTax(Map<String, Object> tax) {
		this.tax = tax;
	}
	public String getStockBehaviour() {
		return stockBehaviour;
	}
	public void setStockBehaviour(String stockBehaviour) {
		this.stockBehaviour = stockBehaviour;
	}
	public boolean isInventory() {
		return inventory;
	}
	public void setInventory(boolean inventory) {
		this.inventory = inventory;
	}
	public Inventory getInventoryDetails() {
		return inventoryDetails;
	}
	public void setInventoryDetails(Inventory inventoryDetails) {
		this.inventoryDetails = inventoryDetails;
	}
	@Override
	public String toString() {
		return "ListingOptions [sku=" + sku + ", optionId=" + optionId + ", productVariant=" + productVariant + ", mrp="
				+ mrp + ", purchasePrice=" + purchasePrice + ", landingPrice=" + landingPrice + ", sellingPrice="
				+ sellingPrice + ", discount=" + discount + ", netPayablePrice=" + netPayablePrice + ", grossMargine="
				+ grossMargine + ", available=" + available + ", tax=" + tax + ", stockBehaviour=" + stockBehaviour
				+ ", inventory=" + inventory + ", inventoryDetails=" + inventoryDetails + "]";
	}
}
