package io.itpl.microservice.base;

import java.util.Date;

public interface Auditable {

    public void setCreatedOn(Date createdOn);
    public void setLastUpdatedOn(Date lastUpdatedOn);
    public void setCreatedBy(String createdBy);
    public void setLastUpdatedBy(String lastUpdatedBy);
    public void setDomain(String domain);
    public void setDeviceInfo(String deviceInfo);
    public void setIpAddress(String ipAddress);
    public void setRemark(String remark);
    public void setAppName(String appName);
    public void setAppVersion(String appVersion);
    public void setAppType(String appType);
    public void setOsName(String osName);
    public void setDeviceMac(String deviceMac);
    public void setDomainDetected(boolean domainDetected);
    public void setTokenDetected(boolean tokenDetected);
    public void setRealm(String realm);

}
