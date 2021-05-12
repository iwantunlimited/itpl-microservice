package io.itpl.microservice;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;


@Component
public class ServiceContext {

private static final Logger logger = LoggerFactory.getLogger(ServiceContext.class);
	
	private ApplicationContext context;
	private String actionPackage;
	
	@Autowired
	private ActionRegistry actionRegistry;
	
	
	public ServiceContext()  {
		
		logger.info("Initialized Service Context");
		
	}
	
	public ApplicationContext getApplicationContext() {
		return context;
	}
	public void setApplicationContext(ApplicationContext context) {
		this.context = context;
	}
	public ActionRegistry getActionRegistry() {
		return actionRegistry;
	}
	
	public void discoverActionRegistry() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		 Reflections reflections = new Reflections(actionPackage);

		 Set<Class<? extends AbstractAction>> allClasses =
		     reflections.getSubTypesOf(AbstractAction.class);
		 Iterator<Class<? extends AbstractAction>> results = allClasses.iterator();
		 while(results.hasNext()) {
			 Class<? extends AbstractAction> current = results.next();
			 Field fieldModuleId = current.getField("MODULE_ID");
			 String moduleId = (String) fieldModuleId.get(null);
			 String className = current.getName();
			 actionRegistry.register(moduleId, className);
			 logger.trace("Registered Module:<"+moduleId+">:<"+className+">");
		 }
	}

	public String getActionPackage() {
			
		return actionPackage;
	}

	public void setActionPackage(String actionPackage) {
		this.actionPackage = actionPackage;
	}

}
