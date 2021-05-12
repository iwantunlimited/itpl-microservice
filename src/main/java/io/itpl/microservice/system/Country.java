package io.itpl.microservice.system;

import org.springframework.data.mongodb.core.index.Indexed;

public class Country {

	private String id;
	@Indexed private long code;
	@Indexed private String name;
	
	private String mcc;
	@Indexed private String isoCode;
	@Indexed private boolean systemDefault;
	
	private String timezoneId;
	private String localeId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getCode() {
		return code;
	}
	public void setCode(long code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	public String getIsoCode() {
		return isoCode;
	}
	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}
	public boolean isSystemDefault() {
		return systemDefault;
	}
	public void setSystemDefault(boolean systemDefault) {
		this.systemDefault = systemDefault;
	}
	public String getTimezoneId() {
		return timezoneId;
	}
	public void setTimezoneId(String timezoneId) {
		this.timezoneId = timezoneId;
	}
	public String getLocaleId() {
		return localeId;
	}
	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}
	
}
