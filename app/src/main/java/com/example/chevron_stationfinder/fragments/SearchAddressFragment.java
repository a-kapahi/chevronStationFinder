package com.example.chevron_stationfinder.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.chevron_stationfinder.R;
import com.example.chevron_stationfinder.adapters.PredictionListAdapter;
import com.example.chevron_stationfinder.models.Prediction;
import com.example.chevron_stationfinder.utils.PlacesAPIUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SearchAddressFragment extends Fragment {

    private static final String KEY = "AIzaSyDcthbWZfYguqLFE3ubQiESnNuIcV7rFSM";
    private OnListFragmentInteractionListener mListener;
    private ArrayList<Prediction> predictions;
    private PredictionListAdapter recyclerViewAdapter;
    private Context mContext;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchAddressFragment() {
    }

    public static SearchAddressFragment newInstance() {
        return new SearchAddressFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        predictions = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prediction_list, container, false);
        EditText address = view.findViewById(R.id.address);
        RecyclerView recyclerView = view.findViewById(R.id.prediction_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewAdapter = new PredictionListAdapter(predictions, mListener);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(mContext.getDrawable(R.drawable.list_divider));
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setAdapter(recyclerViewAdapter);
        ImageButton clearButton = view.findViewById(R.id.clearButton);
        clearButton.setOnClickListener(view1 -> address.getText().clear());
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getPredictions(String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        FrameLayout container = getActivity().findViewById(R.id.full_fragment_container);
        container.setVisibility(View.GONE);
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
        void onListFragmentInteraction(Prediction item);
    }

    private void getPredictions(String searchTerm) {
        Map<String, String> params = new HashMap<>();
        params.put("input", searchTerm);
        params.put("types, ", "geocode");
        params.put("components", "country:us");
        params.put("sensor", "true");
        params.put("key", KEY);
        Call<JsonObject> call = PlacesAPIUtils.getPlacesService().getPlaces(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject object = response.body();
                    JsonArray array = object.getAsJsonArray("predictions");
                    predictions.clear();
                    array.iterator().forEachRemaining(jsonElement -> predictions.add(new Prediction(jsonElement.getAsJsonObject().get("description").getAsString(), jsonElement.getAsJsonObject().get("place_id").getAsString())));
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {

            }
        });
    }


}