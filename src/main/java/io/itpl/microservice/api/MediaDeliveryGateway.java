package io.itpl.microservice.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;
import io.itpl.microservice.LoggedInUser;
import io.itpl.microservice.utils.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class MediaDeliveryGateway extends DefaultApiGateway {
    private static final Logger logger = LoggerFactory.getLogger(MediaDeliveryGateway.class);

    /**
     * This method is entry point for the Bulk File Upload Operation.
     * @param httpReq
     * @param file
     * @return
     */
    @Async
    public CompletableFuture<ApiResponse> executeTask(HttpServletRequest httpReq, MultipartFile file){
        logger.info("Processing file:"+file.getName()+"("+file.getOriginalFilename()+")");

        ApiResponse response = execute(httpReq,file);
        logger.info("Finished Processing file:"+file.getName()+"("+file.getOriginalFilename()+")");
        return CompletableFuture.completedFuture(response);
    }

    /**
     *  Signature C :
     *  HTTP/POST with MultipartFile (http --> form-data)
     *
     * @param httpRequest
     * @param file
     * @return
     */
    public ApiResponse execute(HttpServletRequest httpRequest,MultipartFile file) {
        logger.trace("API Request received with Multipart File");
        try {
            LoggedInUser currentUser = read(httpRequest);
            if(currentUser==null){
                return ApiResponse.error("ERROR! Full Authentication is needed in order to access.");
            }
            String systemUserId = currentUser.getId();
            /**
             *  Lets Attack the HttpRequest now and capture all input parameters.
             *  Remember : This is HTTP/POST with "form-data" and not a JSON BODY.
             */
            String actionCode = httpRequest.getParameter("actionCode");
            if(actionCode == null) {
                return ApiResponse.error("Can't Process request without <actionCode>");
            }
            String transactionId = httpRequest.getParameter("transactionId");
            String signatureKey = httpRequest.getParameter("signatureKey");
            /**
             *  Warning : Property "domain" is deprecated since Jan-2021.
             *  Although keeping implementation just for backward compatibility of existing Applications.
             */
            String domain = httpRequest.getParameter("domain");
            /**
             *  Parameter for DirectoryID under whcih the File will be uploaded.
             */
            String directory = httpRequest.getParameter("directoryId");
            if(directory == null) {
                directory = httpRequest.getParameter("directory");
            }
            String tags = httpRequest.getParameter("tags");
            // Expected input format as below:
            // tags : "value1,value2,value3"
            List<String> tagsList = new ArrayList<String>();
            if(tags != null && !tags.isEmpty()) {

                if(tags.contains(",")) {
                    String tokens[] = tags.split(",");
                    for(String token:tokens)
                        tagsList.add(token.toLowerCase());
                }else {
                    tagsList.add(tags.toLowerCase());
                }

            }
            String width = httpRequest.getParameter("width");
            String height = httpRequest.getParameter("height");
            String age = httpRequest.getParameter("age");
            /**
             *  Its now time to look at the Attachment File.
             */
            String contentType = file.getContentType();
            String name = file!=null?file.getName():(httpRequest.getParameter("name"));
            byte[] data = file!=null?file.getBytes(): new byte[] {};
            if(data == null || data.length < 1) { // 99.99% This will not be a Case.
                return ApiResponse.error("Can't process empty or invalid file");
            }
            String originalFileName = file!=null?file.getOriginalFilename():(httpRequest.getParameter("name"));
            /**
             *  Building "owner" Object based on LoggedInUser.
             */
            ResourceOwner owner = new ResourceOwner();
            owner.setDomain(Strings.isNullOrEmpty(domain)? currentUser.getDomain() : domain);
            owner.setId(systemUserId);
            owner.setMobile(currentUser.getMobile());
            owner.setEmail(currentUser.getEmail());
            owner.setName(currentUser.getFirstName() + " " + currentUser.getLastName());
            // Create a Request Body wrapped in UserFile
            UserFile userFile = new UserFile();
            userFile.setDomain(domain);
            userFile.setOwner(owner);
            userFile.setContent(data);
            userFile.setFileName(originalFileName.toLowerCase());
            userFile.setMimeType(contentType);
            userFile.setSize(data.length);
            userFile.setDirectoryId(directory);
            userFile.setTags(tagsList);
            if(!Strings.isNullOrEmpty(age)) {
                if(CommonHelper.isInteger(age)) {
                    userFile.setAge(Integer.parseInt(age));
                }
            }
            /**
             *  We also allow user to resize the image before Upload.
             *  Lets Build the ResourceFilter Object for the same.
             */
            if(width != null || height != null) {
                ResourceFilter requiredSize = new ResourceFilter();
                boolean bApplyFilter = false;
                if(CommonHelper.isInteger(width)) {
                    requiredSize.setWidth(Integer.valueOf(width));
                    bApplyFilter = true;
                }
                if(CommonHelper.isInteger(height)) {
                    requiredSize.setHeight(Integer.valueOf(height));
                    bApplyFilter = true;
                }
                if(bApplyFilter) {
                    userFile.setRequiredSize(requiredSize);
                }
            }
            /**
             *  We are ready to Go, Lets Wrap up Everything Under FileUploadRequest.
             */
            FileUploadBody req =  new FileUploadBody();
            req.setActionCode(actionCode);
            req.setRequestBody(userFile);
            JsonNode body = objectMapper.convertValue(req, JsonNode.class);
            Map<String,String> pathVariables = new HashMap<>();
            pathVariables.put("originalFileName",originalFileName);
            return execute(httpRequest,body,pathVariables);

        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * Very Specific to the CDN Service. It Returns a Native Response for the Media.
     * @param httpRequest
     * @param id
     * @return
     */
    public ResponseEntity<byte[]> get(HttpServletRequest httpRequest, String id) {
        String actionCode = httpRequest.getParameter("actionCode");
        if(actionCode==null) {
            actionCode = "ACTION_GET_RESOURCE";
        }
        String width = httpRequest.getParameter("width");
        String height = httpRequest.getParameter("height");

        ResourceFilter filter = new ResourceFilter();
        if(width!=null && CommonHelper.isInteger(width)){
            filter.setWidth(Integer.parseInt(width));
        }
        if(height!=null && CommonHelper.isInteger(height)){
            filter.setHeight(Integer.parseInt(height));
        }
        logger.info(filter.toString());
        GetResourceBody req = new GetResourceBody();
        req.setRequestBody(filter);
        req.setActionCode(actionCode);

        JsonNode body = objectMapper.convertValue(req, JsonNode.class);
        try {
            logger.info("1:-"+objectMapper.writeValueAsString(body));
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Map<String,String> pathVariables = new HashMap<>();
        pathVariables.put("id",id);
        ApiResponse response = this.execute(httpRequest, body, pathVariables);
        JsonNode jsonBody = (JsonNode)response.getData();
        UserFile file = objectMapper.convertValue(jsonBody, UserFile.class);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE,file.getMimeType());
        responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, file.getFileName());
        responseHeaders.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.getContent().length));

        return new ResponseEntity<byte[]>(file.getContent(), responseHeaders, HttpStatus.OK) ;
    }
}
