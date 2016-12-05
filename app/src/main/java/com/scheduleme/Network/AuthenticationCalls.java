package com.scheduleme.Network;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mauricio on 12/3/16.
 */

public interface AuthenticationCalls {
    @FormUrlEncoded
    @POST("api/login")
    Call<ResponseBody> login(
            @Field("u") String u,
            @Field("p") String p
    );

    @FormUrlEncoded
    @POST("api/register")
    Call<ResponseBody> register(
            @Field("n") String n,
            @Field("u") String u,
            @Field("p") String p
    );
}
