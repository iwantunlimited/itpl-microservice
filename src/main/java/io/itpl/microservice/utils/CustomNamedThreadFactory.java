package io.itpl.microservice.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@NoArgsConstructor
public class CustomNamedThreadFactory implements ThreadFactory {
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable r) {
        // Create a new thread with a custom name
        return new Thread(r, "CustomThread-" + threadNumber.getAndIncrement());
    }
}