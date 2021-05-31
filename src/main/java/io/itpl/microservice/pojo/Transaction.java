package io.itpl.microservice.pojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;
import io.itpl.microservice.example.model.ShoppingCart;
import io.itpl.microservice.exceptions.ApiException;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

public class Transaction {

	@Transient public static final String UNPAID = "Pending";
    @Transient public static final String PAID = "Paid";
    @Transient public static final String IN_PROGRESS = "In-Progress";
    @Transient public static final String CONFIRMED = "Confirmed";
    @Transient public static final String CANCELLED = "Cancelled";
    @Transient public static final String RETURNED = "Returned";
    @Transient public static final String FAILED = "Failed";
    @Transient public static final String DENIED = "Denied";
    @Transient public static final String EXPIRED = "Expired";
    @Transient public static final String ERROR = "Error";
    
    
    
    @Id private String id;
    
    @Indexed private String paymentReceiptNumber; // Code that wil be given to customer.
    @Indexed private String transactionId; // Link between Service & PaymentGw
    @Indexed private String referenceId; // Returned from Payment Gateway after success.
    @Indexed private String userId; // End User who owns the Transaction.
    @Indexed private String email;
    @Indexed private String mcc;
    @Indexed private String mobile;
    @Indexed private String firstName;
    @Indexed private String lastName;
    @Indexed private String timeZoneId;
    @Indexed private String localeId;
       
    @Indexed private String domain;
    private String paymentGatewayId; //i.e. MIDTRANCE
    
    @Indexed private String paymentMode; //Unique Identifier of Payment Mode. (i.e. Cash, Credit_Card etc.)
    private String externalRefenceId; // Identifier of Payment Mode in Payment Gateway.
    @Indexed private String paymentModeId;// Internal System ID of Payment Mode.
    @Indexed private String orderNumber;
    private String token;
    private String redirectUrl;
    
    @Indexed private String orderId;
    @Indexed private Date orderCreatedOn;
    
    @Indexed private String orderType; // SVOD, BOOKING
    
   // private List<OrderItem> items;
  //  @Indexed private OrderItem orderItem;
    private ShoppingCart shoppingCart;
    
    @Indexed private double amount;
    @Indexed private String currencyCode;
    @Indexed private String currencyISO;
    
    @Indexed private String status;
    private Date createdOn;
    private Date updatedOn;
    private Date confirmedOn;
    private Date paidOn;
    
    @Indexed private long timestamp;
    private String remark;
    private String remoteErrorMessage;
    private boolean error;
    
    @Indexed private String gatewayStatusCode;
    @Indexed private String gatewayApprovalCode;
    @Transient private double gatewayAuthorisedAmount;
    @Indexed private String gatewayConfirmationCode;//i.e. PaymentCode for Alphamart.
    private Map<String,String> clientInfo;

    private Hashtable<String,Object> transactionInfo;
    
    @Indexed private String clientType;
    @Transient boolean redirect;
    @Transient private String signatureKey;
    @Transient private String remoteOrderAmount;
    @Indexed private String orderFulfilmentId;
    
    private long expiryTimestamp;
    private String expiresOnValue;
    private String createdOnValue;
    private String confirmedOnValue;
    private String paidOnValue;
    private int transactionValidityInHours;
    
    @Indexed private String paymentModeAlias;
    private String bankName;
    private String bankReference;
    @Transient private JsonNode gatewayResponse;
    @Indexed private String paymentGatewayCode;
    
