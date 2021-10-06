package io.itpl.microservice.system;

import io.itpl.microservice.base.BaseObject;
import io.itpl.microservice.base.ObjectValidator;
import org.springframework.data.annotation.Transient;

import java.util.Date;
import java.util.List;

public class SystemUser extends BaseObject implements ObjectValidator {
	
	@Transient public static final String [] USER_TYPES = {"CUSTOMER","STAFF","MERCHANT","PARTNER","OTHER"};
	public static final int TYPE_CUSTOMER = 0;
	public static final int TYPE_MERCHANT = 2;
	public static final int TYPE_ADMIN = -1;



	private String id;


	private int userType;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private int encryptionType;

	private String mobile;
	private String mcc;
	private String email;
	private String externalReferenceId;

	private boolean use2Fa;
	private String secret;
	private int codeType2FA;

	private boolean forcePasswordChange;

	private Date passwordExpiry;
	private int passwordValidityDays;

	private String timeZoneId;
	private String localeId;
	private String languageName;

	private String region;
	private Date lastLoginOn;
	private List<String> authorities;

	private String otpCode;
	private String accessCode;
	private boolean accessCodeExpired;

	private String domainSsid;
	private String profileImageUrl;
	private String avatarImageUrl;
	private boolean external;
	private String externalUserId;
	private String externalSystemCode;
	private String profileId;
	private String qrCodeImageUrl;
	private String gender;
	private String birthDate;
	private boolean incognito;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getEncryptionType() {
		return encryptionType;
	}
	public void setEncryptionType(int encryptionType) {
		this.encryptionType = encryptionType;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getExternalReferenceId() {
		return externalReferenceId;
	}
	public void setExternalReferenceId(String externalReferenceId) {
		this.externalReferenceId = externalReferenceId;
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}

	public boolean isUse2Fa() {
		return use2Fa;
	}

	public void setUse2Fa(boolean use2Fa) {
		this.use2Fa = use2Fa;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public int getCodeType2FA() {
		return codeType2FA;
	}

	public void setCodeType2FA(int codeType2FA) {
		this.codeType2FA = codeType2FA;
	}

	public boolean isForcePasswordChange() {
		return forcePasswordChange;
	}

	public void setForcePasswordChange(boolean forcePasswordChange) {
		this.forcePasswordChange = forcePasswordChange;
	}

	public Date getPasswordExpiry() {
		return passwordExpiry;
	}

	public void setPasswordExpiry(Date passwordExpiry) {
		this.passwordExpiry = passwordExpiry;
	}

	public int getPasswordValidityDays() {
		return passwordValidityDays;
	}

	public void setPasswordValidityDays(int passwordValidityDays) {
		this.passwordValidityDays = passwordValidityDays;
	}

	public String getTimeZoneId() {
		return timeZoneId;
	}

	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}

	public String getLocaleId() {
		return localeId;
	}

	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Date getLastLoginOn() {
		return lastLoginOn;
	}

	public void setLastLoginOn(Date lastLoginOn) {
		this.lastLoginOn = lastLoginOn;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

	public String getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public boolean isAccessCodeExpired() {
		return accessCodeExpired;
	}

	public void setAccessCodeExpired(boolean accessCodeExpired) {
		this.accessCodeExpired = accessCodeExpired;
	}

	public String getDomainSsid() {
		return domainSsid;
	}

	public void setDomainSsid(String domainSsid) {
		this.domainSsid = domainSsid;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getAvatarImageUrl() {
		return avatarImageUrl;
	}

	public void setAvatarImageUrl(String avatarImageUrl) {
		this.avatarImageUrl = avatarImageUrl;
	}

	public boolean isExternal() {
		return external;
	}

	public void setExternal(boolean external) {
		this.external = external;
	}

	public String getExternalUserId() {
		return externalUserId;
	}

	public void setExternalUserId(String externalUserId) {
		this.externalUserId = externalUserId;
	}

	public String getExternalSystemCode() {
		return externalSystemCode;
	}

	public void setExternalSystemCode(String externalSystemCode) {
		this.externalSystemCode = externalSystemCode;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getQrCodeImageUrl() {
		return qrCodeImageUrl;
	}

	public void setQrCodeImageUrl(String qrCodeImageUrl) {
		this.qrCodeImageUrl = qrCodeImageUrl;
	}
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public boolean isIncognito() {
		return incognito;
	}

	public void setIncognito(boolean incognito) {
		this.incognito = incognito;
	}
}
