package com.example.chevron_stationfinder;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
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
import android.widget.ImageButton;

import com.example.chevron_stationfinder.models.Prediction;

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


    private class GetPredictions extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
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
                getActivity().runOnUiThread(() -> recyclerViewAdapter.notifyDataSetChanged());

            } catch (JSONException e) {
                Log.e("ERROR", "Unable to parse JSON");
            }
            return null;
        }

        private String makeUrl(String searchTerm) {

            String urlString = "https://maps.googleapis.com/maps/api/place/autocomplete/json?" + "input=" + searchTerm +
                    "&types=geocode" +
                    "&components=country:us" +
                    "&sensor=true&key=" + KEY;
            return urlString.replaceAll(" ", "+");
        }

        private String getUrlContents(String theUrl) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(theUrl);
                URLConnection urlConnection = url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()), 8);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return content.toString();
        }
    }




}
