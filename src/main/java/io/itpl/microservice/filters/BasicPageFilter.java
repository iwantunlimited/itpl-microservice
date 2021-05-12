package io.itpl.microservice.filters;

import com.google.common.base.Strings;
import io.itpl.microservice.utils.CommonHelper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

public class BasicPageFilter implements PageBuilder, QueryBuilder {

    protected int page;
    protected int pageSize;
    protected String sortBy;
    protected String fields;



    @Override
    public Query toQuery() {
        Query query = new Query();

        if(!Strings.isNullOrEmpty(sortBy)) {
            query.with(CommonHelper.sortBy(sortBy));
        }
        return query;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
    @Override
    public Pageable initPage() {

        if(pageSize <=0 || pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        return PageRequest.of(page, pageSize);
    }
}
