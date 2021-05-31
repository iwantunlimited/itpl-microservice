package io.itpl.microservice.pojo;

import io.itpl.microservice.common.KeyValuePair;

import java.util.List;

public class Specifications {
	
	private String name;
	private List<KeyValuePair> data;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<KeyValuePair> getData() {
		return data;
	}
	public void setData(List<KeyValuePair> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Specifications [name=" + name + ", data=" + data + "]";
	}

}
