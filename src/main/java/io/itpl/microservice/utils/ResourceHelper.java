package io.itpl.microservice.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class ResourceHelper extends MongoExecutorService {
    private static final Logger logger = LoggerFactory.getLogger(ResourceHelper.class);

    @Autowired
    ObjectMapper objectMapper;

    public <T> List<T> loadSystemResource(String resourceName, Class<T> model) throws IOException {
        String tag = resourceName;
        Resource resource = new ClassPathResource(resourceName);
        if(!resource.exists()){
            logger.error("[{}]Resource :{} does not exists.",tag,resourceName);
        }else{
            logger.trace("[{}]Resource :{} exists. {}",tag,resourceName,resource.getURI());
        }
        InputStream stream = resource.getInputStream();
        int size = stream.available();
        logger.trace("[{}] Opening Stream. Size:{} bytes",tag,size);
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class,model);
        List<T> elements = objectMapper.readValue(stream, type);
        if(missing(elements)){
            logger.trace("[{}] Finished Reading: {}, elements not found.",tag,resourceName);
        }
        int count = elements.size();
        logger.trace("[{}] Finished Reading: {}, found total [{}] items",tag,resourceName,count);
        stream.close();
        return elements;
    }
}
