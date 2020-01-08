package com.shivasnape.samplemvvm.utils;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import retrofit2.Response;

import static com.shivasnape.samplemvvm.utils.Status.ERROR;
import static com.shivasnape.samplemvvm.utils.Status.LOADING;
import static com.shivasnape.samplemvvm.utils.Status.SUCCESS;


public class ApiResponse {

    public final Status status;

    @Nullable
    public final Response<String> data;

    @Nullable
    public final String type;

    @Nullable
    public final Throwable error;

    private ApiResponse(Status status, @Nullable Response<String> data, @Nullable Throwable error,@Nullable String type) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.type = type;
    }

    public static ApiResponse loading() {
        return new ApiResponse(LOADING, null, null,null);
    }

    public static ApiResponse success(@NonNull Response<String> data,@NonNull String type) {
        return new ApiResponse(SUCCESS, data, null,type);
    }

    public static ApiResponse error(@NonNull Throwable error, String type) {
        return new ApiResponse(ERROR, null, error,type);
    }

}
