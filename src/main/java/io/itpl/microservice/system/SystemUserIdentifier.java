package io.itpl.microservice.system;

public class SystemUserIdentifier {

	private String identifier;
	private int userType;
	private String domain;
	private String id;
	private String propertyName;
	private String value;
	private String otp;
	private String otpId;
	private boolean validateOtp;
	private String mcc;
	private String mobile;
	private String email;
	
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
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
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public boolean isValidateOtp() {
		return validateOtp;
	}
	public void setValidateOtp(boolean validateOtp) {
		this.validateOtp = validateOtp;
	}
	public String getOtpId() {
		return otpId;
	}
	public void setOtpId(String otpId) {
		this.otpId = otpId;
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
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	@Override
	public String toString() {
		return "SystemUserIdentifier [identifier=" + identifier + ", userType=" + userType + ", domain=" + domain
				+ ", id=" + id + ", propertyName=" + propertyName + ", value=" + value + ", otp=" + otp + ", otpId="
				+ otpId + ", validateOtp=" + validateOtp + ", mcc=" + mcc + ", mobile=" + mobile + ", email=" + email
				+ "]";
	}
	
	
}
