package io.itpl.microservice.api;

import com.google.common.primitives.Longs;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class RecentApiCall implements Delayed {
    public static final int SAFE_DURATION = 1000;
    private String clientIpAddress;
    private String uri;
    private long expiry;

    public static RecentApiCall getInstance(String clientIpAddress, String uri){
        RecentApiCall call = new RecentApiCall();
        call.setClientIpAddress(clientIpAddress);
        call.setUri(uri);
        call.setExpiry(System.currentTimeMillis() + SAFE_DURATION);
        return call;
    }
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expiry - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Longs.compare(expiry, ((RecentApiCall) o).expiry);
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    public long getExpiry() {
        return expiry;
    }

    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
