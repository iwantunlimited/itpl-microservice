package io.itpl.microservice.system;

import com.google.common.base.Strings;
import io.itpl.microservice.exceptions.ApiException;
import org.springframework.data.annotation.Transient;

import java.util.HashMap;


public class SystemUserMapping {
	
	@Transient public static final String KEY_FIRST_NAME = "FIRST_NAME";
	@Transient public static final String KEY_LAST_NAME = "LAST_NAME";
	@Transient public static final String KEY_MOBILE = "MOBILE";
	@Transient public static final String KEY_MCC = "MCC";
	@Transient public static final String KEY_EMAIL = "EMAIL";
	@Transient public static final String EMPTY_LINK_VALUE = "NONE";
	@Transient public static final int TYPE_DEFAULT = 1;
	@Transient public static final int TYPE_USERID_PATH = 2;
	@Transient public static final int TYPE_MODEL_MAP = 3;

	private SystemUser defaultUserInfo;
	private String userIdPath;
	private HashMap<String,String> modelMap;
	private int linkType;
	
	public void validate() {

		if(defaultUserInfo != null) {
			userIdPath = null;
			modelMap = null;
			this.linkType = SystemUserMapping.TYPE_DEFAULT;
		}
		if(!Strings.isNullOrEmpty(userIdPath)) {
			defaultUserInfo = null;
			modelMap = null;
			this.linkType = SystemUserMapping.TYPE_USERID_PATH;
		}
		if(modelMap != null && !modelMap.isEmpty()) {
			defaultUserInfo = null;
			userIdPath = null;
			boolean valid = modelMap.containsKey(SystemUserMapping.KEY_FIRST_NAME) &&
					modelMap.containsKey(SystemUserMapping.KEY_LAST_NAME) &&
					modelMap.containsKey(SystemUserMapping.KEY_MCC) &&
					modelMap.containsKey(SystemUserMapping.KEY_MOBILE) &&
					modelMap.containsKey(SystemUserMapping.KEY_EMAIL);
			if(!valid) {
				throw new ApiException("modelMap is invalid");
			}
			this.linkType = SystemUserMapping.TYPE_MODEL_MAP;
		}
		if(this.linkType==0 || this.linkType > 3) {
			throw new ApiException("Invalid Content, provide valid link info");
		}
	}
	

	public SystemUser getDefaultUserInfo() {
		return defaultUserInfo;
	}
	public void setDefaultUserInfo(SystemUser defaultUserInfo) {
		this.defaultUserInfo = defaultUserInfo;
	}
	public String getUserIdPath() {
		return userIdPath;
	}
	public void setUserIdPath(String userIdPath) {
		this.userIdPath = userIdPath;
	}
	public HashMap<String, String> getModelMap() {
		return modelMap;
	}
	public void setModelMap(HashMap<String, String> modelMap) {
		this.modelMap = modelMap;
	}

	public int getLinkType() {
		return linkType;
	}

	public void setLinkType(int linkType) {
		this.linkType = linkType;
	}
	
	
	
	

}
