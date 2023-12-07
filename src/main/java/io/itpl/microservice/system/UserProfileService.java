package io.itpl.microservice.system;


import io.itpl.microservice.api.ApiResponse;
import io.itpl.microservice.pojo.UserProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "${feign.userProfileService}", url = "${feign.userProfileServiceUrl}")
public interface UserProfileService {

    @GetMapping("/api/profile/userProfileFindById/{id}")
    UserProfile userProfileById(@PathVariable("id") String id);

    @GetMapping("/remote/myBlock/list/{id}")
    public List<String> myBlockedUsersList(@PathVariable("id") String id);

    @GetMapping("/remote/block/list/{id}")
    List<String> blockedUserList(@PathVariable("id") String id);

    @GetMapping("/remote/lastSeen/{id}")
    void updateLastSeen(@PathVariable("id") String id);

    @GetMapping("/remote/userStatus/{id}/{status}")
    void updateStatus(@PathVariable("id") String userOnlineId, @PathVariable("status") boolean status);

    @GetMapping("/remote/lastOffline/{id}")
    void updateLastOffline(@PathVariable("id") String id);

    @GetMapping("/api/remote/profile/findBySystemUserId/{id}")
    UserProfile getBySystemUserId(@PathVariable("id") String id);

    @GetMapping("/update-mem-status/{id}/{status}")
    void updateMemStatus(@PathVariable("id") String id, @PathVariable("status") boolean status);
}
