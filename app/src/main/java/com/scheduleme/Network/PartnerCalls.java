package com.scheduleme.Network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mauricio on 12/5/16.
 */

public interface PartnerCalls {
    @GET("/api/partners")
    Call<ResponseBody> getAll(
            @Query("t") String t
    );
}
