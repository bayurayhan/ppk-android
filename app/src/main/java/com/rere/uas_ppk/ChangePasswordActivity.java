package com.rere.uas_ppk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rere.uas_ppk.retrofit.RetrofitClient;
import com.rere.uas_ppk.retrofit.apiInterface.UserApi;
import com.rere.uas_ppk.retrofit.request.ChangePasswordRequest;
import com.rere.uas_ppk.retrofit.response.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText passwordEditText, confPassEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        passwordEditText = findViewById(R.id.newPasswordEditText);
        confPassEditText = findViewById(R.id.confNewPasswordEditText);
        submitButton = findViewById(R.id.changeButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RetrofitClient.getAuthInstance(ChangePasswordActivity.this).create(UserApi.class)
                        .changePassword(new ChangePasswordRequest(passwordEditText.getText().toString(), confPassEditText.getText().toString()))
                        .enqueue(new Callback<Response>() {
                            @Override
                            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(ChangePasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ChangePasswordActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Response> call, Throwable t) {
                                Toast.makeText(ChangePasswordActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                Log.d("App", t.getMessage());
                            }
                        });
            }
        });
    }
}