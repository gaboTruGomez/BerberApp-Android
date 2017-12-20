package com.berber.berberapp.Network;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gabotrugomez on 11/29/17.
 */

public class DataManager {
    private final String API_BASE_URL_DEV = "https://test-beerapp.appspot.com/api/";
    private final String API_BASE_URL_GOLD = "test-beerapp.appspot.com/api/";

    private static DataManager dataManagerInstance;

    private APIService apiService;

    //OkHttp client to do custom requests
    private OkHttpClient okHttpClient = new OkHttpClient();

    public static DataManager getSharedInstance(){
        if(dataManagerInstance == null)
            dataManagerInstance = new DataManager();
        return dataManagerInstance;
    }

    private DataManager(){
        //Retrofit configuration
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL_DEV)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //we are using just one service (for now)
        apiService = retrofit.create(APIService.class);
    }

    public APIService getApiService() {
        return apiService;
    }

    public void getFromUrl (String url, okhttp3.Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
