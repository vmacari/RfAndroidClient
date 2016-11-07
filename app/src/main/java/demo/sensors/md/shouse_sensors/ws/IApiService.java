package demo.sensors.md.shouse_sensors.ws;

import demo.sensors.md.shouse_sensors.ws.response.DataResponse;
import demo.sensors.md.shouse_sensors.ws.response.NodesResponse;
import demo.sensors.md.shouse_sensors.ws.response.SensorsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by elisita on 11/6/16.
 */

public interface IApiService {
    @GET("/node")
    Call<NodesResponse> getNodes();

    @GET("/sensor/{id}")
    Call<SensorsResponse> getSensors(@Path("id") int nodeId);

    @GET("/data/{node_id}/{sensor_id}")
    Call<DataResponse> getData(@Path("node_id") int nodeId, @Path("sensor_id") int sensorId);
}
