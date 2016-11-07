package demo.sensors.md.shouse_sensors.models;

/**
 * Created by elisita on 11/5/16.
 */

public class Sensor {
    private int id;
    private String name;
    private String type;
    private int nodeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sensor sensor = (Sensor) o;

        return nodeId == sensor.nodeId && id == sensor.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
