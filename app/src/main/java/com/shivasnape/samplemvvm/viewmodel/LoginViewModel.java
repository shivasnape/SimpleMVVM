package com.shivasnape.samplemvvm.viewmodel;

import android.app.Activity;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.shivasnape.samplemvvm.model.LoginUser;
import com.shivasnape.samplemvvm.utils.ApiResponse;
import com.shivasnape.samplemvvm.utils.NetworkTask;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();

    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();
    private MutableLiveData<LoginUser> userMutableLiveData;


    /* public LoginViewModel(Repository repository) {
         this.repository = repository;
     }
 */
    public MutableLiveData<ApiResponse> loginResponse() {
        return responseLiveData;
    }

    public MutableLiveData<LoginUser> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick(View view) {

        LoginUser loginUser = new LoginUser(EmailAddress.getValue(), Password.getValue());
        userMutableLiveData.setValue(loginUser);

    }

    /*
     * method to call normal login api
     * */
    public void hitLoginApi(Activity activity, HashMap<String, String> hashMap, String paramPass, String type) {

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

