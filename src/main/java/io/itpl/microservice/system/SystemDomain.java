package io.itpl.microservice.system;

import com.google.common.base.Strings;
import io.itpl.microservice.base.Auditable;
import io.itpl.microservice.base.BaseObject;
import io.itpl.microservice.base.ObjectValidator;
import io.itpl.microservice.base.Sequential;
import io.itpl.microservice.exceptions.ApiException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class SystemDomain extends BaseObject implements ObjectValidator, Auditable, Sequential {
	

	public String defaultDomain;
	
	 
	
	private String id;
	private String identifier;

	private String accountId;
	private String accountType;
	private boolean systemDefault;
	
	public void validate() {

		if (Strings.isNullOrEmpty(identifier)) {
			throw new ApiException("Invalid Request, Missing<domain>");
		} else {
			identifier = identifier.toLowerCase();
			if (identifier.contains(" ")) {
				throw new ApiException("Invalid Domain Value <" + identifier + ">, Space not allowed.");
			}
			try {
				String encodedValue = URLEncoder.encode(identifier, "UTF-8");
				if (!encodedValue.equals(identifier)) {
					throw new ApiException("Domain Name must not contains any special characters");
				}
				if(Strings.isNullOrEmpty(getRealm())) {
					setRealm(identifier);
				}

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (Strings.isNullOrEmpty(accountId)) {
				throw new ApiException("Invalid Request, missing <accountId>");
			}
			if (Strings.isNullOrEmpty(accountType)) {
				accountType = "SYSTEM";
			}

		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public boolean isSystemDefault() {
		return systemDefault;
	}
	public void setSystemDefault(boolean systemDefault) {
		this.systemDefault = systemDefault;
	}
	public String getDefaultDomain() {
		return defaultDomain;
	}
	public void setDefaultDomain(String defaultDomain) {
		this.defaultDomain = defaultDomain;
	}
	
}
