package io.itpl.microservice.api;



public class ResourceBody {

	private String actionCode;
	private UserFile requestBody;
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public UserFile getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(UserFile requestBody) {
		this.requestBody = requestBody;
	}
	
}
