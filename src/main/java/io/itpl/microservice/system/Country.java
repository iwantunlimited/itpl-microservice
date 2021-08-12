package io.itpl.microservice.system;

import io.itpl.microservice.common.GeoPoint;
import io.itpl.microservice.common.LocalTimeZone;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

public class Country {

	private String id;

	@Indexed private String name;
	
	private String mcc;
	@Indexed private String isoCode;
	@Indexed private String countryCode;
	@Indexed private boolean systemDefault;
	private String timezoneId;
	private List<LocalTimeZone> timeZones;
	private String localeId;
	private String emoji;
	private String emojiCode;
	private GeoPoint gpsLocation;
	private String currencyCode;
	private String currencySymbol;
	private String localName;
	private String continent;
	private String region;

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

	public List<LocalTimeZone> getTimeZones() {
		return timeZones;
	}

	public void setTimeZones(List<LocalTimeZone> timeZones) {
		this.timeZones = timeZones;
	}

	public String getEmoji() {
		return emoji;
	}

	public void setEmoji(String emoji) {
		this.emoji = emoji;
	}

	public String getEmojiCode() {
		return emojiCode;
	}

	public void setEmojiCode(String emojiCode) {
		this.emojiCode = emojiCode;
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

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
}
