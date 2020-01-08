package com.shivasnape.samplemvvm.view.register;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.shivasnape.samplemvvm.R;
import com.shivasnape.samplemvvm.databinding.ActivityVendorSignupBinding;
import com.shivasnape.samplemvvm.model.RegisterUser;
import com.shivasnape.samplemvvm.utils.ApiResponse;
import com.shivasnape.samplemvvm.utils.Constants;
import com.shivasnape.samplemvvm.viewmodel.RegisterViewModel;


import java.util.HashMap;

import retrofit2.Response;
import timber.log.Timber;

public class SignUpActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;
    private ActivityVendorSignupBinding binding;

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

        //setting view model with lifecycle
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        binding = DataBindingUtil.setContentView(SignUpActivity.this, R.layout.activity_vendor_signup);
        binding.setLifecycleOwner(this);
        binding.setRegisterModel(registerViewModel);
        registerViewModel.registrationResponse().observe(this, this::consumeResponse);

//        appUtil = new AppUtil(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

//        appUtil.boldTextViewFont(binding.txt1, binding.txt4);
//        appUtil.regularEdittextFont(binding.edtCompanyName, binding.edtMobile, binding.edtEmail, binding.edtPassword);
//        appUtil.regularTextViewFont(binding.txt3);
//        appUtil.boldButtonFont(binding.btnRegister);

        registerViewModel.getUserDetails().observe(this, new Observer<RegisterUser>() {
            @Override
            public void onChanged(@Nullable RegisterUser registerUser) {

                if (registerUser != null) {
                    if (registerUser.getmCompanyName() != null) {
                        if (registerUser.getmCompanyName().equals("")) {
                            binding.edtCompanyName.setError("enter company name");
                            return;
                        }
                    }

                    if (registerUser.getmEmail() != null) {
                        if (registerUser.getmEmail().equals("")) {
                            binding.edtEmail.setError("enter e-mail address");
                            return;
                        } else {
                            if (!registerUser.isEmailValid()) {
                                binding.edtEmail.setError("invalid e-mail");
                                return;
                            }
                        }
                    }

                    if (registerUser.getmMobile() != null) {
                        if (registerUser.getmMobile().equals("")) {
                            binding.edtMobile.setError("enter mobile number");
                            return;
                        } else {
                            if (!registerUser.isValidMobile()) {
                                binding.edtMobile.setError("invalid mobile number");
                                return;
                            }
                        }
                    }

                    if (registerUser.getmPassword() != null) {
                        if (!registerUser.isPasswordLengthGreaterThan5()) {
                            binding.edtPassword.setError("password length should be minimum 5");
                            return;
                        }
                    }

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);

                    registerUser.setApi_key(Constants.API_KEY);

                    String paramPass = new Gson().toJson(registerUser);

                    Timber.e("Header Params : " + new Gson().toJson(hashMap));
                    Timber.e("Param Pass : " + paramPass);

                  /*  if (appUtil.isNetworkAvailable()) {
                        registerViewModel.userRegistration(SignUpActivity.this,hashMap,paramPass,Constants.REGISTER);
                    } else {
                        appUtil.showToast(getResources().getString(R.string.no_internet_error), false);
                    }*/

                }

            }
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

        if (type.equals(Constants.REGISTER)) {

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

        if (type.equals(Constants.REGISTER)) {

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
