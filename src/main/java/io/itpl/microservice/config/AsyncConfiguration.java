package io.itpl.microservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Configuration
@EnableAsync
public class AsyncConfiguration extends AsyncConfigurerSupport{
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncConfiguration.class);
    @Bean (name = "taskExecutor")
    public Executor taskExecutor() {
        LOGGER.debug("Creating Async Task Executor");
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(256);
        executor.setMaxPoolSize(1024);
        executor.setQueueCapacity(128);
        executor.setThreadNamePrefix("Task-");
        executor.initialize();
        return executor;
    }
    private long threadCounter = 0;
    @Override
	public Executor getAsyncExecutor() {
    	
    	//return new DelegatingSecurityContextExecutorService((ExecutorService) taskExecutor());
		//return new DelegatingSecurityContextExecutorService(Executors.newFixedThreadPool(500));
    	return new DelegatingSecurityContextExecutorService(Executors.newFixedThreadPool(500,new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				 Thread t = new Thread(r);
				 t.setName("ResourceTask-"+(threadCounter++));
				return t;
			}
    		
    	}));
	}
}
