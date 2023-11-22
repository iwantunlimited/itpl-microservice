package io.itpl.microservice;

import lombok.ToString;

import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;

@ToString
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

    public String getDomainSsid() {
        return domainSsid;
    }

    public void setDomainSsid(String domainSsid) {
        this.domainSsid = domainSsid;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String[] authorities) {
        this.authorities = authorities;
    }

    public String getExternalReferenceId() {
        return externalReferenceId;
    }

    public void setExternalReferenceId(String externalReferenceId) {
        this.externalReferenceId = externalReferenceId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getAvtarImageUrl() {
        return avtarImageUrl;
    }

    public void setAvtarImageUrl(String avtarImageUrl) {
        this.avtarImageUrl = avtarImageUrl;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }
}
