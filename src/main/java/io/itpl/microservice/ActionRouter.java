package io.itpl.microservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.itpl.microservice.api.ApiResponse;
import io.itpl.microservice.exceptions.ApiException;
import io.itpl.microservice.utils.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class ActionRouter {

	private static final Logger logger = LoggerFactory.getLogger(ActionRouter.class);
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	ServiceContext serviceContext;
	
	public ApiResponse execute(ActionRequest actionRequest, JsonNode requestBody, Map<String,String> pathVariables) {
		if(serviceContext==null) {
			throw new ApiException("Can't process Api Request without Service Context");
		}else {
			if(serviceContext.getActionRegistry().isEmpty()) {
				throw new ApiException("Api Modules Registry is empty, make sure All Modules are loaded and ServiceContext has been initialized");
			}
		}
		String actionCode = actionRequest.getActionCode();
		if(CommonHelper.isNull(actionCode)) {
			logger.error("Action Code is null");
			throw new ApiException("Invalid Api Request, <actionCode> is missing");
		}
		String []tokens = actionCode.split("_");
		
		if(tokens.length<0) {
			logger.error("Action Code is invalid:"+actionCode);
			throw new ApiException("Invalid Api Request, <actionCode> is invalid:"+actionCode);
		}
		String moduleId = tokens[tokens.length-1];
		logger.trace("Target Module Id:"+moduleId);
		String actionClassName = serviceContext.getActionRegistry().lookup(moduleId);
		if(CommonHelper.isNull(actionClassName)) {
			logger.trace("Target Module Id:"+moduleId +"is not registed in Action Registry");
			throw new ApiException("Module ["+moduleId + "] has not been registered");
		}
		try {
			Action moduleAction = (Action)serviceContext.getApplicationContext().getBean(Class.forName(actionClassName));
			return moduleAction.execute(actionRequest, requestBody,pathVariables);
		} catch (BeansException | ClassNotFoundException e) {
			e.printStackTrace();
			ApiResponse.error("Error while mapping to actionCode:"+actionCode+","+e.getMessage());
			
		}catch(ApiException ae) {
			
			return ApiResponse.error(ae.getMessage());
		}
		return ApiResponse.error("Service not found for requested actionCode:"+actionRequest.getActionCode());
		//throw new InvalidActionCodeException("actionCode:"+actionCode+" is not valid");
	
		
	}
}
