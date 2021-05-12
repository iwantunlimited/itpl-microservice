package io.itpl.microservice.system;


import com.google.common.base.Strings;
import io.itpl.microservice.filters.DefaultFilter;
import io.itpl.microservice.utils.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class SystemUserFilter extends DefaultFilter {
    private static final Logger logger = LoggerFactory.getLogger(SystemUserFilter.class);

    private String id;
    private String mobile;
    private String email;
    private String userType;
    private String username;
    
    
    public Query toQuery(){
        Query query = super.toQuery();
        if(null!= id){
            logger.info("id is comeing : {}" , id);
            query.addCriteria(Criteria.where("_id").is(id));
        }
        if(null!=mobile){
            query.addCriteria(Criteria.where("mobile").is(mobile));
        }
        
        if(!Strings.isNullOrEmpty(email)) {
        	query.addCriteria(Criteria.where("email").is(email));
        }
        if(!Strings.isNullOrEmpty(username)) {
        	query.addCriteria(Criteria.where("username").is(username));
        }
        if(!Strings.isNullOrEmpty(userType)) {
        	query.addCriteria(Criteria.where("userType").is(Integer.valueOf(userType)));
        }
        
        
        if(!CommonHelper.isNull(sortBy)) {
			query.with(CommonHelper.sortBy(sortBy));
		}
        return query;
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

        @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public void setDomain(String domain) {
        this.domain = domain;
    }

	


    @Override
    public String toString() {
        return "UserFilter{" +
                "id='" + id + '\'' +
                ", mobile='" + mobile ;
                
    }

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
