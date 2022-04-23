package io.itpl.microservice.base;

import io.itpl.microservice.common.UserObject;

public interface UserObjectConsumer {

    public void executeUpdate(String systemUserId, UserObject updated);
}
