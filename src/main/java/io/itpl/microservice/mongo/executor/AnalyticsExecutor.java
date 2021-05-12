package io.itpl.microservice.mongo.executor;

import io.itpl.microservice.exceptions.ItemNotFoundException;
import io.itpl.microservice.mongo.analytics.AnalyticsElement;

public interface AnalyticsExecutor {

    public AnalyticsElement execute(AnalyticsElement req) throws ItemNotFoundException;
    public byte[] export(AnalyticsElement req);
}
