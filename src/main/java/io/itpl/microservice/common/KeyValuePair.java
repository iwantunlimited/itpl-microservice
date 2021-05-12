package io.itpl.microservice.common;

public class KeyValuePair {

	private String key;
	private String value;
	public static KeyValuePair of(String key,String value){
		KeyValuePair pair = new KeyValuePair();
		pair.setKey(key);
		pair.setValue(value);
		return pair;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Data [key=" + key + ", value=" + value + "]";
	}



}
