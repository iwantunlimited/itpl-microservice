package io.itpl.microservice.api;

public interface RestApiGateway {

    public static final String[] WHITELISTED_ACTIONS = {
      "ACTION_GET_RESOURCE"
    };

    public static final String[] WHITELISTED_HOSTS = {
            "localhost",
            "127.0.0.1",
            "192.168.1.93"
    };
    public static final String [] WHILTELISTED_URIS = {

    };
    public static final int TID_VALIDITY = 60000;
}
