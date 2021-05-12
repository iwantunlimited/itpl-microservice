package io.itpl.microservice.api;

import java.util.Date;

public class ApiResponse {
	
	private int statusCode;
	private String message;
	private Object data;
	private Date timestamp;
	private int responseTime;
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
		if(timestamp != null) {
			int responseTime = (int)(new Date().getTime() - timestamp.getTime());
			this.responseTime = responseTime;
		}
	}
	public int getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(int responseTime) {
		this.responseTime = responseTime;
	}
	public static ApiResponse error(String message) {
		ApiResponse response = new ApiResponse();
		response.setStatusCode(-101);
		response.setMessage(message);
		return response;
	}
	public static ApiResponse error(String msg,int code){
		ApiResponse response = new ApiResponse();
		response.setStatusCode(code);
		response.setMessage(msg);
		return response;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	

}
