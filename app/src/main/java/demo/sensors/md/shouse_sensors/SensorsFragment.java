package demo.sensors.md.shouse_sensors;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import demo.sensors.md.shouse_sensors.models.Sensor;
import demo.sensors.md.shouse_sensors.util.Const;
import demo.sensors.md.shouse_sensors.ws.ApiServiceProvider;
import demo.sensors.md.shouse_sensors.ws.DataHolder;
import demo.sensors.md.shouse_sensors.ws.response.NodesResponse;
import demo.sensors.md.shouse_sensors.ws.response.SensorsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static demo.sensors.md.shouse_sensors.util.Const.NODE_ID;

public class SensorsFragment extends Fragment {

    private int nodeId;

    private OnFragmentInteractionListener mListener;

    Callback<SensorsResponse> sensorsCallback = new Callback<SensorsResponse>() {
        @Override
        public void onResponse(Call<SensorsResponse> call, Response<SensorsResponse> response) {
            if (response.isSuccessful() && response.body() != null && response.body().getData().size() != 0) {
                DataHolder.addSensors(response.body().getData());
                Toast.makeText(getContext(), "Success: " + response.body().getData().size(), Toast.LENGTH_LONG).show();
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
        } else {
            requestSensors();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_sensor, container, false);
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


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
