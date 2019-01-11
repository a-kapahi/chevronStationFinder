package com.example.chevron_stationfinder.utils;


import com.example.chevron_stationfinder.models.StationList;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface StationService {

    @GET("ws_getChevronTexacoNearMe_r2.aspx")
    Call<StationList> getStations(@QueryMap Map<String, String> params);

}