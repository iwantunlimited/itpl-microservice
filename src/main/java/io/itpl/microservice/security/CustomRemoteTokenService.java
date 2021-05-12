package io.itpl.microservice.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

public class CustomRemoteTokenService implements ResourceServerTokenServices {

	static Logger logger = LoggerFactory.getLogger(CustomRemoteTokenService.class);
    private RestOperations restTemplate;
    
    
    @Value("${com.iwant.oauth2.authServerHost}")
    private String authServerHost;
    
    @Value("${com.iwant.oauth2.authServerPort}")
    private String authServerPort;
    
    @Value("${com.iwant.oauth2.http.scheme}")
    private String httpScheme;
    
    @Value("${com.iwant.oauth2.tokenUri}")
    private String tokenUri;
    
    @Value("${com.iwant.oauth2.username}")
    private String username;
    
    @Value("${com.iwant.oauth2.password}")
    private String password;
    
    private String tokenValidationUrl;
    
    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

    @Autowired
    public CustomRemoteTokenService() {
    	
        restTemplate = new RestTemplate();
        ((RestTemplate) restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            // Ignore 400
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400) {
                    super.handleError(response);
                }
            }
        });
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
    	this.tokenValidationUrl = this.httpScheme+"://"+this.authServerHost+":"+this.authServerPort+"/"+this.tokenUri;
        logger.trace(String.format(tokenValidationUrl,accessToken));

        HttpHeaders headers = new HttpHeaders();

        Map<String, Object> map = executeGet(String.format(tokenValidationUrl,accessToken), headers);
        if (map == null || map.isEmpty() || map.get("error") != null) {
            throw new InvalidTokenException("Token not allowed");
        }
        return tokenConverter.extractAuthentication(map);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }

    private Map<String, Object> executeGet(String path, HttpHeaders headers) {
        try {
        	headers.setBasicAuth(this.username,this.password);
            if (headers.getContentType() == null) {
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            }
            @SuppressWarnings("rawtypes")
            Map map = restTemplate.exchange(path, HttpMethod.GET, new HttpEntity<MultiValueMap<String, String>>(null, headers), Map.class).getBody();
            @SuppressWarnings("unchecked")
            Map<String, Object> result = map;
            return result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

}
