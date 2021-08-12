package io.itpl.microservice.system;

import io.itpl.microservice.common.GeoPoint;
import org.springframework.data.mongodb.core.index.Indexed;

public class City {

	private String id;
	private String name;
	private String cityCode;
	@Indexed private String stateId;
	@Indexed private String stateName;
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
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
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

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public GeoPoint getGpsLocation() {
		return gpsLocation;
	}

	public void setGpsLocation(GeoPoint gpsLocation) {
		this.gpsLocation = gpsLocation;
	}
}
