package demo.sensors.md.shouse_sensors.ws.response;

import java.util.List;

import demo.sensors.md.shouse_sensors.models.Data;
import demo.sensors.md.shouse_sensors.models.Node;
import demo.sensors.md.shouse_sensors.models.Sensor;

/**
 * Created by elisita on 11/7/16.
 */

public class BaseApiResponse {
    String message;
    boolean success;

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
