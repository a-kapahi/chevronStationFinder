package com.example.chevron_stationfinder.APIs;

public class APIUtils {

    private static final String API_URL = "https://chevronwithtechrondev.azurewebsites.net/api/app/techron2go/";

    private APIUtils() {
    }

    public static StationService getStationService() {
        return RetrofitClient.getClient(API_URL).create(StationService.class);
    }

}