package io.itpl.microservice.pojo;

import io.itpl.microservice.common.GeoPoint;
import io.itpl.microservice.system.City;

public class Address {


	private String id;
	/**
	 *  i.e. SystemUserId
	 */
	private String userId;
	/**
	 *  Profile Id
	 */
	private String profileId;
	/**
	 *  i.e. Home, warehouse etc.
	 */
	private String addressType;
	private String address;
	private String streetName;
	private String locality;
	private String landmark;
	private String postalCode;
	private GeoPoint gpsLocation;
	private String cityId;
	private City city;
	private boolean defaultAddress;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public GeoPoint getGpsLocation() {
		return gpsLocation;
	}

	public void setGpsLocation(GeoPoint gpsLocation) {
		this.gpsLocation = gpsLocation;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public boolean isDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}
}
