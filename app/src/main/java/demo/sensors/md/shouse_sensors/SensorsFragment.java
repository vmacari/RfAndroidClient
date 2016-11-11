package demo.sensors.md.shouse_sensors;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.robinhood.spark.SparkAdapter;
import com.robinhood.spark.SparkView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import demo.sensors.md.shouse_sensors.enums.SensorType;
import demo.sensors.md.shouse_sensors.models.Data;
import demo.sensors.md.shouse_sensors.models.Sensor;
import demo.sensors.md.shouse_sensors.util.Logger;
import demo.sensors.md.shouse_sensors.ws.ApiServiceProvider;
import demo.sensors.md.shouse_sensors.ws.DataHolder;
import demo.sensors.md.shouse_sensors.ws.response.DataResponse;
import demo.sensors.md.shouse_sensors.ws.response.SensorsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static demo.sensors.md.shouse_sensors.util.Const.NODE_ID;

public class SensorsFragment extends Fragment {

    private int nodeId;
    private int dataRequestsCount = 0;
    private Call<SensorsResponse> sensorResponseCall;
    private Call<DataResponse> dataResponseCall;

    private OnFragmentInteractionListener mListener;

    Callback<SensorsResponse> sensorsCallback = new Callback<SensorsResponse>() {
        @Override
        public void onResponse(Call<SensorsResponse> call, Response<SensorsResponse> response) {
            if (response.isSuccessful() && response.body() != null && response.body().getData().size() != 0) {
                DataHolder.addSensors(response.body().getData());
                requestDataForSensors();
            }
        }

        @Override
        public void onFailure(Call<SensorsResponse> call, Throwable t) {
            if (getContext() != null) {
                Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
            stopRefreshing();
        }
    };

    public SensorsFragment() {
        // Required empty public constructor
    }

    public static SensorsFragment newInstance(int nodeId) {
        SensorsFragment fragment = new SensorsFragment();
        Bundle args = new Bundle();
        args.putInt(NODE_ID, nodeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nodeId = getArguments().getInt(NODE_ID);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Sensor> sensors = DataHolder.getSensors(nodeId);
        if (sensors.size() != 0) {
            // populate view
            populateList();
        } else {
            requestSensors();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = LayoutInflater.from(getContext()).inflate(R.layout.frg_sensors_layout, container, false);
        setup(parent);

        return parent;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sensorResponseCall != null) {
            sensorResponseCall.cancel();
        }
        if (dataResponseCall != null) {
            dataResponseCall.cancel();
        }
    }

    private void requestSensors() {
        sensorResponseCall = ApiServiceProvider.getApiService().getSensors(nodeId);
        sensorResponseCall.enqueue(sensorsCallback);
    }

    private void requestDataForSensors() {
        for (Sensor sensor : DataHolder.getSensors(nodeId)) {
            dataRequestsCount++;
            dataResponseCall = ApiServiceProvider.getApiService().getData(nodeId, sensor.getId(), 50);
            dataResponseCall.enqueue(new Callback<DataResponse>() {
                @Override
                public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                    dataRequestsCount--;
                    checkDataRequestCount();
                    if (response.isSuccessful() && response.body() != null && response.body().getData().size() != 0) {
                        DataHolder.addData(response.body().getData());
                    }

                }

                @Override
                public void onFailure(Call<DataResponse> call, Throwable t) {
                    dataRequestsCount--;
                    checkDataRequestCount();
                    if (getContext() != null) {
                        Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void checkDataRequestCount() {
        if (dataRequestsCount == 0) {
            populateList();
            stopRefreshing();
        }
    }

    private void setup(View parent) {
        SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) parent.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestSensors();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    private void stopRefreshing() {
        if (getView() != null) {
            SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) getView().findViewById(R.id.swipeContainer);
            swipeContainer.setRefreshing(false);
        }
    }

    private void populateList() {
        if (getView() != null) {
            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
            recyclerView.setAdapter(new SensorsAdapter(nodeId));

            final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static class SensorsAdapter extends RecyclerView.Adapter<SensorViewHolder> {

        List<Sensor> sensors = new ArrayList<>();
        Map<Sensor, ArrayList<Float>> dataSet = new LinkedHashMap<>();
        int nodeId;

        public SensorsAdapter(int nodeId) {
            this.nodeId = nodeId;
            this.sensors = DataHolder.getSensors(nodeId);
            generateDataSet();
        }

        @Override
        public SensorViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_sensor_layout, viewGroup, false);

            SensorViewHolder vh = new SensorViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(SensorViewHolder viewHolder, int i) {
            Sensor sensor = sensors.get(i);
            List<Float> sensorRawData = dataSet.get(sensor);
            SensorType sensorType = SensorType.getByName(sensor.getType());
            viewHolder.nameView.setText(sensor.getName());
            viewHolder.chartView.setAdapter(new ChartAdapter(sensorRawData));
            if (sensorRawData.size() != 0) {
                String sensorCurrentValueLabel = sensorRawData.get(sensorRawData.size() - 1) + sensorType.getUnit();
                viewHolder.currentValueView.setText(sensorCurrentValueLabel);
            }
        }

        @Override
        public int getItemCount() {
            return sensors.size();
        }

        private void generateDataSet() {
            for (Sensor sensor : sensors) {
                ArrayList<Data> sensorData = (ArrayList<Data>) DataHolder.getData(nodeId, sensor.getId());
                ArrayList<Float> rawData = new ArrayList<>();
                for (Data data : sensorData) {
                    try {
                        rawData.add(Float.valueOf(data.getData()));
                    } catch (Exception e) {
                        Logger.d(e.getMessage());
                    }
                }
                dataSet.put(sensor, rawData);
            }
        }

    }

    private static class SensorViewHolder extends RecyclerView.ViewHolder {

        TextView nameView;
        TextView currentValueView;
        SparkView chartView;


        public SensorViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.sensorName);
            currentValueView = (TextView) itemView.findViewById(R.id.sensorCurrentValue);
            chartView = (SparkView) itemView.findViewById(R.id.chartView);
        }
    }

    public static class ChartAdapter extends SparkAdapter {
        private List<Float> yData = new ArrayList<>();

        public ChartAdapter(List<Float> yData) {
            this.yData = yData;
        }

        @Override
        public int getCount() {
            return yData.size();
        }

        @Override
        public Object getItem(int index) {
            return yData.get(index);
        }

        @Override
        public float getY(int index) {
            return yData.get(index);
        }
    }

}
