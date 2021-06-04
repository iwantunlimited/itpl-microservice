package io.itpl.microservice.system;


import com.google.common.base.Strings;
import io.itpl.microservice.base.Auditable;
import io.itpl.microservice.base.BaseObject;
import io.itpl.microservice.base.Sequential;
import io.itpl.microservice.exceptions.ApiException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;



public class Feature extends BaseObject implements Auditable, Sequential {

	private String id;
	private String name;
	private String actionCode;
	private String description;
	private String serviceId;
	private String objectId;
	private boolean systemGenerated;

	
	public void validate() {
		if(Strings.isNullOrEmpty(name)) {
			throw new ApiException("Can't Register feature without <name>");
		}
		if(Strings.isNullOrEmpty(actionCode)) {
			throw new ApiException("Can't Register feature without <actionCode>");
		}
		if(Strings.isNullOrEmpty(serviceId)) {
			throw new ApiException("Can't Register feature without <serviceId>");
		}
		if(Strings.isNullOrEmpty(objectId)) {
			throw new ApiException("Can't Register feature without <objectId>");
		}

	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public boolean isSystemGenerated() {
		return systemGenerated;
	}

	public void setSystemGenerated(boolean systemGenerated) {
		this.systemGenerated = systemGenerated;
	}
}
