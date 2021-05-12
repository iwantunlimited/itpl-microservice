package io.itpl.microservice.system;

import org.springframework.data.mongodb.core.index.Indexed;

public class State {

	@Indexed private String id;
	@Indexed private String name;
	private String timeZoneId;
	private String localeId;
	@Indexed private long code;
	
	@Indexed private String countryId;
	@Indexed private String countryName;
	
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
	public long getCode() {
		return code;
	}
	public void setCode(long code) {
		this.code = code;
	}
	
}
