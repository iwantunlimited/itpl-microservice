package io.itpl.microservice.api;

import java.util.HashMap;

public class GenericRequestBody {

	private String actionCode;
	private HashMap<String,String> requestBody;
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public HashMap<String, String> getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(HashMap<String, String> requestBody) {
		this.requestBody = requestBody;
	}
	
}
