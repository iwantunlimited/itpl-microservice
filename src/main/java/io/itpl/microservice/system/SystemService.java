package io.itpl.microservice.system;


import io.itpl.microservice.api.ApiResponse;
import io.itpl.microservice.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name="${feign.systemService}", url="${feign.systemServiceUrl}",configuration= FeignConfiguration.class)
public interface SystemService {

	@PostMapping("/api/system/broadcast")
	public ApiResponse send(@RequestBody BroadcastMessageBody req);

    @PostMapping("/api/remote/system-user")
    public SystemUser create(@RequestBody SystemUser req);

    @DeleteMapping("/api/remote/users/delete/{id}")
	public SystemUser delete( @PathVariable("id") String id);    

    @PostMapping("/api/remote/users/findByIdentity")
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
    
}
