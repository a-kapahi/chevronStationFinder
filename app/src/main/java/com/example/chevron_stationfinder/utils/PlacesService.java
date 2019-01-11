package com.example.chevron_stationfinder.utils;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface PlacesService {

    @GET("autocomplete/json")
    Call<JsonObject> getPlaces(@QueryMap Map<String, String> params);

    @GET("details/json")
    Call<JsonObject> getPlaceDetails(@QueryMap Map<String, String> params);

}