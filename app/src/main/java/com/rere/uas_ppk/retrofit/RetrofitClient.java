package com.rere.uas_ppk.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit instance;
    private static Retrofit authInstance;

    private RetrofitClient() {
    }


    public static Retrofit getInstance() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://192.168.1.4:8000/")
                    .build();
        }
        return instance;
    }

    public static Retrofit getAuthInstance(Context context) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder builder = request.newBuilder();
                        // Retrieve the token from Shared Preferences
                        SharedPreferences preferences = context.getSharedPreferences("prefs", context.MODE_PRIVATE);
                        String token = preferences.getString("token", null);
                        // Set the authorization header
                        builder.addHeader("Authorization", "Bearer " + token);
                        return chain.proceed(builder.build());
                    }
                })
                .build();

        authInstance = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://192.168.1.4:8000/")
                .client(client)
                .build();
        return authInstance;
    }

}
