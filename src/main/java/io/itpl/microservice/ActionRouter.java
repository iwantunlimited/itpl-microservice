package io.itpl.microservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.itpl.microservice.api.ApiResponse;
import io.itpl.microservice.exceptions.ApiException;
import io.itpl.microservice.exceptions.ItemNotFoundException;
import io.itpl.microservice.utils.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class ActionRouter {

	private static final Logger logger = LoggerFactory.getLogger(ActionRouter.class);

	
	@Autowired
	ServiceContext serviceContext;

	@Autowired
	ApplicationContextProvider contextProvider;
	
	public ApiResponse execute(ActionRequest actionRequest, JsonNode requestBody, Map<String,String> pathVariables) {
		ApplicationContext applicationContext = null;

		if(serviceContext==null) {
			logger.error("Service Context not ready");
			throw new ApiException("Can't process Api Request without Service Context");
		}else {
			applicationContext = contextProvider.getApplicationContext();
			if(applicationContext==null){
				logger.trace("Application content is null");
				throw new ApiException("Application context not ready");
			}else{
				logger.trace("{} - Application Context will be used to load beans ",applicationContext.getApplicationName()+"("+applicationContext.getDisplayName()+")");
			}
			if(serviceContext.getActionRegistry().isEmpty()) {
				throw new ApiException("Api Modules Registry is empty, make sure All Modules are loaded and ServiceContext has been initialized");
			}
		}
		String actionCode = actionRequest.getActionCode();
		logger.trace("[{}] Action Code resolved.",actionCode);
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
		logger.trace("Target Class Name:{}",actionClassName);
		if(CommonHelper.isNull(actionClassName)) {
			logger.trace("Target Module Id:"+moduleId +"is not registed in Action Registry");
			throw new ApiException("Module ["+moduleId + "] has not been registered");
		}else{
			try{
				logger.trace("Looking Bean Registry :{}",moduleId);
				Action moduleAction = serviceContext.load(moduleId);
				logger.trace("Successfully Loaded Module from Bean Registry :{}",moduleId);
				return moduleAction.execute(actionRequest, requestBody, pathVariables);
			}catch (ItemNotFoundException ie) {
				//return ApiResponse.error(e.getMessage());
				logger.trace("Error loading Module from Bean Registry :{},{}",moduleId,ie.getMessage());
				try {
					Class actionClass = Class.forName(actionClassName);
					logger.trace("[{}] action-class Loaded Successfully", actionClass);
					try {
						Action moduleAction = (Action) applicationContext.getBean(actionClass);
						return moduleAction.execute(actionRequest, requestBody, pathVariables);
					} catch (NoSuchBeanDefinitionException e) {
						int count = e.getNumberOfBeansFound();
						String name = e.getBeanName();
						Class beanClass = e.getBeanType();
						ResolvableType type = e.getResolvableType();
						String typeName = type.getType().getTypeName();
						logger.error("[{}] Beans found for bean {}({}),type:{}", count, name, beanClass.getName(), typeName);
						e.printStackTrace();
						return ApiResponse.error("No Such Bean Found:" + actionCode + "," + e.getLocalizedMessage());
					} catch (BeansException e) {
						e.printStackTrace();
						return ApiResponse.error("Bean not found for actionCode:" + actionCode + "," + e.getLocalizedMessage());

					} catch (ApiException ae) {
						ae.printStackTrace();
						return ApiResponse.error(ae.getMessage());
					} catch (Exception e) {
						e.printStackTrace();
						return ApiResponse.error("Exception while executing actionCode:" + actionCode + "," + e.getLocalizedMessage());
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					return ApiResponse.error("Class not found for actionCode:" + actionCode + "," + e.getLocalizedMessage());

				} catch (Exception e) {
					e.printStackTrace();
					return ApiResponse.error("something went wrong:" + actionCode + "," + e.getLocalizedMessage());
				}
			}

		}

		//return ApiResponse.error("Service not found for requested actionCode:"+actionRequest.getActionCode());
		//throw new InvalidActionCodeException("actionCode:"+actionCode+" is not valid");
	
		
	}
}
