package com.example.chevron_stationfinder.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class PlacesRetrofitClient {

    private static Retrofit retrofit = null;

    static Retrofit getClient(String url) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

}