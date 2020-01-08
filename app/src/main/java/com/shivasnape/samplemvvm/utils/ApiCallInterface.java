package com.shivasnape.samplemvvm.utils;

import com.google.gson.JsonElement;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface ApiCallInterface {

    @Headers("Content-Type: application/json")
    @POST("user-login")
    Observable<JsonElement> authenticateLogin(@Body String json);

}