    public void validate() {
    	
    	if(Strings.isNullOrEmpty(this.userId)) {
    		throw new ApiException("Invalid Transaction, Missing userId");
    	}
    	if(Strings.isNullOrEmpty(this.paymentModeId)) {
    		throw new ApiException("Invalid Transaction, Missing PaymentModeId");
    	}
    	if(Strings.isNullOrEmpty(this.orderType)) {
    		throw new ApiException("Invalid Transaction, Missing Order Type");
    	}

    }
    	

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPaymentGatewayId() {
		return paymentGatewayId;
	}
	public void setPaymentGatewayId(String paymentGatewayId) {
		this.paymentGatewayId = paymentGatewayId;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	
	public String getPaymentModeId() {
		return paymentModeId;
	}
	public void setPaymentModeId(String paymentModeId) {
		this.paymentModeId = paymentModeId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCurrencyISO() {
		return currencyISO;
	}
	public void setCurrencyISO(String currencyISO) {
		this.currencyISO = currencyISO;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public Date getConfirmedOn() {
		return confirmedOn;
	}
	public void setConfirmedOn(Date confirmedOn) {
		this.confirmedOn = confirmedOn;
	}
	public Date getPaidOn() {
		return paidOn;
	}
	public void setPaidOn(Date paidOn) {
		this.paidOn = paidOn;
	}
	public String getExternalRefenceId() {
		return externalRefenceId;
	}
	public void setExternalRefenceId(String externalRefenceId) {
		this.externalRefenceId = externalRefenceId;
	}
	public Date getOrderCreatedOn() {
		return orderCreatedOn;
	}
	public void setOrderCreatedOn(Date orderCreatedOn) {
		this.orderCreatedOn = orderCreatedOn;
	}

	public String getPaymentReceiptNumber() {
		return paymentReceiptNumber;
	}

	public void setPaymentReceiptNumber(String paymentReceiptNumber) {
		this.paymentReceiptNumber = paymentReceiptNumber;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemoteErrorMessage() {
		return remoteErrorMessage;
	}

	public void setRemoteErrorMessage(String remoteErrorMessage) {
		this.remoteErrorMessage = remoteErrorMessage;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public boolean isRedirect() {
		return redirect;
	}

	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String toString() {
		return "Transaction [Id=" + id + ", paymentReceiptNumber=" + paymentReceiptNumber + ", transactionId="
				+ transactionId + ", referenceId=" + referenceId + ", userId=" + userId + ", domain=" + domain
				+ ", paymentGatewayId=" + paymentGatewayId + ", paymentMode=" + paymentMode + ", externalRefenceId="
				+ externalRefenceId + ", paymentModeId=" + paymentModeId + ", redirectUrl=" + redirectUrl + ", orderId="
				+ orderId + ", orderCreatedOn=" + orderCreatedOn + ", amount=" + amount + ", status=" + status
				+ ", remark=" + remark + ", remoteErrorMessage=" + remoteErrorMessage + ", redirect=" + redirect + "]";
	}

	public Hashtable<String, Object> getTransactionInfo() {
		return transactionInfo;
	}

	public void setTransactionInfo(Hashtable<String, Object> transactionInfo) {
		this.transactionInfo = transactionInfo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/*public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}*/

	public String getGatewayStatusCode() {
		return gatewayStatusCode;
	}

	public void setGatewayStatusCode(String gatewayStatusCode) {
		this.gatewayStatusCode = gatewayStatusCode;
	}

	public String getGatewayApprovalCode() {
		return gatewayApprovalCode;
	}

	public void setGatewayApprovalCode(String gatewayApprovalCode) {
		this.gatewayApprovalCode = gatewayApprovalCode;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getGatewayConfirmationCode() {
		return gatewayConfirmationCode;
	}


	public void setGatewayConfirmationCode(String gatewayConfirmationCode) {
		this.gatewayConfirmationCode = gatewayConfirmationCode;
	}


	public String getSignatureKey() {
		return signatureKey;
	}


	public void setSignatureKey(String signatureKey) {
		this.signatureKey = signatureKey;
	}


	public String getRemoteOrderAmount() {
		return remoteOrderAmount;
	}


	public void setRemoteOrderAmount(String remoteOrderAmount) {
		this.remoteOrderAmount = remoteOrderAmount;
	}


	public String getClientType() {
		return clientType;
	}


	public void setClientType(String clientType) {
		this.clientType = clientType;
	}


	public String getOrderFulfilmentId() {
		return orderFulfilmentId;
	}


	public void setOrderFulfilmentId(String orderFulfilmentId) {
		this.orderFulfilmentId = orderFulfilmentId;
	}


	public String getOrderNumber() {
		return orderNumber;
	}


	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}


	/*public OrderItem getOrderItem() {
		return orderItem;
	}


	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}*/


	public String getTimeZoneId() {
		return timeZoneId;
	}


	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}


	public String getLocaleId() {
		return localeId;
	}


	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}


	public long getExpiryTimestamp() {
		return expiryTimestamp;
	}


	public void setExpiryTimestamp(long expiryTimestamp) {
		this.expiryTimestamp = expiryTimestamp;
	}


	public int getTransactionValidityInHours() {
		return transactionValidityInHours;
	}


	public void setTransactionValidityInHours(int transactionValidityInHours) {
		this.transactionValidityInHours = transactionValidityInHours;
	}


	public String getExpiresOnValue() {
		return expiresOnValue;
	}


	public void setExpiresOnValue(String expiresOnValue) {
		this.expiresOnValue = expiresOnValue;
	}


	public String getCreatedOnValue() {
		return createdOnValue;
	}


	public void setCreatedOnValue(String createdOnValue) {
		this.createdOnValue = createdOnValue;
	}


	public String getConfirmedOnValue() {
		return confirmedOnValue;
	}


	public void setConfirmedOnValue(String confirmedOnValue) {
		this.confirmedOnValue = confirmedOnValue;
	}


	public String getPaidOnValue() {
		return paidOnValue;
	}


	public void setPaidOnValue(String paidOnValue) {
		this.paidOnValue = paidOnValue;
	}


	public String getPaymentModeAlias() {
		return paymentModeAlias;
	}


	public void setPaymentModeAlias(String paymentModeAlias) {
		this.paymentModeAlias = paymentModeAlias;
	}


	public String getBankName() {
		return bankName;
	}


	public void setBankName(String bankName) {
		this.bankName = bankName;
	}


	public String getBankReference() {
		return bankReference;
	}


	public void setBankReference(String bankReference) {
		this.bankReference = bankReference;
	}

	public Map<String, String> getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(Map<String, String> clientInfo) {
		this.clientInfo = clientInfo;
	}

	public JsonNode getGatewayResponse() {
		return gatewayResponse;
	}

	public void setGatewayResponse(JsonNode gatewayResponse) {
		this.gatewayResponse = gatewayResponse;
	}

	public double getGatewayAuthorisedAmount() {
		return gatewayAuthorisedAmount;
	}

	public void setGatewayAuthorisedAmount(double gatewayAuthorisedAmount) {
		this.gatewayAuthorisedAmount = gatewayAuthorisedAmount;
	}

	public String getPaymentGatewayCode() {
		return paymentGatewayCode;
	}

	public void setPaymentGatewayCode(String paymentGatewayCode) {
		this.paymentGatewayCode = paymentGatewayCode;
	}
}
