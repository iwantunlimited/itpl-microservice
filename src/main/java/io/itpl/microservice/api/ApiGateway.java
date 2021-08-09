package io.itpl.microservice.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;
import io.itpl.microservice.utils.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 *  ApiGateway is an entry point of Api Call and  is responsible for E2E execution of the ApiCall.
 */

@Component
public class ApiGateway extends DefaultApiGateway {
	private static final Logger logger = LoggerFactory.getLogger(ApiGateway.class);


	/**
	 *  Utility method to Build the  ApiRequest with Given Action Code and Blank Body.
	 * @param actionCode
	 * @return
	 */
	public JsonNode blankBody(String actionCode,String tid,String sid) {
		Map<String,Object> emptyBody = new HashMap<>();

		emptyBody.put("actionCode",actionCode);
		emptyBody.put("tid",tid);
		emptyBody.put("sid", sid);

		Map<String,String> payload = new HashMap<>();
		payload.put("data","blank-body");
		emptyBody.put("payload", payload);

		return objectMapper.convertValue(emptyBody, JsonNode.class);

	}
	public JsonNode readParams(HttpServletRequest http){
		String actionCode = http.getParameter("actionCode");
		String tid = http.getParameter("tid");
		String sid = http.getParameter("sid");
		logger.trace("[readParams] actionCode:{},tid:{},sid:{}",actionCode,tid,sid);
		return blankBody(actionCode,tid,sid);
	}
	public JsonNode requestBody(HttpServletRequest http,String defaultActionCode){
		return readParams(http,defaultActionCode);
	}
	public JsonNode readParams(HttpServletRequest http,String defaultActionCode){
		String actionCode = http.getParameter("actionCode");
		if(Strings.isNullOrEmpty(actionCode)){
			actionCode = defaultActionCode;
		}
		String tid = http.getParameter("tid");
		String sid = http.getParameter("sid");
		logger.trace("[readParams] actionCode:{},tid:{},sid:{}",actionCode,tid,sid);
		return blankBody(defaultActionCode,tid,sid);
	}
	/**
	 *  Signature A :
	 *  There is no RequestBody,
	 *  HTTP/GET, and Input is Provided using Path Variables.
	 * @param httpReq
	 * @param pathVariables
	 * @return
	 */
	public ApiResponse execute(HttpServletRequest httpReq,Map<String,String> pathVariables) {
		// OfCourse The Request Body is going to be NULL
		return execute(httpReq,null,pathVariables);
	}

	/**
	 * SIGNATURE B :
	 * Most Typical Case of HTTP/POST with JSON Request Body and No Path Variables.
	 *
	 * @param httpReq
	 * @param req
	 * @return
	 */
	public ApiResponse execute(HttpServletRequest httpReq,JsonNode req) {
		// Let's mark the pathVariables as NULL/
		return execute(httpReq,req,null);
	}
	protected ApiResponse executeWithBody(HttpServletRequest http,Object obj){
		JsonNode json = objectMapper.convertValue(obj,JsonNode.class);
		return execute(http,json);
	}
	protected ApiResponse execute(HttpServletRequest http,String actionCode,String id){
		return execute(http,requestBody(http,actionCode),map("id",id));
	}

	/**
	 *  SIGNATURE C :
	 *   HTTP/GET with "Query String" or HTTP/POST with "form-data" or "url-encoded-form-data".
	 *   This is a rare case, but option is needed.
	 * @param httpRequest
	 * @return
	 */
	public ApiResponse execute(HttpServletRequest httpRequest) {
		Hashtable<String,String> requestParameters = new Hashtable<String,String>();
		Enumeration<String> params =  httpRequest.getParameterNames();
		int paramsCount = 0;
		while(params.hasMoreElements()) {
			String name = params.nextElement();
			String value = httpRequest.getParameter(name);
			logger.trace("Added Http Request Parameter:"+(paramsCount++) + "<"+name+">:<"+value+">");
			requestParameters.put(name, value);
		}
		logger.trace("["+paramsCount+"]- Parameters Received in HttpRequest");
		// Here we Generated a Map Object, Lets convert it to JSON and forward to MASTER CALL.
		return execute(httpRequest,objectMapper.convertValue(requestParameters, JsonNode.class),null);
	}

	protected Map<String,String> map(String key,String value){
		return CommonHelper.asMap(key,value);
	}


}
