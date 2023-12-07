package io.itpl.microservice.system;

import io.itpl.microservice.LoggedInUser;

public interface LoggedInUserConsumer{

    void consumeLoggedInUser(LoggedInUser loggedInUser);
}
