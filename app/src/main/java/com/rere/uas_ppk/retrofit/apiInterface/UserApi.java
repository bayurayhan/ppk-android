package com.rere.uas_ppk.retrofit.apiInterface;

import com.rere.uas_ppk.retrofit.request.LoginRequest;
import com.rere.uas_ppk.retrofit.request.RegisterRequest;
import com.rere.uas_ppk.retrofit.response.GetProfileResponse;
import com.rere.uas_ppk.retrofit.response.LoginResponse;
import com.rere.uas_ppk.retrofit.response.Response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {
    @POST("api/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("/api/register")
    Call<Response> register(@Body RegisterRequest request);

    @GET("api/1/profile")
    Call<GetProfileResponse> getProfile();
}
