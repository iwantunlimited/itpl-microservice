package io.itpl.microservice.api;

public class GetResourceBody extends ApiRequestBody{

	private ResourceFilter requestBody;
	private ResourceFilter payload;
	private String remarks;


	public ResourceFilter getPayload() {
		return payload;
	}

	public void setPayload(ResourceFilter payload) {
		this.payload = payload;
	}

	public ResourceFilter getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(ResourceFilter requestBody) {
		this.requestBody = requestBody;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
