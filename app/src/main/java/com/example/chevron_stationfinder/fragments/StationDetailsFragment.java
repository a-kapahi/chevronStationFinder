package com.example.chevron_stationfinder.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chevron_stationfinder.R;
import com.example.chevron_stationfinder.interfaces.OnFragmentInteractionListener;
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
    private String TAG = "DetailsFragment";

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_station_details, container, false);
        TextView stationName = view.findViewById(R.id.stationName);
        TextView stationAddress = view.findViewById(R.id.stationAddress);
        TextView stationDistance = view.findViewById(R.id.stationDistance);
        TextView stationPhone = view.findViewById(R.id.stationPhone);
        TextView directions = view.findViewById(R.id.btnDirections);
        TextView extraMile = view.findViewById(R.id.extraMile);
        TextView tapToPay = view.findViewById(R.id.tapToPay);
        TextView carWash = view.findViewById(R.id.carWash);
        TextView diesel = view.findViewById(R.id.diesel);
        TextView store = view.findViewById(R.id.store);
        TextView rewards = view.findViewById(R.id.rewards);
        ImageButton close = view.findViewById(R.id.closeButton);

        diesel.setTextColor((Integer.valueOf(station.getDiesel()) == 0) ? Color.GRAY : Color.BLACK);
        carWash.setTextColor((Integer.valueOf(station.getCarwash()) == 0) ? Color.GRAY : Color.BLACK);
        tapToPay.setTextColor((Integer.valueOf(station.getNfc()) == 0) ? Color.GRAY : Color.BLACK);
        extraMile.setTextColor((Integer.valueOf(station.getExtramile()) == 0) ? Color.GRAY : Color.BLACK);
        store.setTextColor((Integer.valueOf(station.getCstore()) == 0) ? Color.GRAY : Color.BLACK);
        rewards.setTextColor((Integer.valueOf(station.getLoyalty()) == 0) ? Color.GRAY : Color.BLACK);

        stationName.setText(station.getName());
        stationAddress.setText(station.getAddress());
        stationDistance.setText(station.getDistance().substring(0,3).concat(" Miles"));
        stationPhone.setText(station.getPhone());
        stationPhone.setOnClickListener(this);
        directions.setOnClickListener(this);
        close.setOnClickListener(this);
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
            case R.id.closeButton:{
                mListener.onFragmentInteraction(TAG,v);
            }
        }
    }
}
