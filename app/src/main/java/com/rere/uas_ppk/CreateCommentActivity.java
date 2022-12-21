package com.rere.uas_ppk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rere.uas_ppk.retrofit.RetrofitClient;
import com.rere.uas_ppk.retrofit.apiInterface.CommentApi;
import com.rere.uas_ppk.retrofit.request.PostCommentRequest;
import com.rere.uas_ppk.retrofit.response.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class CreateCommentActivity extends AppCompatActivity {

    private EditText isiEditText;
    private Button commentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);
        isiEditText = findViewById(R.id.commentEditText);
        commentButton = findViewById(R.id.commentButton);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().hasExtra("post_id")) {
            int postId = getIntent().getIntExtra("post_id", -1);

            commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RetrofitClient.getAuthInstance(CreateCommentActivity.this).create(CommentApi.class)
                            .postComment(postId, new PostCommentRequest(isiEditText.getText().toString()))
                            .enqueue(new Callback<Response>() {
                                @Override
                                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                    if(response.isSuccessful()) {
                                        Toast.makeText(CreateCommentActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(CreateCommentActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Response> call, Throwable t) {
                                    Toast.makeText(CreateCommentActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                                    Log.d("App", t.getMessage());
                                }
                            });
                }
            });
        }
    }
}