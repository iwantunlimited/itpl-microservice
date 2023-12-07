package io.itpl.microservice.system;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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

	@Override
	public String toString() {
		return "SystemUserIdentifier [identifier=" + identifier + ", userType=" + userType + ", domain=" + domain
				+ ", id=" + id + ", propertyName=" + propertyName + ", value=" + value + ", otp=" + otp + ", otpId="
				+ otpId + ", validateOtp=" + validateOtp + ", mcc=" + mcc + ", mobile=" + mobile + ", email=" + email
				+ "]";
	}
}
