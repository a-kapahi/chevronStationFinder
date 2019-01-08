package com.example.chevron_stationfinder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    private Integer distance;
    private ToggleButton diesel, carWash, store, tapToPay, extraMile, groceryRewards;
    private Spinner distanceSpinner;
    private ArrayAdapter<CharSequence> adapter;

    public SearchThatHasFragment() {
        // Required empty public constructor
    }


    public static SearchThatHasFragment newInstance(Integer[] filters) {
        SearchThatHasFragment fragment = new SearchThatHasFragment();
        Bundle args = new Bundle();
        args.putInt("distance", filters[6]);
        args.putInt("hasExtraMile", filters[0]);
        args.putInt("hasGroceryRewards", filters[1]);
        args.putInt("hasStore", filters[2]);
        args.putInt("hasTapToPay", filters[3]);
        args.putInt("hasCarWash", filters[4]);
        args.putInt("hasDiesel", filters[5]);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            distance = getArguments().getInt("distance");
            hasDiesel = getArguments().getInt("hasDiesel") == 0 ? false : true;
            hasTapToPay = getArguments().getInt("hasTapToPay") == 0 ? false : true;
            hasExtraMile = getArguments().getInt("hasExtraMile") == 0 ? false : true;
            hasCarWash = getArguments().getInt("hasCarWash") == 0 ? false : true;
            hasStore = getArguments().getInt("hasStore") == 0 ? false : true;
            hasGroceryRewards = getArguments().getInt("hasGroceryRewards") == 0 ? false : true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_that_has, container, false);
        diesel = view.findViewById(R.id.diesel);
        carWash = view.findViewById(R.id.carWash);
        store = view.findViewById(R.id.store);
        tapToPay = view.findViewById(R.id.tapToPay);
        extraMile = view.findViewById(R.id.extraMile);
        groceryRewards = view.findViewById(R.id.groceryRewards);
        ImageButton close = view.findViewById(R.id.closeBtn);
        TextView results = view.findViewById(R.id.resultsBtn);
        TextView resetBtn = view.findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(this);
        distanceSpinner = view.findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(getContext(),
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
        initFilters();
        results.setOnClickListener(this);
        return view;
    }

    public void initFilters() {
        diesel.setChecked(hasDiesel);
        carWash.setChecked(hasCarWash);
        tapToPay.setChecked(hasTapToPay);
        store.setChecked(hasStore);
        groceryRewards.setChecked(hasGroceryRewards);
        extraMile.setChecked(hasExtraMile);

        switch (distance) {
            case 1:
                distanceSpinner.setSelection(0);
                break;
            case 3:
                distanceSpinner.setSelection(1);
                break;
            case 5:
                distanceSpinner.setSelection(2);
                break;
            case 10:
                distanceSpinner.setSelection(3);
                break;
            case 15:
                distanceSpinner.setSelection(4);
                break;
            case 20:
                distanceSpinner.setSelection(5);
                break;
            case 25:
                distanceSpinner.setSelection(6);
                break;
            case 30:
                distanceSpinner.setSelection(7);
                break;
            case 35:
                distanceSpinner.setSelection(8);
                break;
        }
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
                Integer[] filters = new Integer[]{hasExtraMile ? 1 : 0, hasGroceryRewards ? 1 : 0, hasStore ? 1 : 0, hasTapToPay ? 1 : 0, hasCarWash ? 1 : 0, hasDiesel ? 1 : 0, distance};
                mListener.onFragmentInteraction("ThatHas", filters);
                break;
            }
            case R.id.resetBtn: {
                diesel.setChecked(false);
                carWash.setChecked(false);
                tapToPay.setChecked(false);
                store.setChecked(false);
                groceryRewards.setChecked(false);
                extraMile.setChecked(false);
                distanceSpinner.setSelection(8);
                break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String distance = (String)parent.getItemAtPosition(pos);
        this.distance = Integer.valueOf(distance.substring(0, 2).trim());
        Log.d("Spinner selected", this.distance + "miles");

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.diesel: {
                hasDiesel = b;
                Drawable img = getContext().getResources().getDrawable(hasDiesel ? R.drawable.filter_icon_diesel_white : R.drawable.filter_icon_diesel_black);
                img.setBounds(0, 0, 100, 100);
                compoundButton.setCompoundDrawables(null, img, null, null);
                Log.d("diesel", String.valueOf(hasDiesel));
                break;
            }
            case R.id.carWash: {
                hasCarWash = b;
                Drawable img = getContext().getResources().getDrawable(hasCarWash ? R.drawable.filter_icon_car_wash_white : R.drawable.filter_icon_car_wash_black);
                img.setBounds(0, 0, 100, 100);
                compoundButton.setCompoundDrawables(null, img, null, null);
                Log.d("car wash", String.valueOf(hasCarWash));
                break;
            }
            case R.id.tapToPay: {
                hasTapToPay = b;
                Drawable img = getContext().getResources().getDrawable(hasTapToPay ? R.drawable.filter_icon_tap_to_pay_white : R.drawable.filter_icon_tap_to_pay_black);
                img.setBounds(0, 0, 100, 100);
                compoundButton.setCompoundDrawables(null, img, null, null);
                Log.d("Tap to pay", String.valueOf(hasTapToPay));
                break;
            }
            case R.id.store: {
                hasStore = b;
                Drawable img = getContext().getResources().getDrawable(hasStore ? R.drawable.filter_icon_store_white : R.drawable.filter_icon_store_black);
                img.setBounds(0, 0, 100, 100);
                compoundButton.setCompoundDrawables(null, img, null, null);
                Log.d("Store", String.valueOf(hasStore));
                break;
            }
            case R.id.groceryRewards: {
                hasGroceryRewards = b;
                Drawable img = getContext().getResources().getDrawable(hasGroceryRewards ? R.drawable.filter_icon_grocery_rewards_white : R.drawable.filter_icon_grocery_rewards_black);
                img.setBounds(0, 0, 100, 100);
                compoundButton.setCompoundDrawables(null, img, null, null);
                Log.d("Grocery rewards", String.valueOf(hasGroceryRewards));
                break;
            }
            case R.id.extraMile: {
                hasExtraMile = b;
                Drawable img = getContext().getResources().getDrawable(hasExtraMile ? R.drawable.filter_icon_extramile_white : R.drawable.filter_icon_extramile_black);
                img.setBounds(0, 0, 100, 100);
                compoundButton.setCompoundDrawables(null, img, null, null);
                Log.d("Extra mile", String.valueOf(hasExtraMile));
                break;
            }
        }
        compoundButton.setTextColor(b ? Color.WHITE : Color.BLACK);
    }
}
