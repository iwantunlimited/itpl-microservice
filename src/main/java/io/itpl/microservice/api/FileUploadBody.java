package io.itpl.microservice.api;


public class FileUploadBody extends ApiRequestBody {


	private ResourceFilter imageFilter;
	private UserFile requestBody;
	private UserFile payload;
	private String remarks;

	public UserFile getPayload() {
		return payload;
	}

	public void setPayload(UserFile payload) {
		this.payload = payload;
	}

	public UserFile getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(UserFile requestBody) {
		this.requestBody = requestBody;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public ResourceFilter getImageFilter() {
		return imageFilter;
	}
	public void setImageFilter(ResourceFilter imageFilter) {
		this.imageFilter = imageFilter;
	}
	
	
	

}
