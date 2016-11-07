package demo.sensors.md.shouse_sensors.ws;

import java.util.ArrayList;
import java.util.List;

import demo.sensors.md.shouse_sensors.models.Data;
import demo.sensors.md.shouse_sensors.models.Node;
import demo.sensors.md.shouse_sensors.models.Sensor;

/**
 * Created by elisita on 11/7/16.
 * Temp solution for in memory data.
 */

public class DataHolder {
    static List<Node> nodes = new ArrayList<>();
    static List<Sensor> sensors = new ArrayList<>();
    static List<Data> data = new ArrayList<>();

    public static List<Node> getNodes() {
        return nodes;
    }

    public static void setNodes(List<Node> newNodes) {
        nodes = newNodes;
    }

    public static List<Sensor> getSensors(int nodeId) {
        List<Sensor> nodeSensors = new ArrayList<>();

        for (Sensor sensor : sensors) {
            if (sensor.getNodeId() == nodeId) {
                nodeSensors.add(sensor);
            }
        }
        return nodeSensors;
    }

    public static void addSensors(List<Sensor> newSensors) {
        for (Sensor sensor : newSensors) {
            if (!sensors.contains(sensor)) {
                sensors.add(sensor);
            }
        }
    }

    public static List<Data> getData(int nodeId, int sensorId) {
        List<Data> sensorsData = new ArrayList<>();

        for (Data sensorData : data) {
            if (sensorData.getNodeId() == nodeId && sensorData.getSensorId() == sensorId) {
                sensorsData.add(sensorData);
            }
        }
        return sensorsData;

    }

    public static void addData(List<Data> newData) {
        for (Data sensorData : newData) {
            if (!data.contains(sensorData)) {
                data.add(sensorData);
            }
        }
    }
}
