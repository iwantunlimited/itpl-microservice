package io.itpl.microservice.api;

public class GetResourceBody {
	private String actionCode;
	private ResourceFilter requestBody;
	private String remarks;
	public GetResourceBody() {
		// TODO Auto-generated constructor stub
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
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
