package demo.sensors.md.shouse_sensors.ws.response;

import java.util.List;

import demo.sensors.md.shouse_sensors.models.Node;
import demo.sensors.md.shouse_sensors.models.Sensor;

/**
 * Created by elisita on 11/7/16.
 */

public class SensorsResponse extends BaseApiResponse{
    List<Sensor> data;

    public List<Sensor> getData() {
        return data;
    }
}
