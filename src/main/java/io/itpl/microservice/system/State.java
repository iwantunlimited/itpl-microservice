package io.itpl.microservice.system;

import io.itpl.microservice.common.GeoPoint;
import org.springframework.data.mongodb.core.index.Indexed;

public class State {

	@Indexed private String id;
	@Indexed private String name;
	private String timeZoneId;
	private String localeId;
	@Indexed private long code;
	@Indexed private String stateCode;
	@Indexed private String countryId;
	@Indexed private String countryName;
	@Indexed private String countryCode;
	private GeoPoint gpsLocation;
	
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

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public GeoPoint getGpsLocation() {
		return gpsLocation;
	}

	public void setGpsLocation(GeoPoint gpsLocation) {
		this.gpsLocation = gpsLocation;
	}
}
