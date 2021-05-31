package io.itpl.microservice.pojo;

import io.itpl.microservice.common.UserObject;
import org.springframework.data.mongodb.core.index.Indexed;

public class ContactDetail {
    private String mcc;
    @Indexed private String mobile;
    @Indexed private String email;
    private String contactPerson;
    private String designation;
    private String userId;
    private UserObject userObject;

    public static ContactDetail build(UserObject user){
        ContactDetail contact = new ContactDetail();
        contact.setContactPerson(user.getFirstName()+" "+user.getLastName());
        contact.setEmail(user.getEmail());
        contact.setMcc(user.getMcc());
        contact.setMobile(user.getMobile());
        contact.setUserObject(user);
        contact.setUserId(user.getId());
        return contact;
    }


    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserObject getUserObject() {
        return userObject;
    }

    public void setUserObject(UserObject userObject) {
        this.userObject = userObject;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}
