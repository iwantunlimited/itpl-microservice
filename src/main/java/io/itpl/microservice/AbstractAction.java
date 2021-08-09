
package io.itpl.microservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

import io.itpl.microservice.api.ApiResponse;
import io.itpl.microservice.base.Auditable;
import io.itpl.microservice.mongo.MongoExecutorService;
import io.itpl.microservice.system.BroadcastMessageBody;
import io.itpl.microservice.system.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;


/**
 *  Abstract Action Class is responsible for performing Pre and Post Transaction Operations.
 *  For Example, Population of Audit Data, or SSID before Transaction Starts.
 *  And Relays the Transaction Details to the System Service Post Transaction Completion.
 */
public abstract class AbstractAction extends MongoExecutorService implements Action {

	private static final Logger logger = LoggerFactory.getLogger(AbstractAction.class);

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	SystemService relayService;

	@Autowired
	ServiceContext serviceContext;

	/**
	 * This method creates an instance of ApiResponse with Blank Response Body.
	 * The Action Class is responsible to  inject the response body in Api Response after completion of transaction.
	 * @return Empty ApiResponse before Actual transaction starts.
	 */
	public ApiResponse init() {
		Date current = new Date();
		ApiResponse resp = new ApiResponse();
		resp.setTimestamp(new Date());
		int responseTime = -1;
		Hashtable<String,String> empty = new Hashtable<String,String>();
		empty.put("Body","<blank>");
		resp.setData(objectMapper.convertValue(empty, JsonNode.class));
		return resp;
		
	}

	/**
	 * This method has been depracted since we started using actionCode in String value in Switch-Case statement.
	 * Newer version of JDK Supports using String value in Switch-Case, Hence no need for additional overhead
	 * of maintaining mapping of actionCode and respective integer value.
	 *
	 * @param actionCode String Value of action code.
	 * @return numeric value of the Action Code mapped in Action Interface.
	 *
	 */
	public static int getActionCode(String actionCode)  {
		return getActionCode(actionCode, Action.class);
	}
	public static int getActionCode(String actionCode,Class actionClass) {
		Class<Action> action = actionClass;
		Field fields[] = action.getDeclaredFields();
		for(Field field:fields) {
			String name = field.getName();
			int value;
			try {
				value = field.getInt(null);
				if(actionCode.equals(name)) {
					return value;
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return -1;
	}

	/**
	 * broadcast transaction details to the System Service for further Post Transaction processing,
	 * For example, Audit Log, Notification etc.
	 * @param req
	 * @param request
	 * @param response
	 */
	public void execute(ActionRequest req, JsonNode request, JsonNode response) {
		if(!serviceContext.isTransactionRelayEnabled()){
			return;
		}
		final BroadcastMessageBody message = new BroadcastMessageBody();
		JsonNode domainNode = response.path("domain");
		if(domainNode != null){
			String domain = domainNode.textValue();
			if(!Strings.isNullOrEmpty(domain)){
				message.setDomain(domain);
			}
		}
		message.setActionRequest(req);
		message.setResponseObject(response);

		Thread relayProcess = new Thread(new Runnable() {
			public void run() {
				relayService.send(message);
			}
		});
		relayProcess.start();
	}
	public ApiResponse execute(ActionRequest req,JsonNode response){
		execute(req,null,response);
		ApiResponse apiResponse = init();
		apiResponse.setData(response);
		return apiResponse;
	}
	public ApiResponse execute(ActionRequest req,Object response){
		JsonNode jsonNode = objectMapper.convertValue(response,JsonNode.class);
		execute(req,null,jsonNode);
		ApiResponse apiResponse = init();
		apiResponse.setData(jsonNode);
		return apiResponse;
	}

	/**
	 *  This signature is needed to ensure User from one domain are able to create new Users in Other domain.
	 * @param req
	 * @param action
	 */
	public void create(Auditable req, ActionRequest action){
		create(req,action,true);
	}
	/**
	 * Populate the Audit Data into Requested Object during CRUD Operations.
	 * @param req
	 * @param action
	 */
	public void create(Auditable req,ActionRequest action,boolean autoPopulateDomain){
		String actionCode = action.getActionCode();
		LoggedInUser currentUser = action.getCurrentUser();
		if(currentUser != null && autoPopulateDomain){
			String domain = currentUser.getDomainSsid();
			req.setDomain(domain);
			req.setDomainDetected(true);
			req.setTokenDetected(true);
			req.setCreatedBy(currentUser.getId());
			req.setRealm(currentUser.getDomain());
		}else{
			logger.trace("[{}] User Token not detected in request.",actionCode);
		}
		req.setCreatedOn(new Date());
		req.setDeviceInfo(action.getDeviceInfo());
		req.setIpAddress(action.getIpAddress());
		req.setRemark(action.getRemarks());
		req.setAppName(action.getAppName());
		req.setAppType(action.getAppType());
		req.setAppVersion(action.getAppVersion());
		req.setOsName(action.getOsName());
		req.setDeviceMac(action.getDeviceMac());
		logger.trace("Abstarct-action-Create-req-{}",req);
	}

	/**
	 * Populate Audit Data into Object while CRUD operations.
	 * @param req
	 * @param action
	 * @param remark
	 */
	public void update(Auditable req,ActionRequest action,String remark){
		String actionCode = action.getActionCode();
		LoggedInUser currentUser = action.getCurrentUser();
		if(currentUser != null){
			req.setTokenDetected(true);
			req.setLastUpdatedBy(currentUser.getId());
		}else{
			logger.trace("[{}] User Token not detected in request.",actionCode);
		}

		req.setLastUpdatedOn(new Date());
		req.setDeviceInfo(action.getDeviceInfo());
		req.setIpAddress(action.getIpAddress());
		req.setRemark(Strings.isNullOrEmpty(action.getRemarks())? remark:action.getRemarks());
		req.setAppName(action.getAppName());
		req.setAppType(action.getAppType());
		req.setAppVersion(action.getAppVersion());
		req.setOsName(action.getOsName());
		req.setDeviceMac(action.getDeviceMac());
		logger.trace("Abstarct-action-Update-req-{}",req);
	}
	protected String mapId(Map<String,String> pathVariables){
		if(pathVariables != null && !pathVariables.isEmpty() && pathVariables.containsKey("id")){
			return pathVariables.get("id");
		}else{
			return null;
		}
	}
	protected String mapDomain(ActionRequest action){
		if(action.getCurrentUser()!=null){
			return action.getCurrentUser().getDomainSsid();
		}else{
			return null;
		}
	}
	protected String mapUserId(ActionRequest action){
		if(action.getCurrentUser()!=null){
			return action.getCurrentUser().getId();
		}else{
			return null;
		}
	}
	protected int mapUserType(ActionRequest action){
		if(action.getCurrentUser()!=null){
			return action.getCurrentUser().getUserType();
		}else{
			return Integer.MAX_VALUE;
		}
	}
}

