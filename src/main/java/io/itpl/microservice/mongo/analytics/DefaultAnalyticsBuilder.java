package io.itpl.microservice.mongo.analytics;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.itpl.microservice.exceptions.ItemNotFoundException;
import io.itpl.microservice.mongo.MongoExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class DefaultAnalyticsBuilder extends MongoExecutorService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultAnalyticsBuilder.class);
    public static final String resourceName = "init-widgets.json";
    public static final String tag = "LOAD-WIDGETS";
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AnalyticsExecutorService service;

    public void createDefaultAnalytics() throws IOException {
        Resource resource = new ClassPathResource(resourceName);
        if(!resource.exists()){
            logger.error("[{}]Resource :{} does not exists.",tag,resourceName);
        }else{
            logger.trace("[{}]Resource :{} exists. {}",tag,resourceName,resource.getURI());
        }
        InputStream stream = resource.getInputStream();
        int size = stream.available();
        logger.trace("[{}] Opening Stream. Size:{} bytes",tag,size);
        byte[] chunk = new byte[size];
        stream.read(chunk);
        ByteArrayInputStream byteStream = new ByteArrayInputStream(chunk);
        List<AnalyticsElement> elements = objectMapper.readValue(chunk, new TypeReference<List<AnalyticsElement>>() {
        });

        if(exists(elements)){
            logger.trace("[{}] Analytics Elements Found in init-widgets.json",elements.size());
            elements.forEach(element->{
                element.validate();
                element.setSystemGenerated(true);
                String alias = element.getAlias();
                try {
                    AnalyticsElement existing = service.findByAlias(alias);
                    logger.trace("[{}] Analytics Element Already Exists.",alias);
                } catch (ItemNotFoundException e) {
                    AnalyticsElement newElement = service.create(element);
                    logger.trace("[{}] Created New Default Analytics Element [{}].",alias,newElement.getId());
                }
            });
        }else{
            logger.trace("[ NULL ] Analytics Elements Not Found in init-widgets.json");
        }
    }
}
