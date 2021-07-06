package io.itpl.microservice.utils;

import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.List;

@Component
public class ResourceHelper extends MongoExecutorService {
    private static final Logger logger = LoggerFactory.getLogger(ResourceHelper.class);

    @Autowired
    ObjectMapper objectMapper;

    public <T> List<T> loadSystemResource(String resourceName, Class<T> model) throws IOException {
        String tag = "Load-System-Resource";
        Resource resource = new ClassPathResource(resourceName);
        File file = resource.getFile();
        boolean fileExists = file.exists();
        long size = file.length();
        logger.trace("[{}] loadSystemDefault : {}, exists:{}, size:{}",tag,resourceName,fileExists,size);
        byte[] chunk = new byte[(int)size];
        InputStream stream = resource.getInputStream();
        stream.read(chunk);
        ByteArrayInputStream byteStream = new ByteArrayInputStream(chunk);
        List<T> elements = objectMapper.readValue(chunk, new TypeReference<List<T>>() {
        });
        if(missing(elements)){
            logger.trace("[{}] Finished Reading: {}, Relay-Config elements not found.",tag,resourceName);
        }
        int count = elements.size();
        logger.trace("[{}] Finished Reading: {}, found total [{}] items",tag,resourceName,count);
        return elements;
    }
}
