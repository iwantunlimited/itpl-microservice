package io.itpl.microservice.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class FeignConfiguration {

	@Bean
	  public Logger.Level configureLogLevel(){
	    return  Logger.Level.BASIC;
	  }
}
