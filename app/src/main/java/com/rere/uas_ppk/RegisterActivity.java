package com.rere.uas_ppk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rere.uas_ppk.retrofit.RetrofitClient;
import com.rere.uas_ppk.retrofit.apiInterface.UserApi;
import com.rere.uas_ppk.retrofit.request.RegisterRequest;
import com.rere.uas_ppk.retrofit.response.ErrorResponse;
import com.rere.uas_ppk.retrofit.response.Response;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText, confPasswordEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameEditText = findViewById(R.id.namaEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confPasswordEditText = findViewById(R.id.konfirmPasswordEditText);

        registerButton = findViewById(R.id.registerButton);

        UserApi userApi = RetrofitClient.getInstance().create(UserApi.class);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userApi.register(new RegisterRequest(nameEditText.getText().toString(), emailEditText.getText().toString(), "Test", passwordEditText.getText().toString(), confPasswordEditText.getText().toString()))
                        .enqueue(new Callback<Response>() {
                            @Override
                            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                                if (response.isSuccessful()) {
                                    // Request was successful
                                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
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

                                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Response> call, Throwable t) {
                                System.out.println(t);
                            }
                        });
            }
        });
    }
}