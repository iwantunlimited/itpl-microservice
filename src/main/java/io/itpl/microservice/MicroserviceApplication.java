package io.itpl.microservice;

import io.itpl.microservice.init.ServiceBuilder;
import io.itpl.microservice.mongo.ModelRegistry;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MicroserviceApplication {

    public static void main(String []args){
        SpringApplication app = new SpringApplication(MicroserviceApplication.class);

        app.setBannerMode(Banner.Mode.CONSOLE);

        ApplicationContext context = app.run(args);
        ModelRegistry registry = context.getBean(ModelRegistry.class);
        registry.addToScanList("io.itpl.microservice.example.model");
        registry.execute();
        ServiceBuilder builder = context.getBean(ServiceBuilder.class);
        builder.execute();
    }
}
