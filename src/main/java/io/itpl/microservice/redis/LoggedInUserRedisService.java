package io.itpl.microservice.redis;

import io.itpl.microservice.LoggedInUser;
import io.itpl.microservice.system.SystemService;
import io.itpl.microservice.system.SystemUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoggedInUserRedisService {

    @Qualifier("loggedInUserRedisTemplate")
    @Autowired
    RedisTemplate<String, LoggedInUser> redisTemplate;

    @Autowired
    SystemService systemService;

    public void addLoggedInUserToRedis(String key, LoggedInUser loggedInUser) {
        redisTemplate.opsForValue().set(key, loggedInUser);
        log.info("LoggedInUser added to Redis");
    }

    public LoggedInUser getLoggedInUserFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public LoggedInUser getLoggedInUser(String key) {
        LoggedInUser loggedInUser = getLoggedInUserFromRedis(key);
        if (loggedInUser != null) {
            log.info("LoggedInUser fetched from the redis");
        } else {
            log.info("LoggedInUser is not present in the redis");
            // If not present in Redis, fetch it from MongoDB
            SystemUser systemUser = systemService.findById(key);

            if (systemUser == null) {
                return null;
            }

            loggedInUser = getLoggedInUserFromSystemUser(systemUser);
            addLoggedInUserToRedis(key, loggedInUser);
        }
        return loggedInUser;
    }

    public LoggedInUser getLoggedInUserFromSystemUser(SystemUser systemUser) {
        LoggedInUser loggedInUser = new LoggedInUser();
        loggedInUser.setFirstName(systemUser.getFirstName());
        loggedInUser.setLastName(systemUser.getLastName());
        loggedInUser.setUserName(systemUser.getUsername());
        loggedInUser.setMobile(systemUser.getMobile());
        loggedInUser.setMcc(systemUser.getMcc());
        loggedInUser.setAuthorities(systemUser.getAuthorities().toArray(new String[0]));
        loggedInUser.setExternalReferenceId(systemUser.getExternalReferenceId());
        loggedInUser.setDomain(systemUser.getDomain());
        loggedInUser.setId(systemUser.getId());
        loggedInUser.setUserType(systemUser.getUserType());
        loggedInUser.setEmail(systemUser.getEmail());
        loggedInUser.setDomainSsid(systemUser.getDomainSsid());
        loggedInUser.setLocale(systemUser.getLocaleId());
        loggedInUser.setTimeZoneId(systemUser.getTimeZoneId());
        loggedInUser.setProfileImageUrl(systemUser.getProfileImageUrl());
        loggedInUser.setAvtarImageUrl(systemUser.getAvatarImageUrl());
        loggedInUser.setRealm(systemUser.getRealm());
        return loggedInUser;
    }

    public boolean removeUserFromRedis(String key){
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }
}
