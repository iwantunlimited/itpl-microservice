package io.itpl.microservice.api;

public interface ApiHeaders {

    public static final String ITPL_APP_NAME = "itpl-app-name";
    public static final String ITPL_APP_VERSION = "itpl-app-version";
    public static final String ITPL_APP_TYPE = "itpl-app-type"; // Mobile, Web, STB etc.
    public static final String ITPL_APP_DEVICEOS = "itpl-app-os"; // Android
    public static final String ITPL_APP_DEVICEMAC = "itpl-app-device-mac"; // IMEI or Unique Identifier
    public static final String ITPL_APP_CLIENTID = "itpl-client-id"; // IMEI or Unique Identifier
    public static final String ITPL_TRANSACTION_ID = "itpl-transaction-id"; // Unique Transaction Identifier.
    public static final String ITPL_SIGNATURE_KEY = "itpl-signature-key"; // Signature key Generated by the Client.


}
