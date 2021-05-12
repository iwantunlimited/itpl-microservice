package io.itpl.microservice.system;

import io.itpl.microservice.base.BaseObject;
import io.itpl.microservice.base.ObjectValidator;
import org.springframework.data.annotation.Transient;

public class SystemUser extends BaseObject implements ObjectValidator {
	
	@Transient public static final String [] USER_TYPES = {"CUSTOMER","STAFF","MERCHANT","PARTNER","OTHER"};
	public static final int TYPE_CUSTOMER = 0;
	public static final int TYPE_MERCHANT = 2;
	public static final int TYPE_ADMIN = -1;
	
	
	private String id;
	private String domain;
	private int userType;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private int encryptionType;
	
	private String mobile;
	private String mcc;
	private String email;
	private String externalReferenceId;
	
	private boolean isBlocked;
	private boolean isDeleted;
	private String clientVerion;
	private String clientPlatform;
	private String timeZoneId;
	private String localeId;
	private LocaleOption locale;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getEncryptionType() {
		return encryptionType;
	}
	public void setEncryptionType(int encryptionType) {
		this.encryptionType = encryptionType;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getExternalReferenceId() {
		return externalReferenceId;
	}
	public void setExternalReferenceId(String externalReferenceId) {
		this.externalReferenceId = externalReferenceId;
	}
	public boolean isBlocked() {
		return isBlocked;
	}
	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String toString() {
		return "SystemUser [id=" + id + ", domain=" + domain + ", userType=" + userType + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", username=" + username + ", encryptionType=" + encryptionType
				+ ", mobile=" + mobile + ", mcc=" + mcc + ", email=" + email + ", externalReferenceId="
				+ externalReferenceId + ", isBlocked=" + isBlocked + ", isDeleted=" + isDeleted + "]";
	}
	public String getClientVerion() {
		return clientVerion;
	}
	public void setClientVerion(String clientVerion) {
		this.clientVerion = clientVerion;
	}
	public String getClientPlatform() {
		return clientPlatform;
	}
	public void setClientPlatform(String clientPlatform) {
		this.clientPlatform = clientPlatform;
	}
	public String getTimeZoneId() {
		return timeZoneId;
	}
	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}
	public String getLocaleId() {
		return localeId;
	}
	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}
	public LocaleOption getLocale() {
		return locale;
	}
	public void setLocale(LocaleOption locale) {
		this.locale = locale;
	}
	

}
