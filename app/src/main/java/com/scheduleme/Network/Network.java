package com.scheduleme.Network;

import android.app.Activity;

import com.scheduleme.Authentication;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by mauricio on 12/5/16.
 */
public class Network {
    private String BASE_URL = "http://192.168.0.9:8000/";
    private Retrofit retrofit = null;

    // calls
    private PartnerCalls partnerCalls = null;
    private AuthenticationCalls authenticationCalls = null;

    private static Network ourInstance = new Network();
    public static Network getInstance() {
        return ourInstance;
    }

    private Network() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
        partnerCalls = retrofit.create(PartnerCalls.class);
        authenticationCalls = retrofit.create(AuthenticationCalls.class);
    }

    public Call<ResponseBody> getPartners(Activity activity) {
        return partnerCalls.getAll(
                Authentication.load(activity).getToken()
        );
    }

    public Call<ResponseBody> login(String email, String password){
        return authenticationCalls.login(
                email,
                password
        );
    }

    public Call<ResponseBody> register (String name, String email, String password) {
        return authenticationCalls.register(
          name, email, password
        );
    }
}
