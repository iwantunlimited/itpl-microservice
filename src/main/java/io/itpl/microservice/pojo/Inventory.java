package io.itpl.microservice.pojo;

public class Inventory {
	
	private int totalQuantity;
	private int blockedQuantity;
	private int reservedQuantity;
	
	public int getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public int getBlockedQuantity() {
		return blockedQuantity;
	}
	public void setBlockedQuantity(int blockedQuantity) {
		this.blockedQuantity = blockedQuantity;
	}
	public int getReservedQuantity() {
		return reservedQuantity;
	}
	public void setReservedQuantity(int reservedQuantity) {
		this.reservedQuantity = reservedQuantity;
	}
	@Override
	public String toString() {
		return "Inventory [totalQuantity=" + totalQuantity + ", blockedQuantity=" + blockedQuantity
				+ ", reservedQuantity=" + reservedQuantity + "]";
	}	
	
	
	
}
