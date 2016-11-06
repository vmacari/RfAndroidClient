package demo.sensors.md.shouse_sensors.models;

import java.util.Date;

/**
 * Created by elisita on 11/5/16.
 */

public class Node {
    private String name;
    private String version;
    private Date lastUpdateTime;
    private String fwVersion;
    private Short batteryLevel;
    private String configuration;
    private Boolean isRebooting;
    private String protocol;

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public String getFwVersion() {
        return fwVersion;
    }

    public Short getBatteryLevel() {
        return batteryLevel;
    }

    public String getConfiguration() {
        return configuration;
    }

    public Boolean getRebooting() {
        return isRebooting;
    }

    public String getProtocol() {
        return protocol;
    }
}
