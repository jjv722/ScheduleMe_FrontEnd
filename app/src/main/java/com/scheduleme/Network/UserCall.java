package com.scheduleme.Network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mauricio on 12/5/16.
 */

public interface UserCall {
    @GET("/api/users")
    Call<ResponseBody> get(
            @Query("t") String t
    );
}
