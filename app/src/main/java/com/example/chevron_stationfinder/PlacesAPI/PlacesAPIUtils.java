package com.example.chevron_stationfinder.PlacesAPI;

public class PlacesAPIUtils {

    private static final String API_URL = "https://maps.googleapis.com/maps/api/place/";

    private PlacesAPIUtils() {
    }

    public static PlacesService getPlacesService() {
        return PlacesRetrofitClient.getClient(API_URL).create(PlacesService.class);
    }

}