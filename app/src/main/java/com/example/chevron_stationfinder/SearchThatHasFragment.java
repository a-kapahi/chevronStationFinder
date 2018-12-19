package com.example.chevron_stationfinder;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchThatHasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchThatHasFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    private OnFragmentInteractionListener mListener;
    private boolean hasDiesel;
    private boolean hasTapToPay;
    private boolean hasExtraMile;
    private boolean hasCarWash;
    private boolean hasStore;
    private boolean hasGroceryRewards;

    public SearchThatHasFragment() {
        // Required empty public constructor
    }


    public static SearchThatHasFragment newInstance(String param1, String param2) {
        SearchThatHasFragment fragment = new SearchThatHasFragment();
        Bundle args = new Bundle();
       // args.putString(ARG_PARAM1, param1);
       // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // mParam1 = getArguments().getString(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_that_has, container, false);
        ToggleButton diesel = view.findViewById(R.id.diesel);
        ToggleButton carWash = view.findViewById(R.id.carWash);
        ToggleButton store = view.findViewById(R.id.store);
        ToggleButton tapToPay = view.findViewById(R.id.tapToPay);
        ToggleButton extraMile = view.findViewById(R.id.extraMile);
        ToggleButton groceryRewards = view.findViewById(R.id.groceryRewards);
        ImageButton close = view.findViewById(R.id.closeBtn);
        TextView results = view.findViewById(R.id.resultsBtn);
        Spinner distanceSpinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.distance_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceSpinner.setAdapter(adapter);
        distanceSpinner.setOnItemSelectedListener(this);
        close.setOnClickListener(this);
        diesel.setOnCheckedChangeListener(this);
        carWash.setOnCheckedChangeListener(this);
        store.setOnCheckedChangeListener(this);
        tapToPay.setOnCheckedChangeListener(this);
        extraMile.setOnCheckedChangeListener(this);
        groceryRewards.setOnCheckedChangeListener(this);
        results.setOnClickListener(this);
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.closeBtn:{
                mListener.onFragmentInteraction("ThatHas",view);
                break;
            }
            case R.id.resultsBtn: {

                //mListener.onFragmentInteraction("ThatHas");
                break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String distance = (String)parent.getItemAtPosition(pos);
        Log.d("Spinner selected",distance.substring(0,2).trim()+"miles");

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            compoundButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        else {
            compoundButton.setBackgroundColor(Color.WHITE);
        }
        switch (compoundButton.getId()){
            case R.id.diesel: {
                hasDiesel = hasDiesel?false:true;
                Log.d("diesel", String.valueOf(hasDiesel));
                break;
            }
            case R.id.carWash: {
                hasCarWash = hasCarWash?false:true;
                Log.d("car wash", String.valueOf(hasCarWash));
                break;
            }
            case R.id.tapToPay: {
                hasTapToPay = hasTapToPay?false:true;
                Log.d("Tap to pay", String.valueOf(hasTapToPay));
                break;
            }
            case R.id.store: {
                hasStore = hasStore?false:true;
                Log.d("Store", String.valueOf(hasStore));
                break;
            }
            case R.id.groceryRewards: {
                hasGroceryRewards = hasGroceryRewards?false:true;
                Log.d("Grocery rewards", String.valueOf(hasGroceryRewards));
                break;
            }
            case R.id.extraMile: {
                hasExtraMile = hasExtraMile?false:true;
                Log.d("Extra mile", String.valueOf(hasExtraMile));
                break;
            }
        }
    }
}
