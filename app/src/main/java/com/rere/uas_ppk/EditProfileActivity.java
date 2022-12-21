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
import com.rere.uas_ppk.retrofit.request.EditProfileRequest;
import com.rere.uas_ppk.retrofit.response.EditProfileResponse;
import com.rere.uas_ppk.retrofit.response.GetProfileResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private EditText nameEditText, bioEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nameEditText = findViewById(R.id.namaEditText);
        bioEditText = findViewById(R.id.bioEditText);
        submitButton = findViewById(R.id.ubahButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitClient.getAuthInstance(EditProfileActivity.this).create(UserApi.class)
                        .editProfile(new EditProfileRequest(nameEditText.getText().toString(), bioEditText.getText().toString()))
                        .enqueue(new Callback<EditProfileResponse>() {
                            @Override
                            public void onResponse(Call<EditProfileResponse> call, Response<EditProfileResponse> response) {
                                if(response.isSuccessful()) {
                                    Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(EditProfileActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<EditProfileResponse> call, Throwable t) {
                                Toast.makeText(EditProfileActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                                Log.d("App", t.getMessage());
                            }
                        });
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        RetrofitClient.getAuthInstance(this).create(UserApi.class)
                .getProfile()
                .enqueue(new Callback<GetProfileResponse>() {
                    @Override
                    public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                        if(response.isSuccessful()) {
                            nameEditText.setText(response.body().getData().getName());
                            bioEditText.setText(response.body().getData().getBio());
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                        Toast.makeText(EditProfileActivity.this, "Failed to fetch", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}