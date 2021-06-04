package io.itpl.microservice.system;


import com.fasterxml.jackson.databind.JsonNode;
import io.itpl.microservice.api.ApiResponse;
import io.itpl.microservice.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@FeignClient(name="${feign.systemService}", url="${feign.systemServiceUrl}",configuration= FeignConfiguration.class)
public interface SystemService {

	@PostMapping("/api/system/broadcast")
	public ApiResponse send(@RequestBody BroadcastMessageBody req);


    @PostMapping("/api/remote/users/create")
    ApiResponse createOld(@RequestBody JsonNode body);

    @PostMapping("/api/remote/system-user")
    public SystemUser create(@RequestBody SystemUser req);

    @DeleteMapping("/api/remote/users/delete/{id}")
	public SystemUser delete( @PathVariable("id") String id);

    @PostMapping("/api/remote/system-user/findByIdentity")
	public SystemUser findByIdentity(@RequestBody SystemUserIdentifier body);
    
    @GetMapping("/api/remote/users/findById/{id}")
	public SystemUser findById( @PathVariable("id") String id);
    
    @PostMapping("/api/users/update/identity")
	public SystemUser updateIdentity(@RequestBody SystemUserIdentifier body);

    @PutMapping("/api/remote/users/update")
	public SystemUser update(@RequestBody SystemUser req);
    
    @GetMapping("/api/remote/system/city/{id}")
	public City findCityById(@PathVariable("id") String id);
    
    @GetMapping("/api/remote/system/states/{id}")
	public State findStateById(@PathVariable("id") String id);
    
    @GetMapping("/api/remote/system/country/{id}")
	public Country findCountryById(@PathVariable("id") String id);

    @GetMapping("/api/remote/system/domain/{domain}")
    public SystemDomain findDomainByName(@PathVariable("domain") String domain);

    @PostMapping("/oauth/token")
    public Object login(@RequestHeader("Authorization") String basicAuth, @RequestBody Map<String,String> body);

    @PostMapping("/api/remote/feature/upload")
    public void uploadFeatures(@RequestBody List<Feature> elements);

    @PostMapping("/api/remote/relay-config/upload")
    public void uploadRelayConfig(@RequestBody List<RelayConfig> elements);
    
}
