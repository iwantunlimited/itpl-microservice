package io.itpl.microservice.system;

import java.util.Locale;

public class LocaleOption {
	private String language;
	private String localeId;
	private String country;
	private String mcc;
	
	public LocaleOption() {
		this("Engilish","in_US","","+91");
	}
	public LocaleOption(String language, String locale,String country,String mcc) {
		this.language = language;
		this.localeId = locale;
		this.country = country;
		this.mcc = mcc;
	}
	public Locale getInstance() {
		return new Locale(this.localeId);
	}
	public static LocaleOption getDefault() {
		return new LocaleOption("Engilish","in_US","","+91");
	}
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getLocaleId() {
		return localeId;
	}
	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
}
