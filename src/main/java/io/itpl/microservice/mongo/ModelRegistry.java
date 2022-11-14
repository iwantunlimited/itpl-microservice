package io.itpl.microservice.mongo;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ModelRegistry {
    Logger logger = LoggerFactory.getLogger(ModelRegistry.class);

    private List<String> packages = new ArrayList<>();

    private Map<String,Class> registry = new HashMap<>();


    public Class load(String className){
        logger.trace("[Model-Class-Loader] Query:{}, exists:{}",className,registry.containsKey(className));
        return registry.get(className);
    }
    public void execute(){
        if(this.packages.isEmpty()){
            logger.error("Nothing to Scan. Package List is empty");
            return;
        }
        packages.forEach(packageName->scan(packageName));
    }
    public void addToScanList(String packageName){
        packages.add(packageName);
    }

    public void scan(String packageName){
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(packageName));

        Set<Class<?>> allClasses =
                reflections.getTypesAnnotatedWith(Document.class);
        Iterator<Class<? extends Object>> results = allClasses.iterator();
        logger.trace("[{}] model classed discovered in package [{}]",allClasses.size(),packageName);
        while(results.hasNext()) {
            Class<? extends Object> current = results.next();
            String className = current.getName();
            int index = className.lastIndexOf(".");
            String name = className.substring(index+1);
            registry.put(name, current);
            /*
            Field[] fields = current.getDeclaredFields();
            if(fields!= null){
                for(Field field:fields){
                    logger.trace("[{}] Field:{},{},{}",name,field.getName(),field.getType(),field.getGenericType());
                }
            }
            */
            logger.trace("[Scanning for Model Classes] added :<"+name+">");
        }
    }
    public List<String> getClassNames(){
        List<String> classNames = new ArrayList<>();
        this.registry.keySet().forEach(item->classNames.add(item));
        Collections.sort(classNames);
        return classNames;
    }
}
