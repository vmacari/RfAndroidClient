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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        if (id != data.id) return false;
        if (nodeId != null ? !nodeId.equals(data.nodeId) : data.nodeId != null) return false;
        return sensorId != null ? sensorId.equals(data.sensorId) : data.sensorId == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nodeId != null ? nodeId.hashCode() : 0);
        result = 31 * result + (sensorId != null ? sensorId.hashCode() : 0);
        return result;
    }
}
