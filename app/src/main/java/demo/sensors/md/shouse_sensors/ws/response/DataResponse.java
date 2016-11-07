package demo.sensors.md.shouse_sensors.ws.response;

import java.util.List;

import demo.sensors.md.shouse_sensors.models.Data;
import demo.sensors.md.shouse_sensors.models.Node;

/**
 * Created by elisita on 11/7/16.
 */

public class DataResponse extends BaseApiResponse{
    List<Data> data;

    public List<Data> getData() {
        return data;
    }
}
