package com.example.chevron_stationfinder.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chevron_stationfinder.R;
import com.example.chevron_stationfinder.fragments.StationListFragment.OnListFragmentInteractionListener;
import com.example.chevron_stationfinder.models.Station;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Station} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */

public class MystationRecyclerViewAdapter extends RecyclerView.Adapter<MystationRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Station> stations;
    private final OnListFragmentInteractionListener mListener;

    public MystationRecyclerViewAdapter(ArrayList<Station> items, OnListFragmentInteractionListener listener) {
        stations = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_station_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.station = stations.get(position);
        holder.stationName.setText(stations.get(position).getName());
        holder.stationAddress.setText(stations.get(position).getAddress());
        holder.distance.setText(stations.get(position).getDistance().substring(0,3).concat(" Mi"));
        holder.amenities.setOnClickListener(view -> {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.amenities.getLayoutParams();
            if (holder.amenitiesLayout.getVisibility() == View.VISIBLE) {
                holder.amenitiesLayout.setVisibility(View.GONE);
                params.topToBottom = holder.distance.getId();
            } else {
                holder.amenitiesLayout.setVisibility(View.VISIBLE);
                params.topToBottom = holder.amenitiesLayout.getId();
                setAmenities(holder);
            }
        });

        holder.directions.setOnClickListener(view -> mListener.onListFragmentInteraction(holder.station, 1));
        holder.details.setOnClickListener(view -> mListener.onListFragmentInteraction(holder.station, 2));
    }

    private void setAmenities(ViewHolder holder) {
        holder.diesel.setTextColor((Integer.valueOf(holder.station.getDiesel()) == 0) ? Color.GRAY : Color.BLACK);
        holder.carWash.setTextColor((Integer.valueOf(holder.station.getCarwash()) == 0) ? Color.GRAY : Color.BLACK);
        holder.tapToPay.setTextColor((Integer.valueOf(holder.station.getNfc()) == 0) ? Color.GRAY : Color.BLACK);
        holder.extraMile.setTextColor((Integer.valueOf(holder.station.getExtramile()) == 0) ? Color.GRAY : Color.BLACK);
        holder.store.setTextColor((Integer.valueOf(holder.station.getCstore()) == 0) ? Color.GRAY : Color.BLACK);
        holder.rewards.setTextColor((Integer.valueOf(holder.station.getLoyalty()) == 0) ? Color.GRAY : Color.BLACK);
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView stationName;
        public final TextView stationAddress;
        public final TextView distance;
        public final ConstraintLayout amenitiesLayout;
        final TextView amenities;
        final TextView directions;
        final TextView details;
        public final TextView extraMile;
        public final TextView tapToPay;
        public final TextView carWash;
        public final TextView diesel;
        public final TextView store;
        public final TextView rewards;
        Station station;

        ViewHolder(View view) {
            super(view);
            mView = view;
            stationName = view.findViewById(R.id.stationName);
            stationAddress = view.findViewById(R.id.address);
            distance = view.findViewById(R.id.distance);
            amenitiesLayout = view.findViewById(R.id.amenitiesLayout);
            amenities = view.findViewById(R.id.btnAmenities);
            directions = view.findViewById(R.id.btnDirections);
            details = view.findViewById(R.id.btnDetails);
            extraMile = view.findViewById(R.id.extraMile);
            tapToPay = view.findViewById(R.id.tapPay);
            carWash = view.findViewById(R.id.carWash);
            diesel = view.findViewById(R.id.diesel);
            store = view.findViewById(R.id.store);
            rewards = view.findViewById(R.id.rewards);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + stationAddress.getText() + "'";
        }
    }
}
