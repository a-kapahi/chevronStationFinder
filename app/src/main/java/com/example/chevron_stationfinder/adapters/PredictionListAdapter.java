package com.example.chevron_stationfinder.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chevron_stationfinder.R;
import com.example.chevron_stationfinder.fragments.SearchAddressFragment.OnListFragmentInteractionListener;
import com.example.chevron_stationfinder.models.Prediction;

import java.util.ArrayList;


/**
 * {@link RecyclerView.Adapter} that can display a {@link Prediction} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class PredictionListAdapter extends RecyclerView.Adapter<PredictionListAdapter.ViewHolder> {

    private final ArrayList<Prediction> predictions;
    private final OnListFragmentInteractionListener mListener;

    public PredictionListAdapter(ArrayList<Prediction> items, OnListFragmentInteractionListener listener) {
        predictions = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_prediction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.address.setText(predictions.get(position).getDescription());

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListFragmentInteraction(predictions.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return predictions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        public final TextView address;

        ViewHolder(View view) {
            super(view);
            mView = view;
            address = view.findViewById(R.id.address);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + address.getText() + "'";
        }
    }
}
