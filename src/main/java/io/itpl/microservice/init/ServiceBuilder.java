package io.itpl.microservice.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.itpl.microservice.mongo.analytics.DefaultAnalyticsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ServiceBuilder {
    private static final Logger logger = LoggerFactory.getLogger(ServiceBuilder.class);
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    DefaultAnalyticsBuilder analyticsBuilder;

    public void execute()  {

        try{
            analyticsBuilder.createDefaultAnalytics();
        }catch (IOException e){
            logger.warn("[analyticsBuilder]-{}",e.getMessage());
        }

    }


}