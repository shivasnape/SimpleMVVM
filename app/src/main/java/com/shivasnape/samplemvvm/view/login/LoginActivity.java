package com.shivasnape.samplemvvm.view.login;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.shivasnape.samplemvvm.R;
import com.shivasnape.samplemvvm.databinding.ActivityVendorLoginBinding;
import com.shivasnape.samplemvvm.utils.ApiResponse;
import com.shivasnape.samplemvvm.utils.Constants;
import com.shivasnape.samplemvvm.utils.NetworkTask;
import com.shivasnape.samplemvvm.utils.SessionManager;
import com.shivasnape.samplemvvm.viewmodel.LoginViewModel;


import java.util.HashMap;

import javax.inject.Inject;

import retrofit2.Response;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {

    private ActivityVendorLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Inject
    SessionManager sessionManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.window_bar));
        }

        // setting view model with lifecycle
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_vendor_login);
        binding.setLifecycleOwner(this);
        binding.setLoginModel(loginViewModel);
        loginViewModel.loginResponse().observe(this, this::consumeResponse);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);


        loginViewModel.getUser().observe(this, loginUser -> {

            String data = new Gson().toJson(loginUser);
            Timber.e("Data : " + data);

            if (loginUser.getUsername() != null) {
                if (loginUser.getUsername().equals("")) {
                    binding.edtEmail.setError("enter e-mail");
                    return;
                } else if (!loginUser.isEmailValid()) {
                    binding.edtEmail.setError("invalid e-mail");
                    return;
                }
            }

            if (loginUser.getPassword() != null) {
                if (loginUser.getPassword().equals("")) {
                    binding.edtPassword.setError("enter password");
                    return;
                }
            }

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);

            loginUser.setApi_key(Constants.API_KEY);

            String paramPass = new Gson().toJson(loginUser);

            Timber.e("Header Params : " + new Gson().toJson(hashMap));
            Timber.e("Param pass : " + paramPass);

            //call web service here
            loginViewModel.hitLoginApi(LoginActivity.this, hashMap, paramPass, Constants.LOGIN);
        });

    }

    /*
     * method to handle response
     * */
    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                progressDialog.setMessage("Loading.........");
                progressDialog.show();
                break;

            case SUCCESS:
                progressDialog.dismiss();
                renderSuccessResponse(apiResponse.data, apiResponse.type);
                break;

            case ERROR:
                progressDialog.dismiss();
                renderFailureResponse(apiResponse.error, apiResponse.type);
                Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }


    /*
     * method to handle success response
     * */
    private void renderSuccessResponse(Response<String> response, String type) {

        Timber.e("From : " + type);

        if (type.equals(Constants.LOGIN)) {

            if (response != null) {

                Timber.e("On Success");
                Timber.e("Response : %s", response.raw().request().url());
                Timber.e("Response : %s", response);
                Timber.e("Response : %s", response.body());

            } else {

                Timber.e("Response is NULL");
            }
        }
    }

    /*
     * method to handle failure response
     * */
    private void renderFailureResponse(Throwable error, String type) {

        Timber.e("From : " + type);

        if (type.equals(Constants.LOGIN)) {

            Timber.e("On Failure");
            Timber.e(error);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
