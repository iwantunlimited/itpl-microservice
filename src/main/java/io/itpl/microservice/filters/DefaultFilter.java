package io.itpl.microservice.filters;

import com.google.common.base.Strings;
import io.itpl.microservice.utils.CommonHelper;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collection;


public class DefaultFilter implements PageBuilder, QueryBuilder {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DefaultFilter.class);
	
	protected String domain;
	protected String deleted;
	protected String inactive;
	protected int page;
	protected int pageSize;
	protected String sortBy;
	protected String fields;
	public DefaultFilter() {}
	public DefaultFilter(String domain) {
		this.domain = domain;
	}
	
	@Override
	public Query toQuery() {
		Query query = new Query();

		if(!Strings.isNullOrEmpty(domain)) {
			query.addCriteria(Criteria.where("domain").is(domain));
		}
		if(true){//!Strings.isNullOrEmpty(deleted)) {
			query.addCriteria(Criteria.where("deleted").is(Boolean.valueOf(deleted)));
		}
		if(!Strings.isNullOrEmpty(inactive)) {
			query.addCriteria(Criteria.where("inactive").is(Boolean.valueOf(inactive)));
		}
		if(!Strings.isNullOrEmpty(sortBy)) {
			query.with(CommonHelper.sortBy(sortBy));
		}
		
		return query;
	}
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultFilter other = (DefaultFilter) obj;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		return true;
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
	@Override
	public Pageable initPage() {
		
		if(pageSize <=0 || pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		return PageRequest.of(page, pageSize);
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getInactive() {
		return inactive;
	}

	public void setInactive(String inactive) {
		this.inactive = inactive;
	}

	protected boolean missing(String value){
		return Strings.isNullOrEmpty(value);
	}
	protected boolean exists(String value){
		return !Strings.isNullOrEmpty(value);
	}
	protected boolean exists(Collection value){
		return value!=null && !value.isEmpty();
	}
	protected boolean missing(Collection value){
		return value==null || value.isEmpty();
	}
}
