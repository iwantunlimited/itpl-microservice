package io.itpl.microservice.api;

import java.util.HashMap;

public class GenericRequestBody extends ApiRequestBody{


	private HashMap<String,String> requestBody;
	private HashMap<String,String> payload;

	public HashMap<String, String> getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(HashMap<String, String> requestBody) {
		this.requestBody = requestBody;
	}

	public HashMap<String, String> getPayload() {
		return payload;
	}

	public void setPayload(HashMap<String, String> payload) {
		this.payload = payload;
	}
}
