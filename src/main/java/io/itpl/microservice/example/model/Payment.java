package io.itpl.microservice.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("payment-detail")
public class Payment {

    @Id private String id;
    /**
     *  i.e. PaymentMode Id
     */
    @Indexed private String paymentModeId;
    /**
     *  i.e. Gift Voucher
     */
    @Indexed private String paymentMode;
    /**
     *  Total Amount paid by payment mode.
     */
    private double amount;
    /**
     *  i.e. In-Progress, Confirmed or Paid
     */
    @Indexed private String status;
    /**
     *  Unique Payment Transaction Number
     */
    @Indexed private String transactionNumber;
    /**
     *  Receipt Number generated by the Payment Gateway
     */
    private String receiptNumber;
    /**
     *  Additional Payment Gateway Specific information
     */
    private String extra1;
    private String extra2;
    private String extra3;
    /**
     *  Success or error message that is received from the payment gateway.
     */
    private String message;
    private Date initiatedOn;
    /**
     *  I.e. Validity period of completion of transaction after
     *  confirmation from payment gateway.
     *
     *  For example, Customer selected ATM or Bank Transfer etc. Payment wherein
     *  customer needs to finish the payment in certain duration.
     */
    private int validityInHours;
    /**
     *  Expiry Date of payment transaction.
     */
    @Indexed private Date expiresOn;
    /**
     *  Date of payment completion.
     */
    private Date paidOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentModeId() {
        return paymentModeId;
    }

    public void setPaymentModeId(String paymentModeId) {
        this.paymentModeId = paymentModeId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getExtra1() {
        return extra1;
    }

    public void setExtra1(String extra1) {
        this.extra1 = extra1;
    }

    public String getExtra2() {
        return extra2;
    }

    public void setExtra2(String extra2) {
        this.extra2 = extra2;
    }

    public String getExtra3() {
        return extra3;
    }

    public void setExtra3(String extra3) {
        this.extra3 = extra3;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getInitiatedOn() {
        return initiatedOn;
    }

    public void setInitiatedOn(Date initiatedOn) {
        this.initiatedOn = initiatedOn;
    }

    public int getValidityInHours() {
        return validityInHours;
    }

    public void setValidityInHours(int validityInHours) {
        this.validityInHours = validityInHours;
    }

    public Date getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(Date expiresOn) {
        this.expiresOn = expiresOn;
    }

    public Date getPaidOn() {
        return paidOn;
    }

    public void setPaidOn(Date paidOn) {
        this.paidOn = paidOn;
    }
}
