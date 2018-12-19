package com.example.chevron_stationfinder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chevron_stationfinder.models.Station;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StationDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StationDetailsFragment extends Fragment implements OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Station station;
    private String TAG = "DETAILS";

    public StationDetailsFragment() {
        // Required empty public constructor
    }

    public static StationDetailsFragment newInstance(Station station) {
        StationDetailsFragment fragment = new StationDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("Station", station);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            station = getArguments().getParcelable("Station");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_station_details, container, false);
        TextView stationName = view.findViewById(R.id.stationName);
        TextView stationAddress = view.findViewById(R.id.stationAddress);
        TextView stationDistance = view.findViewById(R.id.stationDistance);
        TextView stationPhone = view.findViewById(R.id.stationPhone);
        TextView directions = view.findViewById(R.id.btnDirections);
        stationName.setText(station.getName());
        stationAddress.setText(station.getAddress());
        stationDistance.setText(station.getDistance().substring(0,3).concat(" Miles"));
        stationPhone.setText(station.getPhone());
        stationPhone.setOnClickListener(this);
        directions.setOnClickListener(this);
        return view;
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
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnDirections:{
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + station.lat + "," + station.lng);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                break;
            }
            case R.id.stationPhone:{
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:".concat(station.getPhone())));
                startActivity(intent);
                break;
            }
        }
    }
}
