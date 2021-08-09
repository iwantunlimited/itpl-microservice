package io.itpl.microservice.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;


@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	Logger logger = LoggerFactory.getLogger(ResourceServerConfiguration.class);
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		//resources.resourceId("resource-server-rest-api").authenticationManager(authenticationManagerBean())
		//		.tokenExtractor(new CustomTokenExtractor()); 
		TokenExtractor ext = new BearerTokenExtractor();
		resources.authenticationManager(authenticationManagerBean()).tokenExtractor(ext);
		logger.info("Resource Server Configured");
	}

	@Bean
	public ResourceServerTokenServices tokenService() {
		CustomRemoteTokenService tokenServices = new CustomRemoteTokenService();
		return tokenServices;
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
		authenticationManager.setTokenServices(tokenService());
		return authenticationManager;
	}

	@Override
    public void configure(HttpSecurity http) throws Exception {
		
                http
                .requestMatchers()
                .and()
                .authorizeRequests()
                .antMatchers("/api/**","/iwantcdn/**","/public/**","/swagger-ui.html","/v2/api-docs","/webjars/**").permitAll()
                .antMatchers("/api/secured/**" ).authenticated();
                
                logger.info("Http Security Configured in Resource Server");
    }
}

