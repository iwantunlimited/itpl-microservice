package io.itpl.microservice.mongo.analytics.api;

import io.itpl.microservice.exceptions.ItemNotFoundException;
import io.itpl.microservice.mongo.analytics.AnalyticsElement;
import io.itpl.microservice.mongo.analytics.AnalyticsElementFilter;
import io.itpl.microservice.mongo.analytics.AnalyticsExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnalyticsController {
    private static final Logger logger = LoggerFactory.getLogger(AnalyticsController.class);
    @Autowired
    AnalyticsExecutorService service;

    //1
    @PostMapping("/remote/analytics")
    public AnalyticsElement create(@RequestBody AnalyticsElement req){

        return service.create(req);
    }
    //2
    @PutMapping("/remote/analytics")
    public AnalyticsElement update(@RequestBody AnalyticsElement req){
        return service.update(req);
    }
    //3
    @PostMapping("/remote/analytics/findByElement")
    public AnalyticsElement findByElement(@RequestBody AnalyticsElement req){
        String id = req.getId();
        try {
            return service.findById(id);
        } catch (ItemNotFoundException e) {
            return null;
        }
    }
    //4
    @GetMapping("/remote/analytics/findById/{id}")
    public AnalyticsElement findById(@PathVariable("id") String id){
        try {
            return service.findById(id);
        } catch (ItemNotFoundException e) {
            return null;
        }
    }
    //5
    @PostMapping("/remote/analytics/execute-element")
    public AnalyticsElement execute(@RequestBody AnalyticsElement req){
        String id = req.getId();
        String domain = req.getDomain();
        String timeZoneId = req.getTimeZoneId();
        int page = req.getPage();
        int pageSize = req.getPageSize();
        try {
            return service.execute(id,domain,timeZoneId,pageSize,page);
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
            req.setError(true);
            req.setErrorMessage(e.getMessage());
            return req;
        }
    }
    //6
    @GetMapping("/remote/analytics/execute/{domain}/{id}")
    public AnalyticsElement execute(@PathVariable("domain") String domain,@PathVariable("id") String id){
        try {
            return service.execute(id,domain,null,0,0);
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
            AnalyticsElement element = new AnalyticsElement();
            element.setId(id);
            element.setError(true);
            element.setErrorMessage(e.getMessage());
            return element;
        }
    }
    //7
    @GetMapping("/remote/analytics/findAll/{domain}")
    public List<AnalyticsElement> findAll(@PathVariable("domain") String domain){
        return service.findAll(domain);
    }
    //8
    @GetMapping("/remote/analytics/findAllActive/{domain}")
    public List<AnalyticsElement> findAllActive(@PathVariable("domain") String domain){
        return service.findAllActive(domain);
    }
    //9
    @GetMapping("/remote/analytics/findByClass/{domain}/{class}")
    public List<AnalyticsElement> findByClassName(@PathVariable("domain") String domain,@PathVariable("class") String className){
        return service.findAllByClassName(domain,className);
    }
    //10
    @DeleteMapping("/remote/analytics/delete/{id}")
    public AnalyticsElement delete(@PathVariable("id") String id) throws ItemNotFoundException {
        return service.delete(id);
    }
    //11
    @PostMapping("/remote/analytics/delete-element")
    public AnalyticsElement delete(@RequestBody AnalyticsElement req) throws ItemNotFoundException {
        String id = req.getId();
        return service.delete(id);
    }
    //12
    @PostMapping("/remote/analytics/filter")
    public Page<AnalyticsElement> filter(@RequestBody AnalyticsElementFilter filter){
        logger.trace("[Filter-Analytics] Executing ...");
        Page<AnalyticsElement> page = service.filter(filter);
        logger.trace("[Filter-Analytics] Returning : empty:{}, size:{}",page.isEmpty(),page.getTotalElements());
        return page;
    }

    @GetMapping("/remote/analytics/class-names/{id}")
    public List<String> getClassNames(@PathVariable("id") String serviceId){
        return service.getClassNames();
    }

}
