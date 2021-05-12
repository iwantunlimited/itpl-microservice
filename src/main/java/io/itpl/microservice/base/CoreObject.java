package io.itpl.microservice.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;


public class CoreObject {
	@Autowired
	MessageSource messageSource;
	
	
	
	public String message(String code, Locale locale) {
		try {
			String errorMessage = messageSource.getMessage(code, null, locale==null?Locale.getDefault():locale);
			return errorMessage;
		}catch(Exception ex) {
			return "Something went wrong, message not available for ["+code+"]";
		}
	}
	public String message(String code) {
		try {
			String errorMessage = messageSource.getMessage(code, null,Locale.getDefault());
			return errorMessage;
		}catch(Exception ex) {
			return "Something went wrong, message not available for ["+code+"]";
		}
	}
}
