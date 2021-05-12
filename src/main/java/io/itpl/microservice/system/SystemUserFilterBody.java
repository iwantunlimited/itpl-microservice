package io.itpl.microservice.system;

import io.itpl.microservice.api.ApiRequestBody;

public class SystemUserFilterBody extends ApiRequestBody {


	private SystemUserFilter requestBody;

	public SystemUserFilter getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(SystemUserFilter requestBody) {
		this.requestBody = requestBody;
	}
	
	
	
}
