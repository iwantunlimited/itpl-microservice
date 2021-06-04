package io.itpl.microservice.system;

import com.google.common.base.Strings;
import io.itpl.microservice.base.Auditable;
import io.itpl.microservice.base.BaseObject;
import io.itpl.microservice.base.ObjectValidator;
import io.itpl.microservice.base.Sequential;
import io.itpl.microservice.exceptions.ApiException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


public class RelayConfig extends BaseObject implements ObjectValidator, Auditable, Sequential {

	private String id;
	private String featureId;
	private String featureName;
	Feature feature;
	private String serviceId;
	private String actionCode;
	private SystemUserMapping userMapping;
	private List<String> relayServices;
	private boolean systemGenerated;
	private int userType;
	
	public void validate() {
		if(Strings.isNullOrEmpty(domain) ) {
			throw new ApiException("Invalid Request, Missing <featureId> or <domain>");
		}
		if(relayServices == null || relayServices.isEmpty()) {
			throw new ApiException("Invalid Request, Can't create configuration without <relayServices>");
		}
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public List<String> getRelayServices() {
		return relayServices;
	}
	public void setRelayServices(List<String> relayServices) {
		this.relayServices = relayServices;
	}

	public String getFeatureId() {
		return featureId;
	}
	public void setFeatureId(String featureId) {
		this.featureId = featureId;
	}
	public String getFeatureName() {
		return featureName;
	}
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}


	public SystemUserMapping getUserMapping() {
		return userMapping;
	}

	public void setUserMapping(SystemUserMapping userMapping) {
		this.userMapping = userMapping;
	}

	public boolean isSystemGenerated() {
		return systemGenerated;
	}

	public void setSystemGenerated(boolean systemGenerated) {
		this.systemGenerated = systemGenerated;
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}


}
