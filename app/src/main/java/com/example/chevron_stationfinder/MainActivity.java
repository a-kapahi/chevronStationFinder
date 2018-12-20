package com.example.chevron_stationfinder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.chevron_stationfinder.models.Station;
import com.example.chevron_stationfinder.models.StationList;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity
        implements OnFragmentInteractionListener, OnMapReadyCallback, StationListFragment.OnListFragmentInteractionListener {

    private FusedLocationProviderClient mFusedLocationClient;
    public static final String RELATIVE_URL_FOR_NEAR_ME 	= "ws_getChevronTexacoNearMe_r2.aspx"; // "GetChevronWithTechronNearMe.aspx";
    private Location loc;
    private ArrayList<Station> stations;
    private GoogleMap gMap;
    private OnStationListReady listReady;
    private StationListFragment stationListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stations = new ArrayList<>();
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SearchFragment searchFragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, searchFragment).commit();

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof StationListFragment) {
            listReady = (StationListFragment) fragment;
        }
    }


    @Override
    public void onFragmentInteraction(String TAG, Object o) {
        if(TAG.equals("SearchFragment")){
            View view = (View)o;
            switch (view.getId()){
                case R.id.button:{
                    Log.d("msg","Nearby presssed");
                    stationListFragment = StationListFragment.newInstance(stations, "Current Location");
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, stationListFragment).commit();
                    transaction.addToBackStack(null);
                    getNearbyStations(loc,1);
                    break;
                }
                case R.id.button2:{
                    Log.d("msg","near Address");
                    SearchAddressFragment searchAddressFragment = new SearchAddressFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, searchAddressFragment);
                    transaction.commit();
                    break;
                }
                case R.id.button3:{
                    Log.d("msg","That has");
                    getNearbyStations(loc,3);
                    break;
                }

            }
        }
        else if(TAG.equals("ListFragment")){
            SearchThatHasFragment thatHasFragment = new SearchThatHasFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, thatHasFragment).addToBackStack(null);
            transaction.commit();
        }
        else if(TAG.equals("ThatHas")){
            if(o instanceof  View) {
                View view = (View) o;
                switch (view.getId()) {
                    case R.id.closeBtn: {
                        Log.d("msg", "That has close pressed");
                        getSupportFragmentManager().popBackStack();
                    }
                }
            } else if (o instanceof Boolean[]) {
                Boolean[] filters = (Boolean[]) o;
                ArrayList<Station> filteredStations = new ArrayList<>();
                filteredStations.addAll(stations);
                if (filters[0])
                    filteredStations.removeIf(s -> Integer.valueOf(s.getExtramile()) == 0);
                if (filters[1])
                    filteredStations.removeIf(s -> Integer.valueOf(s.getLoyalty()) == 0);
                if (filters[2]) filteredStations.removeIf(s -> Integer.valueOf(s.getCstore()) == 0);
                if (filters[3]) filteredStations.removeIf(s -> Integer.valueOf(s.getNfc()) == 0);
                if (filters[4])
                    filteredStations.removeIf(s -> Integer.valueOf(s.getCarwash()) == 0);
                if (filters[5]) filteredStations.removeIf(s -> Integer.valueOf(s.getDiesel()) == 0);
                listReady.onListReady(filteredStations);
                getSupportFragmentManager().popBackStack();

            }
        }

        else if(TAG.equals("searchAddress")){
            Place place = (Place)o;
            Location location = new Location("location");
            LatLng latLng = place.getLatLng();
            location.setLatitude(latLng.latitude);
            location.setLongitude(latLng.longitude);
            Bundle extra = new Bundle();
            extra.putString("address", String.valueOf(place.getAddress()));
            location.setExtras(extra);
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    latLng, 13));
            gMap.addMarker(new MarkerOptions().position(latLng));
            getNearbyStations(location,2);
        }
    }

    @Override
    public void onMapReady(final GoogleMap mMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            loc = location;
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(location.getLatitude(),
                                            location.getLongitude()), 13));
                            mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),
                                    location.getLongitude())));
                        }
                    }
                });
        gMap = mMap;
    }

    private void getNearbyStations(final Location location, final int flag) {

        AsyncHttpClient.get(RELATIVE_URL_FOR_NEAR_ME, getParams(location), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if(responseBody != null) {
                    String response = new String(responseBody);
                    Gson gson = new GsonBuilder().create();
                    StationList stationList;
                    try{
                        stationList = gson.fromJson(response, StationList.class);
                        stations = stationList.stations;
                        markMap(stations);
                        if(flag==1){
                            listReady.onListReady(stations);
                        }
                        else if(flag==2){
                            stationListFragment = StationListFragment.newInstance(stationList.stations, location.getExtras().getString("address"));
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, stationListFragment).commit();
                            transaction.addToBackStack(null);
                        }
                        else{
                            SearchThatHasFragment thatHasFragment = new SearchThatHasFragment();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, thatHasFragment).addToBackStack(null);
                            transaction.commit();
                        }


                    } catch(JsonSyntaxException jse) {
                        Log.d("jsonfail", jse.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("httpfail", ""+statusCode);
            }
        });
    }

    private RequestParams getParams(Location location) {
        RequestParams params = new RequestParams();
        params.put("lat", String.valueOf(location.getLatitude()));
        params.put("lng", String.valueOf(location.getLongitude()));
        params.put("brand", "ChevronTexaco");
        params.put("radius", 35);
        return params;
    }

    @Override
    public void onListFragmentInteraction(Station station, int flag) {
        if(flag == 1) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + station.lat + "," + station.lng);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
        else if(flag == 2){
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(Double.parseDouble(station.lat), Double.parseDouble(station.lng)), 15));
            StationDetailsFragment detailFragment = StationDetailsFragment.newInstance(station);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, detailFragment).commit();
            transaction.addToBackStack(null);
        }
        else if(flag == 3){
            gMap.animateCamera((CameraUpdateFactory.newLatLngZoom(
                    new LatLng(Double.parseDouble(station.lat), Double.parseDouble(station.lng)), 13)));
        }
    }

    public void markMap(ArrayList<Station> stations){
        for(Station station: stations){
            gMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(station.lat), Double.parseDouble(station.lng)))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.techron_logo_lockup)));
        }
    }
}