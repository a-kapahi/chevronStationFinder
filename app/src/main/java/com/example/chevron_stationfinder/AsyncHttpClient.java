package com.example.chevron_stationfinder;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AsyncHttpClient {

    private static com.loopj.android.http.AsyncHttpClient client = null;
    private static final String BASE_URL = "https://chevronwithtechrondev.azurewebsites.net/api/app/techron2go/";
    public static void get(String relativeURL, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        getAsyncHttpClient().get(getAbsoluteUrl(relativeURL), params, responseHandler);
    }

    public static void post(String relativeURL, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        getAsyncHttpClient().post(getAbsoluteUrl(relativeURL), params, responseHandler);
    }

    // singleton method to get AsyncHttpClient
    private static com.loopj.android.http.AsyncHttpClient getAsyncHttpClient() {
        if(client == null) {
            client = new com.loopj.android.http.AsyncHttpClient();
            client.getHttpClient().getParams().setParameter("http.protocol.allow-circular-redirects", true);
        }
        return client;
    }

    private static String getAbsoluteUrl(String relativeURL) {
        return BASE_URL + relativeURL;
    }
}
