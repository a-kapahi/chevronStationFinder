package com.example.chevron_stationfinder;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SearchAddressFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String KEY = "AIzaSyDcthbWZfYguqLFE3ubQiESnNuIcV7rFSM";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private ArrayList<Prediction> predictions;
    private PredictionListAdapter recyclerViewAdapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchAddressFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SearchAddressFragment newInstance(int columnCount) {
        SearchAddressFragment fragment = new SearchAddressFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        predictions = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prediction_list, container, false);
        EditText address = view.findViewById(R.id.address);
        RecyclerView recyclerView = view.findViewById(R.id.prediction_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewAdapter = new PredictionListAdapter(predictions, mListener);
        recyclerView.setAdapter(recyclerViewAdapter);
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                new GetPredictions().execute(String.valueOf(charSequence));
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
        // TODO: Update argument type and name
        void onListFragmentInteraction(Prediction item);
    }


    private class GetPredictions extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String url = makeUrl(strings[0]);
            String results = getUrlContents(url);
            try {
                JSONObject object = new JSONObject(results);
                JSONArray array = object.getJSONArray("predictions");
                Log.d("predictions", String.valueOf(array.length()));
                predictions.clear();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject f = (JSONObject) array.get(i);
                    Log.d(String.valueOf(f.get("description")), String.valueOf(f.get("place_id")));
                    predictions.add(new Prediction(String.valueOf(f.get("description")), String.valueOf(f.get("place_id"))));
                }
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                });

            } catch (JSONException e) {
                Log.e("ERROR", "Unable to parse JSON");
            }
            return null;
        }

        private String makeUrl(String searchTerm) {
            StringBuilder urlString = new StringBuilder("https://maps.googleapis.com/maps/api/place/autocomplete/json?");

            urlString.append("input=" + searchTerm);
            urlString.append("&types=geocode");
            urlString.append("&components=country:us");
            urlString.append("&sensor=true&key=" + KEY);
            return urlString.toString().replaceAll(" ", "+");
        }

        private String getUrlContents(String theUrl) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(theUrl);
                URLConnection urlConnection = url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()), 8);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line + "\n");
                }
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return content.toString();
        }
    }




}
