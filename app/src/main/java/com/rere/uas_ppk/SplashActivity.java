package com.rere.uas_ppk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.rere.uas_ppk.retrofit.RetrofitClient;
import com.rere.uas_ppk.retrofit.apiInterface.UserApi;
import com.rere.uas_ppk.retrofit.response.GetProfileResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        RetrofitClient.getAuthInstance(this).create(UserApi.class)
                .getProfile()
                .enqueue(new Callback<GetProfileResponse>() {
                    @Override
                    public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                        if (response.isSuccessful()) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 3000);

                            Log.d("App", "Response is successfull!");
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 3000);
                            Log.d("App", "Response is failed!");
                        }
                    }

                    @Override
                    public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                        Toast.makeText(SplashActivity.this, "Err: Cannot connect to server!", Toast.LENGTH_SHORT).show();
                        Log.d("App", "Retrofit error: " + t.getMessage());
                    }
                });
    }
}