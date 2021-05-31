package io.itpl.microservice.pojo;

import com.google.common.base.Strings;
import io.itpl.microservice.base.ObjectValidator;
import io.itpl.microservice.exceptions.InvalidInputException;

public class StockUpdate implements ObjectValidator {

    private String domain;
    private String sku;
    private int quantity;
    private String label;
    private boolean replace;
    private String transactionType;
    private String transactionId;
    private String transactionNumber;
    private String remark;

    private String creditTransactionId;
    private String debitTransactionId;

    private double costPrice;
    private double landingPrice;
    private double sellingPrice;

    @Override
    public void validate(){
        if(Strings.isNullOrEmpty(domain)){
            throw new InvalidInputException("Can't process Stock Update without domain");
        }
        if(Strings.isNullOrEmpty(sku)){
            throw new InvalidInputException("Can't process Stock Update without sku");
        }
        if(Strings.isNullOrEmpty(label)){
            throw new InvalidInputException("Can't process Stock Update without label");
        }
        if(quantity <=0){
            throw new InvalidInputException("Can't process Stock Update with quantity:"+ quantity);
        }
    }
    public static StockUpdate of(String domain,String sku,int quantity,String label,boolean replace){
        StockUpdate stock = new StockUpdate();
        stock.setDomain(domain);
        stock.setSku(sku);
        stock.setQuantity(quantity);
        stock.setLabel(label);
        stock.setReplace(replace);
        return stock;
    }
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isReplace() {
        return replace;
    }

    public void setReplace(boolean replace) {
        this.replace = replace;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreditTransactionId() {
        return creditTransactionId;
    }

    public void setCreditTransactionId(String creditTransactionId) {
        this.creditTransactionId = creditTransactionId;
    }

    public String getDebitTransactionId() {
        return debitTransactionId;
    }

    public void setDebitTransactionId(String debitTransactionId) {
        this.debitTransactionId = debitTransactionId;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
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
}
