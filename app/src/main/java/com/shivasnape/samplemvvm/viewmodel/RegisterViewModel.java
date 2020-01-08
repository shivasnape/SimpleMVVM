package com.shivasnape.samplemvvm.viewmodel;


import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shivasnape.samplemvvm.model.RegisterUser;
import com.shivasnape.samplemvvm.utils.ApiResponse;
import com.shivasnape.samplemvvm.utils.NetworkTask;
import com.shivasnape.samplemvvm.view.login.LoginActivity;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterViewModel extends ViewModel {

    public MutableLiveData<String> companyName = new MutableLiveData<>();
    public MutableLiveData<String> mobileNumber = new MutableLiveData<>();
    public MutableLiveData<String> emailAddress = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    private MutableLiveData<RegisterUser> userMutableLiveData;

    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();

    public MutableLiveData<RegisterUser> getUserDetails() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public MutableLiveData<ApiResponse> registrationResponse() {
        return responseLiveData;
    }

    public void onClick(View view) {

        RegisterUser registerUser = new RegisterUser(companyName.getValue(), mobileNumber.getValue(), emailAddress.getValue(), password.getValue());
        userMutableLiveData.setValue(registerUser);

    }

    public void goToSignIn(View v) {

        Intent intent = new Intent(v.getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        v.getContext().startActivity(intent);

    }

    /*
     * method to call normal login api
     * */
    public void userRegistration(Activity activity, HashMap<String, String> hashMap, String paramPass, String type) {

        //show loading
        responseLiveData.setValue(ApiResponse.loading());

        NetworkTask networkTask = new NetworkTask(activity);

        Call<String> response = networkTask.userLogin(hashMap, paramPass);

        response.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                responseLiveData.setValue(ApiResponse.success(response, type));
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                responseLiveData.setValue(ApiResponse.error(t, type));
            }
        });

    }

}
