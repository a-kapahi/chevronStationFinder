package com.example.chevron_stationfinder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chevron_stationfinder.models.Station;
import com.example.chevron_stationfinder.StationListFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Station} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MystationRecyclerViewAdapter extends RecyclerView.Adapter<MystationRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Station> stations;
    private final OnListFragmentInteractionListener mListener;

    public MystationRecyclerViewAdapter(ArrayList<Station> items, OnListFragmentInteractionListener listener) {
        stations = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_station, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.station = stations.get(position);
        holder.stationName.setText(stations.get(position).getName());
        holder.stationAddress.setText(stations.get(position).getAddress());
        holder.distance.setText(stations.get(position).getDistance().substring(0,3).concat(" Mi"));
        holder.amenities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.amenities.getLayoutParams();
                if(holder.amenitiesLayout.getVisibility()==View.VISIBLE) {
                    holder.amenitiesLayout.setVisibility(View.GONE);
                    params.topToBottom = holder.distance.getId();
                }
                else{
                    holder.amenitiesLayout.setVisibility(View.VISIBLE);
                    params.topToBottom = holder.amenitiesLayout.getId();
                }
            }
        });
        holder.directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onListFragmentInteraction(holder.station, 1);
            }
        });

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onListFragmentInteraction(holder.station, 2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView stationName;
        public final TextView stationAddress;
        public final TextView distance;
        public final ConstraintLayout amenitiesLayout;
        public final TextView amenities;
        public final TextView directions;
        public final TextView details;
        public Station station;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            stationName = view.findViewById(R.id.stationName);
            stationAddress = view.findViewById(R.id.address);
            distance = view.findViewById(R.id.distance);
            amenitiesLayout = view.findViewById(R.id.amenitiesLayout);
            amenities = view.findViewById(R.id.btnAmenities);
            directions = view.findViewById(R.id.btnDirections);
            details = view.findViewById(R.id.btnDetails);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + stationAddress.getText() + "'";
        }
    }
}
