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
import com.rere.uas_ppk.retrofit.apiInterface.PostApi;
import com.rere.uas_ppk.retrofit.request.AddPostRequest;
import com.rere.uas_ppk.retrofit.response.AddPostResponse;
import com.rere.uas_ppk.retrofit.response.ErrorResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class CreatePostActivity extends AppCompatActivity {

    private EditText judulEditText, isiEditText;
    private Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        judulEditText = findViewById(R.id.judulEditText);
        isiEditText = findViewById(R.id.isiEditText);
        postButton = findViewById(R.id.postButton);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RetrofitClient.getAuthInstance(CreatePostActivity.this).create(PostApi.class)
                        .addPost(new AddPostRequest(judulEditText.getText().toString(), isiEditText.getText().toString()))
                        .enqueue(new Callback<AddPostResponse>() {
                            @Override
                            public void onResponse(Call<AddPostResponse> call, Response<AddPostResponse> response) {
                                if (response.isSuccessful()) {
                                    // Request was successful
                                    Toast.makeText(CreatePostActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CreatePostActivity.this, HomeActivity.class);
                                    intent.putExtra("refresh", "true");
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    String message = "";
                                    // Request failed
                                    // Get the JSON response body
                                    ResponseBody errorBody = response.errorBody();
                                    try {
                                        Converter<ResponseBody, ErrorResponse> converter = RetrofitClient.getInstance(CreatePostActivity.this).responseBodyConverter(ErrorResponse.class, ErrorResponse.class.getAnnotations());

                                        ErrorResponse errorResponse = converter.convert(errorBody);

                                        // Do something with the JSON response
                                        message = errorResponse.getMessage();
                                    } catch (Exception e) {
                                        // Handle the exception
                                        System.out.println(e.getMessage());
                                    }

                                    Toast.makeText(CreatePostActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<AddPostResponse> call, Throwable t) {
                                Toast.makeText(CreatePostActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                                Log.d("App", "Retrofit error: " + t.getMessage());
                            }
                        });
            }
        });
    }
}