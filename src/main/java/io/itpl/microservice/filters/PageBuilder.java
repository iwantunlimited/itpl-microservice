package io.itpl.microservice.filters;

import org.springframework.data.domain.Pageable;

public interface PageBuilder {

	public static final int MAX_PAGE_SIZE = 100;
	public Pageable initPage();
}
