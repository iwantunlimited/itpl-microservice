package io.itpl.microservice;

import com.fasterxml.jackson.databind.JsonNode;
import io.itpl.microservice.api.ApiResponse;

import java.util.Map;


public interface Action {

	
	
	public static final int ACTION_DEFAULT_MOULDE = 0;
	public static final int ACTION_PING_API = 1;
	
	public ApiResponse execute(ActionRequest action, JsonNode requestBody, Map<String,String> pathVariable);
}
