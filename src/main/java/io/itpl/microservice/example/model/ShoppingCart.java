package io.itpl.microservice.example.model;

import io.itpl.microservice.base.Auditable;
import io.itpl.microservice.base.BaseObject;
import io.itpl.microservice.base.ObjectValidator;
import io.itpl.microservice.base.Sequential;
import io.itpl.microservice.common.UserObject;
import io.itpl.microservice.pojo.Address;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("shopping-cart")
public class ShoppingCart extends BaseObject implements Auditable, Sequential, ObjectValidator {

	public static final String ORDER_STATUS_NEW = "new";
	public static final String ORDER_STATUS_INPROGRESS = "In-Progress";
	public static final String ORDER_STATUS_READYTOSHIP = "readytoship";
	public static final String ORDER_STATUS_SHIPPED = "shipped";
	public static final String ORDER_STATUS_DELIVERED = "delivered";
		
    @Id private String id;
    @Indexed private String customerId;
    private UserObject customer;
    @Indexed private String customerMobile;
    @Indexed private String customerName;
    @Indexed private String customerEmail;
    /**
     *  ID of the Customer Location that is selected/detected
     */
    private String locationId;
    private String locationName;

    private int totalItems;


    private int deliveredItems;
    private double discountAmount;
    private double grossAmount;
    private double taxAmount;
    private double deliveryCharge;
    private double deliveryChargeTaxAmount;
    private double netAmount;
    private Address shippingAddress;
    /**
     *  We will update this flag to true when customer checkout the order.
     *
     */
    private boolean orderPlaced;
    /**
     *  Unique order Number will be generated when customer check out the shopping cart.
     */
    @Indexed private String orderNumber;
    @Indexed private Date orderDate;
    private String orderDateValue;

    @Indexed private String deliveryModeId;
    private String deliveryModeName;

    private double paidAmount;
    @DBRef private List<Payment> paymentDetail;
    private boolean paid;
    private boolean delivered;
    
	private String orderStatus = ORDER_STATUS_NEW;
	@Indexed private String createdOnDate;
    @Indexed private String orderedOnDate;
    @Indexed private String paidOnDate;
    @Indexed private String createdOnMonth;
    @Indexed private String orderedOnMonth;
    @Indexed private String paidOnMonth;

    @Override
    public void validate() {

    }

	public String getOrderStatus() {
		return orderStatus;
	}
	
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public UserObject getCustomer() {
        return customer;
    }

    public void setCustomer(UserObject customer) {
        this.customer = customer;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }


    public int getDeliveredItems() {
        return deliveredItems;
    }

    public void setDeliveredItems(int deliveredItems) {
        this.deliveredItems = deliveredItems;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public double getDeliveryChargeTaxAmount() {
        return deliveryChargeTaxAmount;
    }

    public void setDeliveryChargeTaxAmount(double deliveryChargeTaxAmount) {
        this.deliveryChargeTaxAmount = deliveryChargeTaxAmount;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public boolean isOrderPlaced() {
        return orderPlaced;
    }

    public void setOrderPlaced(boolean orderPlaced) {
        this.orderPlaced = orderPlaced;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDateValue() {
        return orderDateValue;
    }

    public void setOrderDateValue(String orderDateValue) {
        this.orderDateValue = orderDateValue;
    }

    public String getDeliveryModeId() {
        return deliveryModeId;
    }

    public void setDeliveryModeId(String deliveryModeId) {
        this.deliveryModeId = deliveryModeId;
    }

    public String getDeliveryModeName() {
        return deliveryModeName;
    }

    public void setDeliveryModeName(String deliveryModeName) {
        this.deliveryModeName = deliveryModeName;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public List<Payment> getPaymentDetail() {
        return paymentDetail;
    }

    public void setPaymentDetail(List<Payment> paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public String getCreatedOnDate() {
        return createdOnDate;
    }

    public void setCreatedOnDate(String createdOnDate) {
        this.createdOnDate = createdOnDate;
    }

    public String getOrderedOnDate() {
        return orderedOnDate;
    }

    public void setOrderedOnDate(String orderedOnDate) {
        this.orderedOnDate = orderedOnDate;
    }

    public String getPaidOnDate() {
        return paidOnDate;
    }

    public void setPaidOnDate(String paidOnDate) {
        this.paidOnDate = paidOnDate;
    }

    public String getCreatedOnMonth() {
        return createdOnMonth;
    }

    public void setCreatedOnMonth(String createdOnMonth) {
        this.createdOnMonth = createdOnMonth;
    }

    public String getOrderedOnMonth() {
        return orderedOnMonth;
    }

    public void setOrderedOnMonth(String orderedOnMonth) {
        this.orderedOnMonth = orderedOnMonth;
    }

    public String getPaidOnMonth() {
        return paidOnMonth;
    }

    public void setPaidOnMonth(String paidOnMonth) {
        this.paidOnMonth = paidOnMonth;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}
