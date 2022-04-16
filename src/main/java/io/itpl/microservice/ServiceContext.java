package io.itpl.microservice;

import io.itpl.microservice.exceptions.ItemNotFoundException;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


@Component
public class ServiceContext {

private static final Logger logger = LoggerFactory.getLogger(ServiceContext.class);
	


	private boolean transactionRelayEnabled;

	@Autowired
	private ActionRegistry actionRegistry;

	@Autowired
	ApplicationContextProvider contextProvider;

	private Map<String,Action> beansRegistry = new HashMap<>();
	
	public void discoverActionRegistry(String actionPackage) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		ApplicationContext context = contextProvider.getApplicationContext();
		 Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(actionPackage));

		 Set<Class<? extends AbstractAction>> allClasses =
		     reflections.getSubTypesOf(AbstractAction.class);
		 Iterator<Class<? extends AbstractAction>> results = allClasses.iterator();
		 logger.trace("[{}] Modules Discovered in package {}",allClasses.size(),actionPackage);
		 while(results.hasNext()) {
			 Class<? extends AbstractAction> current = results.next();
			 Field fieldModuleId = current.getField("MODULE_ID");
			 String moduleId = (String) fieldModuleId.get(null);
			 String className = current.getName();
			 actionRegistry.register(moduleId, className);
			 Action bean = context.getBean(current);
			 beansRegistry.put(moduleId,bean);
			 logger.trace("Registered Module:<"+moduleId+">:<"+className+">");
		 }
	}
	public Action load(String moduleId) throws ItemNotFoundException {
		if(beansRegistry.containsKey(moduleId)){
			return beansRegistry.get(moduleId);
		}else{
			throw new ItemNotFoundException("Bean Not Found :"+moduleId);
		}
	}

	public boolean isTransactionRelayEnabled() {
		return transactionRelayEnabled;
	}

	public void setTransactionRelayEnabled(boolean transactionRelayEnabled) {
		this.transactionRelayEnabled = transactionRelayEnabled;
	}

	public void setActionRegistry(ActionRegistry actionRegistry) {
		this.actionRegistry = actionRegistry;
	}

	public ActionRegistry getActionRegistry() {
		return actionRegistry;
	}
}
