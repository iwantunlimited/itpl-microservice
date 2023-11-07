package io.itpl.microservice.common;

import io.itpl.microservice.LoggedInUser;
import lombok.ToString;

import java.io.Serializable;

@ToString
public class UserObject implements Serializable {

	private String id;
	private String avtarUrl;
	private String firstName;
	private String lastName;
	private String username;
	private String mobile;
	private String email;
	private String locale;
	private String timeZoneId;
	private String mcc;
	private String ssid;
	private String realm;
	private String domain;
	private String systemUserId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAvtarUrl() {
		return avtarUrl;
	}
	public void setAvtarUrl(String avtarUrl) {
		this.avtarUrl = avtarUrl;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getTimeZoneId() {
		return timeZoneId;
	}

	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}

	public static UserObject from(LoggedInUser user){
		UserObject userObject = new UserObject();
		userObject.setFirstName(user.getFirstName());
		userObject.setId(user.getId());
		userObject.setEmail(user.getEmail());
		userObject.setUsername(user.getUserName());
		userObject.setLastName(user.getLastName());
		userObject.setSsid(user.getDomainSsid());
		userObject.setMcc(user.getMcc());
		userObject.setMobile(user.getMobile());
		userObject.setTimeZoneId(user.getTimeZoneId());
		userObject.setLocale(user.getLocale());
		userObject.setAvtarUrl(user.getAvtarImageUrl());
		userObject.setDomain(user.getDomainSsid());
		userObject.setRealm(user.getRealm());
		return userObject;
	}
	public LoggedInUser toLoggedInUser(){
		LoggedInUser userObject = new LoggedInUser();
		userObject.setFirstName(this.firstName);
		userObject.setId(this.id);
		userObject.setEmail(this.email);
		userObject.setLastName(this.lastName);
		userObject.setDomain(this.domain);
		userObject.setRealm(this.realm);
		userObject.setDomainSsid(this.domain);
		userObject.setMcc(this.mcc);
		userObject.setMobile(this.mobile);
		userObject.setTimeZoneId(this.timeZoneId);
		userObject.setLocale(this.locale);
		userObject.setAvtarImageUrl(this.avtarUrl);

		return userObject;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSystemUserId() {
		return systemUserId;
	}

	public void setSystemUserId(String systemUserId) {
		this.systemUserId = systemUserId;
	}
}
