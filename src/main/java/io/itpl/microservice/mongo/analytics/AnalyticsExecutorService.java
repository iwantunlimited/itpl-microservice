package io.itpl.microservice.mongo.analytics;

import com.google.common.base.Strings;
import io.itpl.microservice.exceptions.ItemNotFoundException;
import io.itpl.microservice.filters.DefaultPage;
import io.itpl.microservice.mongo.ModelRegistry;
import io.itpl.microservice.mongo.MongoExecutorService;
import io.itpl.microservice.mongo.document.AggregationDocument;
import io.itpl.microservice.mongo.executor.CrossTabExecutor;
import io.itpl.microservice.mongo.executor.TabularExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Component
public class AnalyticsExecutorService extends MongoExecutorService {

    @Autowired
    CrossTabExecutor executor;

    @Autowired
    TabularExecutor reportExecutor;

    @Autowired
    ModelRegistry modelRegistry;

    public AnalyticsElement create(AnalyticsElement req){

        req.validate();
        req.setCreatedOn(new Date());
        return mongoService.insert(req);
    }
    public AnalyticsElement update(AnalyticsElement req){
        req.validate();
        if(req.isSystemGenerated()){
            return req;
        }
        return mongoService.save(req);
    }
    public AnalyticsElement findById(String id) throws ItemNotFoundException {
        if(missing(id)){
            returnInputError("Missing _id");
        }
        AnalyticsElement element = findOne("Analytics-findById",_id(id),AnalyticsElement.class);
        if(element==null){
            returnNotFoundError("[Analytics-Element:"+id+"]");
        }
        return element;
    }
    public List<AnalyticsElement> findAllActive(String domain){
        Query query = new Query(Criteria.where("domain").is(domain));
        query.addCriteria(Criteria.where("deleted").is(false));
        query.addCriteria(Criteria.where("inactive").is(false));
        return find("findAllActive",query,AnalyticsElement.class);
    }
    public List<AnalyticsElement> findAll(String domain){
        Query query = new Query(Criteria.where("domain").is(domain));
        query.addCriteria(Criteria.where("deleted").is(false));
        return find("findAll",query,AnalyticsElement.class);
    }
    public List<AnalyticsElement> findAllByClassName(String domain,String className){
        Query query = new Query(Criteria.where("domain").is(domain));
        query.addCriteria(Criteria.where("deleted").is(false));
        query.addCriteria(Criteria.where("className").is(className));
        return find("findAll",query,AnalyticsElement.class);
    }
    public DefaultPage<AnalyticsElement> filter(AnalyticsElementFilter filter){
        return findByDefaultPage("filter",filter.toQuery(),AnalyticsElement.class,filter.getPageSize(),filter.getPage());
    }

    public AnalyticsElement delete(String id) throws ItemNotFoundException {
        AnalyticsElement element = findById(id);
        if(element.isSystemGenerated()){
            return element;
        }
        element.setDeleted(true);
        element.setInactive(true);
        return mongoService.save(element);
    }
    public AnalyticsElement inactivate(String id) throws ItemNotFoundException {
        AnalyticsElement element = findById(id);
        element.setInactive(true);
        return mongoService.save(element);
    }
    public AnalyticsElement activate(String id) throws ItemNotFoundException {
        AnalyticsElement element = findById(id);
        element.setInactive(false);
        return mongoService.save(element);
    }

    /**
     *
     * @param id
     * @param domain
     * @param timeZoneId
     * @param pageSize
     * @param page
     * @return
     * @throws ItemNotFoundException
     */
    public AnalyticsElement execute(String id,String domain,String timeZoneId, int pageSize,int page) throws ItemNotFoundException {
        if(pageSize == 0){
            pageSize = AggregationDocument.MAX_PAGE_SIZE;
        }
        if(Strings.isNullOrEmpty(timeZoneId)){
            timeZoneId = TimeZone.getDefault().getID();
        }
        AnalyticsElement element = findById(id);
        element.setDomain(domain);
        element.setTimeZoneId(timeZoneId);
        element.setPage(page);
        element.setPageSize(pageSize);
        if(element.isReport()){
            return reportExecutor.execute(element);
        }else {
            return executor.execute(element);
        }
    }
    public AnalyticsElement findByAlias(String alias) throws ItemNotFoundException {
        Query query = new Query(Criteria.where("alias").is(alias));
        query.addCriteria(Criteria.where("systemGenerated").is(true));
        AnalyticsElement existing = findOne("findByAlias",query,AnalyticsElement.class);
        if(existing==null){
            throw new ItemNotFoundException("[AnalyticsElement-"+alias+"]");
        }else{
            return existing;
        }
    }
    public List<String> getClassNames(){
        return modelRegistry.getClassNames();
    }
}
