package io.itpl.microservice.api;



public class ResourceBody extends ApiRequestBody{


	private UserFile requestBody;
	private UserFile payload;

	public UserFile getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(UserFile requestBody) {
		this.requestBody = requestBody;
	}

	public UserFile getPayload() {
		return payload;
	}

	public void setPayload(UserFile payload) {
		this.payload = payload;
	}
}
