package com.rere.uas_ppk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rere.uas_ppk.retrofit.RetrofitClient;
import com.rere.uas_ppk.retrofit.apiInterface.UserApi;
import com.rere.uas_ppk.retrofit.request.LoginRequest;
import com.rere.uas_ppk.retrofit.response.ErrorResponse;
import com.rere.uas_ppk.retrofit.response.GetProfileResponse;
import com.rere.uas_ppk.retrofit.response.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    UserApi userApi;

    protected void login(String token) {
        editor.putString("token", token);
        editor.apply();
        checkIfLogin();
    }

    protected void checkIfLogin() {
        if (preferences.contains("token")) {
            RetrofitClient.getAuthInstance(this)
                    .create(UserApi.class)
                    .getProfile()
                    .enqueue(new Callback<GetProfileResponse>() {
                        @Override
                        public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<GetProfileResponse> call, Throwable t) {

                        }
                    });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        editor = preferences.edit();
        userApi = RetrofitClient.getInstance().create(UserApi.class);

        checkIfLogin();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userApi.login(new LoginRequest(emailEditText.getText().toString(), passwordEditText.getText().toString()))
                        .enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                if (response.isSuccessful()) {
                                    // Request was successful
                                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    login(response.body().getToken());
                                } else {
                                    String message = "";
                                    // Request failed
                                    // Get the JSON response body
                                    ResponseBody errorBody = response.errorBody();
                                    try {
                                        Converter<ResponseBody, ErrorResponse> converter = RetrofitClient.getInstance().responseBodyConverter(ErrorResponse.class, ErrorResponse.class.getAnnotations());

                                        ErrorResponse errorResponse = converter.convert(errorBody);

                                        // Do something with the JSON response
                                        message = errorResponse.getMessage();
                                    } catch (Exception e) {
                                        // Handle the exception
                                        System.out.println(e.getMessage());
                                    }

                                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {
                                System.out.println(t);
                            }
                        });
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}