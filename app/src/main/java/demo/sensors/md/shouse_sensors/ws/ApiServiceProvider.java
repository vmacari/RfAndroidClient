package demo.sensors.md.shouse_sensors.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by elisita on 11/6/16.
 */

public class ApiServiceProvider {

    private static final String BASE_URL = "http://192.168.0.14:8081"; // "http://192.168.5.5:8081";
    private static IApiService apiService;

    public static void createApiService() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(IApiService.class);
    }

    public static IApiService getApiService() {
        if (apiService == null) {
            createApiService();
        }
        return apiService;
    }
}
