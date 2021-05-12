package io.itpl.microservice.mongo.pipeline;

import org.springframework.data.mongodb.core.aggregation.Aggregation;

public interface PipelineBuilder {

    public Aggregation build();
}
