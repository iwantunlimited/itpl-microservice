package io.itpl.microservice.system;

import com.fasterxml.jackson.databind.JsonNode;
import io.itpl.microservice.ActionRequest;


public class BroadcastMessageBody {

	private String domain;
	private ActionRequest actionRequest;
	private JsonNode requestObject;
	private JsonNode responseObject;
	private String pathVariable;
	private String extraInfo;
	
	
	public ActionRequest getActionRequest() {
		return actionRequest;
	}
	public void setActionRequest(ActionRequest actionRequest) {
		this.actionRequest = actionRequest;
	}
	public JsonNode getRequestObject() {
		return requestObject;
	}
	public void setRequestObject(JsonNode requestObject) {
		this.requestObject = requestObject;
	}
	public JsonNode getResponseObject() {
		return responseObject;
	}
	public void setResponseObject(JsonNode responseObject) {
		this.responseObject = responseObject;
	}
	public String getPathVariable() {
		return pathVariable;
	}
	public void setPathVariable(String pathVariable) {
		this.pathVariable = pathVariable;
	}
	public String getExtraInfo() {
		return extraInfo;
	}
	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	
	
	
}
