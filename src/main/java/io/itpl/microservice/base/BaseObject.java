package io.itpl.microservice.base;

import com.google.common.base.Strings;
import io.itpl.microservice.exceptions.InvalidInputException;
import io.itpl.microservice.exceptions.ItemNotFoundException;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * 
 * @author timirpatel
 * @since 12-dec-2020
 * 
 * The Base Object class defines a set of basic properties that can be supported in every model of master object/document.
 * 
 */

public class BaseObject  {

	/**
	 *  Holds a SystemUserId detected from the token for the Audit Purpose.
	 */
	private String createdBy;
	/**
	 *  Holds a SystemUserId detected from the token for the Audit Purpose.
	 */
	private String lastUpdatedBy;
	/**
	 *  UTC time-stamp of the Create Date in case of CRUD operations.
	 */
	@Indexed private Date createdOn;
	/**
	 *  UTD time-stamp of the Edit or Delete Operations in Case of CRUN Operation.
	 */
	private Date lastUpdatedOn;
	/**
	 * @implNote the status of object whether active or inactive.
	 */
	@Indexed private boolean inactive;
	/**
	 * @implNote - Soft Deletion flag. It may require to just execute soft delete instead of physical delete operation.
	 * This flag indicates whether the object/document is deleted or not.
	 */
	@Indexed private boolean deleted;
	/**
	 * @implNote : To display in the Audit Information.
	 * (i.e. Reason for Change while edit or delete)
	 * This is options field.
	 * @since : 3.0
	 */
	private String remark;
	/**
	 *  Remote Host Ip Address of the User Device who executed the request.
	 */
	private String ipAddress;
	/**
	 * Can contain the User-Agent String in most of the time. This can be useful for Audit purpose.
	 */
	private String deviceInfo;

	/**
	 * The data owner domain.
	 */
	@Indexed protected String domain;
	/**
	 * Any other non-standard information that is needed to be included with object for display purpose only.
	 */
	private Map<String,Object> metaInfo;
	@Indexed  private int sequenceNumber;
	@Indexed  private String ssid;
	private String appName;
	private String appVersion;
	private String appType;
	private String osName;
	private String deviceMac;
	private String clientId;
	@Transient private boolean tokenDetected;
	@Transient private boolean domainDetected;
	@Transient private String transactionId;
	private String realm;
	@Transient private boolean error;
	@Transient private String errorMessage;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public boolean isTokenDetected() {
		return tokenDetected;
	}

	public void setTokenDetected(boolean tokenDetected) {
		this.tokenDetected = tokenDetected;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public boolean isDomainDetected() {
		return domainDetected;
	}

	public void setDomainDetected(boolean domainDetected) {
		this.domainDetected = domainDetected;
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

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}
	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}
	public boolean isInactive() {
		return inactive;
	}
	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public Map<String, Object> getMetaInfo() {
		return metaInfo;
	}
	public void setMetaInfo(Map<String, Object> metaInfo) {
		this.metaInfo = metaInfo;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	protected static boolean missing(String value){
		return Strings.isNullOrEmpty(value);
	}
	protected static boolean exists(String value){
		return !Strings.isNullOrEmpty(value);
	}
	protected static boolean missing(Collection value){
		return value == null || value.isEmpty();
	}
	protected static boolean exists(Collection value){
		return value != null && !value.isEmpty();
	}
	protected static void returnInputError(String msg){
		throw new InvalidInputException(msg);
	}
	protected static void returnNotFoundError(String msg) throws ItemNotFoundException {
		throw new ItemNotFoundException(msg);
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
