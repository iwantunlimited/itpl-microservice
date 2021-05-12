package io.itpl.microservice.common;

import io.itpl.microservice.LoggedInUser;

public class UserObject {

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
		return userObject;
	}

}
