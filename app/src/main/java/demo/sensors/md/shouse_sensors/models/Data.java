package demo.sensors.md.shouse_sensors.models;

import java.util.Date;

/**
 * Created by elisita on 11/5/16.
 */

public class Data {
    private int id;
    private Integer nodeId;
    private Integer sensorId;
    private String dataType;
    private String data;
    private Date time;

    public int getId() {
        return id;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public Integer getSensorId() {
        return sensorId;
    }

    public String getDataType() {
        return dataType;
    }

    public String getData() {
        return data;
    }

    public Date getTime() {
        return time;
    }
}
