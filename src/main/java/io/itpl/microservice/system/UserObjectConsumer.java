package io.itpl.microservice.system;

import io.itpl.microservice.common.UserObject;

public interface UserObjectConsumer {

    void consumeUserObject(UserObject userObject);
}
