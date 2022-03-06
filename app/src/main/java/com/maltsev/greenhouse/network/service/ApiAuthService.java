package com.maltsev.greenhouse.network.service;

import com.maltsev.greenhouse.network.model.AccessToken;
import com.maltsev.greenhouse.network.model.LoginRequest;
import com.maltsev.greenhouse.network.model.MessageResponse;
import com.maltsev.greenhouse.network.model.SignupRequest;
import com.maltsev.greenhouse.network.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiAuthService {

    @POST("auth/signin")
    Call<User> login(@Body LoginRequest loginRequest);

    @POST("auth/signup")
    Call<MessageResponse> signup(@Body SignupRequest signupRequest);

    @POST("auth/refreshtoken")
    Call<AccessToken> refresh(@Body AccessToken token);

    @GET("test")
    Call<List<String>> getTests(@Header("Authorization") String authToken);

}
