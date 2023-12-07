package io.itpl.microservice;

import lombok.*;

import java.io.Serializable;

@ToString
@Data
@NoArgsConstructor
/**
 *  This Class defines a Properties of Logged-In User.
 *  Api-Gateway will parse the Barrier Token and Create Instance of this Class to represent the User Details.
 */
public class LoggedInUser implements Serializable {
    private String lastName;
    private String userName;
    private String mobile;
    private String mcc;
    private String type;
    private String [] authorities;
    private String externalReferenceId;
    private String firstName;
    private String domain;
    private String id;
    private int userType;
    private String email;
    private String domainSsid;
    private String locale;
    private String timeZoneId;
    private String profileImageUrl;
    private String avtarImageUrl;
    private String realm;

    public LoggedInUser(String id) {
        this.id = id;
    }
}
