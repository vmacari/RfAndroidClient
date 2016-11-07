package demo.sensors.md.shouse_sensors.ws.response;

import java.util.List;

import demo.sensors.md.shouse_sensors.models.Node;

/**
 * Created by elisita on 11/7/16.
 */

public class NodesResponse extends BaseApiResponse{
    List<Node> data;

    public List<Node> getData() {
        return data;
    }
}
