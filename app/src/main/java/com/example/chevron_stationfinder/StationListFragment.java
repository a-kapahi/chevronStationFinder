package com.example.chevron_stationfinder;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chevron_stationfinder.models.Station;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class StationListFragment extends Fragment implements View.OnClickListener, OnStationListReady {

    private ArrayList<Station> stations;
    private OnListFragmentInteractionListener mListListener;
    private OnFragmentInteractionListener mListener;
    private String address;
    private MystationRecyclerViewAdapter recyclerViewAdapter;
    private TextView stationCount;
    private RecyclerView recyclerView;
    private ProgressBar simpleProgressBar;
    private boolean isLoading = true;
    private TextView addressText;
    private TextView emptyText;
    private Context mContext;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StationListFragment() {
    }

    public static StationListFragment newInstance(ArrayList<Station> stations, String address) {
        StationListFragment fragment = new StationListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("stations", stations);
        args.putString("Address", address);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.stations = getArguments().getParcelableArrayList("stations");
            address = getArguments().getString("Address");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_station_list, container, false);
        Context context = view.getContext();
        addressText = view.findViewById(R.id.address);
        recyclerView = view.findViewById(R.id.list);
        simpleProgressBar = view.findViewById(R.id.progressBar);
        emptyText = view.findViewById(R.id.emptyText);
        if(isLoading) {
            recyclerView.setVisibility(View.INVISIBLE);
            emptyText.setVisibility(View.GONE);
            simpleProgressBar.setVisibility(View.VISIBLE);
        } else {
            simpleProgressBar.setVisibility(View.GONE);
            checkEmptyList();
        }
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new MystationRecyclerViewAdapter(stations, mListListener);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                 super.onScrollStateChanged(recyclerView, newState);
                 if(newState==RecyclerView.SCROLL_STATE_IDLE) {
                     mListListener.onListFragmentInteraction(stations.get(layoutManager.findFirstVisibleItemPosition()),3);
                 }
                }
            });
        stationCount = view.findViewById(R.id.stationCount);
        Button optionsButton = view.findViewById(R.id.optionsBtn);
        optionsButton.setOnClickListener(this);
        stationCount.setText(String.valueOf(stations.size()));
        addressText.setText(this.address);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListListener = (OnListFragmentInteractionListener) context;
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListListener = null;
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.optionsBtn:{
                mListener.onFragmentInteraction("ListFragment", view);
            }
        }
    }

    @Override
    public void onListReady(ArrayList<Station> filteredStations) {
        stations.clear();
        stations.addAll(filteredStations);
        recyclerViewAdapter.notifyDataSetChanged();
        stationCount.setText(String.valueOf(stations.size()));
        simpleProgressBar.setVisibility(View.GONE);
        checkEmptyList();
        isLoading = false;
    }

    @Override
    public void changeAddressText(String address) {
        this.address = address;
        addressText.setText(this.address);

    }

    @Override
    public void scrollToStation(Station station) {
        RecyclerView.SmoothScroller smoothScroller = new
                LinearSmoothScroller(mContext) {
                    @Override
                    protected int getVerticalSnapPreference() {
                        return LinearSmoothScroller.SNAP_TO_START;
                    }
                };
        smoothScroller.setTargetPosition(stations.indexOf(station));
        recyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
    }

    public void checkEmptyList() {
        if (stations.isEmpty()) {
            recyclerView.setVisibility(View.INVISIBLE);
            emptyText.setText("We couldn't find any stations near this location.");
            emptyText.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Station station, int flag);
    }
}
