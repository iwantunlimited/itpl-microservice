
package io.itpl.microservice.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import io.itpl.microservice.ActionRequest;
import io.itpl.microservice.ActionRouter;
import io.itpl.microservice.LoggedInUser;
import io.itpl.microservice.redis.LoggedInUserRedisService;
import io.itpl.microservice.utils.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;


@Component
public class DefaultApiGateway implements RestApiGateway {

    private static final Logger logger = LoggerFactory.getLogger(DefaultApiGateway.class);
    @Autowired
    ActionRouter requestMapper;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    LoggedInUserRedisService loggedInUserRedisService;

    @Value("false")
    private boolean apiSecurityEnabled;

    /**
     * The Real Executor of the Api Request. All the overloaded variatns of the Api Call finally make a call to this method.
     * This method forward the ApiRequest to ActionRouter and then Reach to Respective Action Class.
     *
     * Additionally, This method intercepts the Token and HTTP Headers in order to build
     * 	- ActionRequest
     * 	- Separate out the Action Code & Payload from JSON Object
     * 	- Validate the Headers.
     *
     * @param httpReq
     * @param req
     * @param pathVariables
     * @return
     */
    public ApiResponse execute(HttpServletRequest httpReq, JsonNode req, Map<String,String> pathVariables) {

        LoggedInUser loggedInUser = read(httpReq);
        LoggedInUser currentUser = loggedInUserRedisService.getLoggedInUser(loggedInUser.getId());
//        LoggedInUser loggedInUser = loggedInUserRedisService.getLoggedInUser(currentUser.getId());
//        logger.info("Logged in user  : " + loggedInUser);
        logger.info("Current user : " + currentUser);


        String systemUserId;
        if(currentUser == null){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            systemUserId = auth.getName();

        }else{
            systemUserId = currentUser.getId();
        }

        String requestUri = httpReq.getRequestURI();
        String ipAddress =  httpReq.getRemoteAddr() ; // Mostly Api Gateway URL..
        String detectedIpAddress = httpReq.getHeader("X-Forwarded-For");// need this to store in Audit info
        detectedIpAddress = Strings.isNullOrEmpty(detectedIpAddress)?ipAddress:detectedIpAddress;
        String clientInfo = httpReq.getHeader("User-Agent"); // For the Audit info
        String hostName = httpReq.getServerName();
        String port = httpReq.getServerPort() +"";
        String appName = httpReq.getHeader(ApiHeaders.ITPL_APP_NAME);
        String appVersion = httpReq.getHeader(ApiHeaders.ITPL_APP_VERSION);
        String appType = httpReq.getHeader(ApiHeaders.ITPL_APP_TYPE);
        String appOs = httpReq.getHeader(ApiHeaders.ITPL_APP_DEVICEOS);
        String deviceMac = httpReq.getHeader(ApiHeaders.ITPL_APP_DEVICEMAC);
        String clientId = httpReq.getHeader(ApiHeaders.ITPL_APP_CLIENTID);
        // actionCode is must...!!
        String actionCode=null,tid=null,sid=null,remark=null; //
        JsonNode body = null;
        String method = httpReq.getMethod();
        logger.trace("[{}] Http Method:{}",requestUri,method);
        if(Strings.isNullOrEmpty(method)){
            method = "GET";
        }
        if(req != null) { //
            JsonNode action = req.path("actionCode");
            JsonNode tidJson = req.path("tid");
            JsonNode sidJson = req.path("sid");
            body = req.path("payload");
            if(body==null){
                body = req.path("requestBody");
            }
            JsonNode reason = req.path("remark");

            if(action!= null) {
                actionCode = action.textValue();
                if(Strings.isNullOrEmpty(actionCode)) {
                    return ApiResponse.error("Can't Process request without <actionCode> (blank?)");
                }
            }else{
                logger.trace("[{}]<actionCode> Not Present in Http/Request Body",requestUri);
            }
            if(tidJson != null) {
                tid = tidJson.textValue();
            }else{
                logger.trace("[{}]<tid> Not Present in Http/Request Body",requestUri);
            }
            if(sidJson!=null) {
                sid = sidJson.textValue();
            }else{
                logger.trace("[{}]<sid> Not Present in Http/Request Body",requestUri);
            }
            if(reason != null) {
                remark = reason.textValue();
            }

        }
        logger.trace("[{}] Populated from Payload>> actionCode={},tid={},sid={}",requestUri,actionCode,tid,sid);
        if(Strings.isNullOrEmpty(actionCode)|| Strings.isNullOrEmpty(tid) || Strings.isNullOrEmpty(sid)){
            logger.trace("[{}] Incomplete Request (actionCode | tid | sid) >> , Trying to Populate missing attributes from Http/Request Parameters",requestUri );
            if(Strings.isNullOrEmpty(actionCode))
                actionCode = httpReq.getParameter("actionCode");
            if(Strings.isNullOrEmpty(tid))
                tid = httpReq.getParameter("tid");
            if(Strings.isNullOrEmpty(tid))
                sid = httpReq.getParameter("sid");
            if(Strings.isNullOrEmpty(remark))
                remark = httpReq.getParameter("remark");
        }
        if(actionCode == null) {
            return ApiResponse.error("Can't Process request without <actionCode>");
        }
        logger.trace("[{}] X-Forwarded-For : {}",requestUri,detectedIpAddress);
        logger.trace("[{}] Remote-Address : {}",requestUri,ipAddress);
        logger.trace("[{}] host-Name : {}",requestUri,hostName);
        logger.trace("[{}] user-Agent : {}",requestUri,clientInfo);
        logger.trace("[{}] itpl-app-name : {}",requestUri,appName);
        logger.trace("[{}] itpl-client-id : {}",requestUri,clientId);
        logger.trace("[{}] tid : {}",requestUri,tid);
        logger.trace("[{}] sid :{}",requestUri,sid);
		/*
			clientId, transactionId & SignatureKey are extracted already.
			Lets validate the Signature first before executing APi Call.
		 */
        if(securityCheckRequired(actionCode,ipAddress,requestUri) && apiSecurityEnabled){
            if(Strings.isNullOrEmpty(clientId) || Strings.isNullOrEmpty(sid) || Strings.isNullOrEmpty(tid)){
                return ApiResponse.error("Security Error! Invalid Api Request");
            }else{

                try{
                    // Ensure the transaction code is valid and not expired.
                    long timestamp = Long.parseLong(tid);
                    long currentTimestamp = System.currentTimeMillis();
                    long difference = currentTimestamp - timestamp;
                    logger.trace("Remote time:{}",timestamp);
                    logger.trace("Local time:{}",currentTimestamp);
                    logger.trace("Difference:{}",difference);

                    if(difference > 0 && difference > RestApiGateway.TID_VALIDITY) {
                        // Oops >> timestamp must be valid.
                        // Lets shout >>>>>
                        return ApiResponse.error("Error!! Transaction is Expired");
                    }
                }catch(NumberFormatException ne){
                    // Means transactionId does not contains the timestamp.
                    return ApiResponse.error("Error! Invalid Transaction Code.");
                }
                // Lets generated sid locally abd then compare it with one exists in request.
                String localSignature = CommonHelper.sid(tid,clientId);
                if (!localSignature.equals(sid)) {
                    return ApiResponse.error("Security Error! Transactions are allowed from authorised clients only.");
                }else{
                    logger.trace("[{}] SID Check Successful >> actionCode:{},hostName:{}, uri:{}",requestUri,actionCode,ipAddress,requestUri);
                }

            }
        }else{
            // SID check not required.
            logger.trace("[{}] SID Check Skipped >> actionCode:{},hostName:{}, uri:{}",requestUri,actionCode,ipAddress,requestUri);
        }

        /*
         * Audit info will be wrapped in ActionRequest
         */
        ActionRequest actionRequest = new ActionRequest();
        if(currentUser !=null) {
            actionRequest.setCurrentUser(currentUser);
            actionRequest.setUsername(currentUser.getUserName());
            actionRequest.setSystemUserId(currentUser.getId());
            actionRequest.setUserTokenDetected(true);
        }

        actionRequest.setClientInfo(clientInfo);
        actionRequest.setClientIpAddress(detectedIpAddress);
        actionRequest.setIpAddress(ipAddress);
        actionRequest.setAppName(appName);
        actionRequest.setAppVersion(appVersion);
        actionRequest.setAppType(appType);
        actionRequest.setOsName(appOs);
        actionRequest.setDeviceMac(deviceMac);
        actionRequest.setActionCode(actionCode);
        actionRequest.setRemarks(remark);
        actionRequest.setTransactionId(tid);
        actionRequest.setSignatureKey(sid);

        return requestMapper.execute(actionRequest, body,pathVariables);

    }
    /**
     *  Read Barrier Token, Parse it and Convert that to LoggedInUser Object.
     * @param httpReq
     * @return
     */
    public LoggedInUser read(HttpServletRequest httpReq) {
        String header = httpReq.getHeader("Authorization");
        String requestUri = httpReq.getRequestURI();
        if(!Strings.isNullOrEmpty(header)) {
            String[] split_string = header.split("\\.");
            try {
                String base64EncodedBody = split_string[1];
                byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedBody);
                String decodedString = new String(decodedBytes);
                logger.trace("[{}]BARRIER TOKEN>> decodedString:{}",requestUri,decodedString);

                LoggedInUser currentUser = objectMapper.readValue(decodedString, LoggedInUser.class);
                return currentUser;
            }catch(JsonProcessingException | ArrayIndexOutOfBoundsException e){
                logger.error("[{}]Exception while Reading Token:{}",requestUri,e.getMessage());
                return null;
            }

        }else{
            logger.trace("[{}] Http-Header <Authorization> Not Present in Request",requestUri);
            return null;
        }
    }

    /**
     *  SignatureKey Verification might not required for certain APIs (for example CDN Apis, or Other Public Apis),
     *  or For the Obvious Default Hosts (i.e. localhost or known hosts).
     *   RestApiGateway Holds the Static Configuration of Action Codes, Host Names and URIS to be excluded for SignatureKey
     *   Verification. This method validate the same.
     *
     * @param actionCode
     * @param hostName
     * @param requestUri
     * @return
     */
    private boolean securityCheckRequired(String actionCode, String hostName,String requestUri){
        long count = Arrays.stream(WHITELISTED_ACTIONS).filter(action -> action.equals(actionCode)).count();
        if(count > 0){
            logger.trace("[WHITELISTED ACTION] >> Skipping Security Check for actionCode: {} ",actionCode);
            return false;
        }
        count = Arrays.stream(WHITELISTED_HOSTS).filter(host -> host.toLowerCase().equals(hostName.toLowerCase())).count() ;
        if(count > 0){
            logger.trace("[WHITELISTED HOST] >> Skipping Security Check for host: {} ",hostName);
            return false;
        }
        count = Arrays.stream(WHILTELISTED_URIS).filter((uri-> uri.equals(requestUri))).count();
        if(count > 0){
            logger.trace("[WHITELISTED URI] >> Skipping Security Check for uri: {} ",requestUri);
            return false;
        }
        return true;
    }
}

