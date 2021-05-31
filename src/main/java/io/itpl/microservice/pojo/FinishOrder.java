package io.itpl.microservice.pojo;

public class FinishOrder {
    private String subscriptionId;
    private String orderId;

    public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    @Override
    public String toString() {
        return "FinishOrder{" +
                "subscriptionId='" + subscriptionId + '\'' +
                '}';
    }
}
