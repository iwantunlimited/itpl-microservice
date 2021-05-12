package io.itpl.microservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Hashtable;


@Component
public class ActionRegistry {

	private static final Logger logger = LoggerFactory.getLogger(ActionRegistry.class);
	private Hashtable<String,String> actionRegistry;
	public ActionRegistry() {
		actionRegistry = new Hashtable<String,String>();
	}
	
	public void register(String moduleId,String entityClass) {
		actionRegistry.put(moduleId, entityClass);
		logger.info("Registered [ ModuleId:"+moduleId+", Class:"+entityClass +"]");
	}
	public String lookup(String moduleId) {
		logger.info("Looking up for :"+moduleId+" in the Action Registry:"+actionRegistry);
		
		if(actionRegistry.containsKey(moduleId)) {
			return actionRegistry.get(moduleId);
		}else {
			return null;
		}
	}
	public boolean isEmpty() {
		return actionRegistry.isEmpty();
	}
	
}
