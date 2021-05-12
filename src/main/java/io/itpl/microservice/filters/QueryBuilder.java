package io.itpl.microservice.filters;

import org.springframework.data.mongodb.core.query.Query;

public interface QueryBuilder {

	public Query toQuery();
}
