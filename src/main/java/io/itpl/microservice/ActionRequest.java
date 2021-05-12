package io.itpl.microservice;

public class ActionRequest {

	private String actionCode;
	private String systemUserId;


	private String username;
	private String ipAddress;
	private String clientIpAddress;
	private String clientInfo;
	private String deviceInfo;
	private String locationInfo;
	private String remarks;
	private LoggedInUser currentUser;
	private String hostName;
	private String port;
	private String appName;
	private String appVersion;
	private String appType;
	private String osName;
	private String deviceMac;
	private String clientId;
	private String transactionId;
	private String signatureKey;
	private String transactionType;
	private String mobile;
	private String source;
	private boolean userTokenDetected;

	public String getDomainSsid() {
		return domainSsid;
	}

	public void setDomainSsid(String domainSsid) {
		this.domainSsid = domainSsid;
	}

	private String domainSsid;


	public String getSystemUserId() {
		return systemUserId;
	}

	public void setSystemUserId(String systemUserId) {
		this.systemUserId = systemUserId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getSignatureKey() {
		return signatureKey;
	}

	public void setSignatureKey(String signatureKey) {
		this.signatureKey = signatureKey;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

	public LoggedInUser getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(LoggedInUser currentUser) {
		this.currentUser = currentUser;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public ActionRequest() {}
	
	public ActionRequest(String actionCode, String username, String ipAddress, String clientInfo, String deviceInfo,
                         String locationInfo, String remarks) {
		super();
		this.actionCode = actionCode;
		this.username = username;
		this.ipAddress = ipAddress;
		this.clientInfo = clientInfo;
		this.deviceInfo = deviceInfo;
		this.locationInfo = locationInfo;
		this.remarks = remarks;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getClientInfo() {
		return clientInfo;
	}
	public void setClientInfo(String clientInfo) {
		this.clientInfo = clientInfo;
	}
	public String getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	public String getLocationInfo() {
		return locationInfo;
	}
	public void setLocationInfo(String locationInfo) {
		this.locationInfo = locationInfo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getClientIpAddress() {
		return clientIpAddress;
	}

	public void setClientIpAddress(String clientIpAddress) {
		this.clientIpAddress = clientIpAddress;
	}

	public boolean isUserTokenDetected() {
		return userTokenDetected;
	}

	public void setUserTokenDetected(boolean userTokenDetected) {
		this.userTokenDetected = userTokenDetected;
	}
}
