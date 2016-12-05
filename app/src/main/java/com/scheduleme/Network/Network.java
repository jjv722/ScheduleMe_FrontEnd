package com.scheduleme.Network;

import retrofit2.Retrofit;

/**
 * Created by mauricio on 12/5/16.
 */
public class Network {
    private String BASE_URL = "http://192.168.0.8:8000/";
    private Retrofit retrofit = null;
    private static Network ourInstance = new Network();
    public static Network getInstance() {
        return ourInstance;
    }

    private Network() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
    }
}
