package demo.sensors.md.shouse_sensors;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import demo.sensors.md.shouse_sensors.models.Sensor;
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

    private OnFragmentInteractionListener mListener;

    Callback<SensorsResponse> sensorsCallback = new Callback<SensorsResponse>() {
        @Override
        public void onResponse(Call<SensorsResponse> call, Response<SensorsResponse> response) {
            if (response.isSuccessful() && response.body() != null && response.body().getData().size() != 0) {
                DataHolder.addSensors(response.body().getData());
                Toast.makeText(getContext(), "Success: " + response.body().getData().size(), Toast.LENGTH_LONG).show();
                requestDataForSensors();
            }
        }

        @Override
        public void onFailure(Call<SensorsResponse> call, Throwable t) {
            Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_sensors_layout, container, false);
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

    private void requestSensors() {
        Call<SensorsResponse> sensorResponseCall = ApiServiceProvider.getApiService().getSensors(nodeId);
        sensorResponseCall.enqueue(sensorsCallback);
    }

    private void requestDataForSensors() {
        for (Sensor sensor : DataHolder.getSensors(nodeId)) {
            dataRequestsCount++;
            Call<DataResponse> dataResponseCall = ApiServiceProvider.getApiService().getData(nodeId, sensor.getId());
            dataResponseCall.enqueue(new Callback<DataResponse>() {
                @Override
                public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                    dataRequestsCount--;
                    checkDataRequestCount();
                    if (response.isSuccessful() && response.body() != null && response.body().getData().size() != 0) {
                        DataHolder.addData(response.body().getData());
                        Toast.makeText(getContext(), "Data: " + response.body().getData().size(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<DataResponse> call, Throwable t) {
                    dataRequestsCount--;
                    checkDataRequestCount();
                    Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void checkDataRequestCount() {
        if (dataRequestsCount == 0) {
            populateList();
        }
    }

    private void populateList() {
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new SensorsAdapter(DataHolder.getSensors(nodeId)));

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static class SensorsAdapter extends RecyclerView.Adapter<SensorViewHolder> {

        List<Sensor> sensors = new ArrayList<>();

        public SensorsAdapter(List<Sensor> sensors) {
            this.sensors = sensors;
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
            viewHolder.categoryTitleView.setText(sensor.getName());
//            viewHolder.parentView.setBackgroundColor(category.getColor());
//            viewHolder.categoryIcon.setImageResource(CategoryIconEnum.values()[category.getIconId()].getIconWhiteRes());
        }

        @Override
        public int getItemCount() {
            return sensors.size();
        }

    }

    private static class SensorViewHolder extends RecyclerView.ViewHolder {

        TextView categoryTitleView;
        ImageView categoryIcon;
        View parentView;

        public SensorViewHolder(View itemView) {
            super(itemView);
            categoryTitleView = (TextView) itemView.findViewById(R.id.category_title);
//            categoryIcon = (ImageView) itemView.findViewById(R.id.categoryIcon);
            parentView = itemView;
        }
    }

}
