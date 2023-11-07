package io.itpl.microservice.pojo;

import io.itpl.microservice.common.KeyValuePair;
import io.itpl.microservice.common.MediaObject;
import io.itpl.microservice.system.City;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
@Builder
public class UserProfile {
    private String id;
    private String systemUserId;
    private int userType;
    private boolean external;
    private String externalUserId;
    private String externalSystemCode;
    private String firstName;
    private String lastName;
    private String middleName;
    private String username;
    private String businessName;
    private boolean enterprise;
    private String email;
    private String mobile;
    private String mcc;
    private Identity identity;
    private Date lastOnline;
    private Date lastOffline;
    private City city;
    private boolean profileVerified;
    private boolean mobileVerified;
    private boolean emailVerified;
    private boolean cloudMember;
    private String nationality;
    private String locality;
    private String occupation;
    private String designation;
    private String workPhone;
    private String extension;
    private String homePhone;
    private String birthYear;
    private String birthMonth;
    private String birthDate;
    private String birthDay;
    private List<KeyValuePair> demographics;
    private String countryId;
    private String stateId;
    private String countryName;
    private String stateName;
    private String gender;
    private MediaObject audioProfile;
    private MediaObject videoProfile;
    private Person contactPerson;
    private String website;
    private String classification;
    private String createdOnDate;
    private String createdOnMonth;
    private int createdInYear;
    private String timeZoneId;
    private String localeId;
    private String languageName;
    private String password;
    private String avtarUrl;
    private boolean incognito;
    private boolean blocked;
    private String blockedBy;
    private boolean online;
    private GeoLocation location;
    private boolean hide;
    private String address;
    private long totalFeeds;
    private long totalGroups;
    private boolean reported;
    private boolean muteMessage;
    private boolean muteCalls;
    private boolean muteAlert;
    private boolean muteEvents;
}
