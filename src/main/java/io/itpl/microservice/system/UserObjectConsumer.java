package io.itpl.microservice.system;

import io.itpl.microservice.common.UserObject;
import io.itpl.microservice.pojo.UserProfile;

public interface UserObjectConsumer {

    void consumeUserObject(UserObject userObject, UserProfile userProfile);
}
