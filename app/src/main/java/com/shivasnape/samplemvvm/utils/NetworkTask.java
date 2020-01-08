package com.shivasnape.samplemvvm.utils;

import android.app.Activity;
import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * Created by ShivaSnape on 31/7/17.
 */

public class NetworkTask {

    private ApiCalls apiCalls;

    /*Build Retrofit Here*/
    public NetworkTask(Activity activity) {

        if (activity != null) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.MINUTES)
                    .readTimeout(300, TimeUnit.SECONDS)
                    .writeTimeout(1500, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            apiCalls = retrofit.create(ApiCalls.class);
        }
    }

    public NetworkTask(Context activity) {

        if (activity != null) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.MINUTES)
                    .readTimeout(300, TimeUnit.SECONDS)
                    .writeTimeout(1500, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            apiCalls = retrofit.create(ApiCalls.class);
        }
    }

    /*****************************************************************************************************************************************************************************************************************************************************************************************************************************/

    public Call<String> userLogin(HashMap<String, String> hashMap, String jsonData) {
        return apiCalls.authenticateLogin(hashMap, jsonData);
    }

    /****************************************************************************************************************************************************************************************************************************************************************************************************************/

    /*Retrofit Interface*/
    public interface ApiCalls {

        @POST("user-login")
        Call<String> authenticateLogin(@HeaderMap HashMap<String, String> hashMap, @Body String json);

    }

}