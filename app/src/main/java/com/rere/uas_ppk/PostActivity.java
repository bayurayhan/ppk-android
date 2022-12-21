package com.rere.uas_ppk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rere.uas_ppk.adapter.CommentRecyclerViewAdapter;
import com.rere.uas_ppk.model.Comment;
import com.rere.uas_ppk.retrofit.RetrofitClient;
import com.rere.uas_ppk.retrofit.apiInterface.CommentApi;
import com.rere.uas_ppk.retrofit.apiInterface.PostApi;
import com.rere.uas_ppk.retrofit.response.GetAllCommentPostResponse;
import com.rere.uas_ppk.retrofit.response.GetPostResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {
    private TextView authorTextView, judulTextView, isiTextView;
    private RecyclerView commentRV;
    private FloatingActionButton addCommentButton;
    CommentRecyclerViewAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        authorTextView = findViewById(R.id.authorPostTextView);
        judulTextView = findViewById(R.id.judulTextView);
        isiTextView = findViewById(R.id.isiTextView);
        commentRV = findViewById(R.id.commentRV);
        addCommentButton = findViewById(R.id.addCommentButton);

        commentAdapter = new CommentRecyclerViewAdapter();
        commentRV.setAdapter(commentAdapter);
        commentRV.setLayoutManager(new LinearLayoutManager(PostActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(getIntent().hasExtra("post_id")) {
            RetrofitClient.getAuthInstance(this).create(PostApi.class)
                    .getPost(getIntent().getIntExtra("post_id", -1))
                    .enqueue(new Callback<GetPostResponse>() {
                        @Override
                        public void onResponse(Call<GetPostResponse> call, Response<GetPostResponse> response) {
                            if(response.isSuccessful()) {
                                String title = response.body().getData().getTitle();
                                String body = response.body().getData().getBody();
                                String author = response.body().getData().getAuthor().getName();

                                judulTextView.setText(title);
                                isiTextView.setText(body);
                                authorTextView.setText(author);
                            } else {
                                Toast.makeText(PostActivity.this, "Error!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetPostResponse> call, Throwable t) {
                            Toast.makeText(PostActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                            Log.d("App", t.getMessage());
                        }
                    });
        RetrofitClient.getAuthInstance(this).create(CommentApi.class)
                .getAllCommentPost(getIntent().getIntExtra("post_id", -1))
                .enqueue(new Callback<GetAllCommentPostResponse>() {
                    @Override
                    public void onResponse(Call<GetAllCommentPostResponse> call, Response<GetAllCommentPostResponse> response) {
                        if(response.isSuccessful()) {
                            List<Comment> comments = response.body().getData();

                            commentAdapter.setComments(comments);

                            commentAdapter.notifyDataSetChanged();

                            addCommentButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(PostActivity.this, CreateCommentActivity.class).putExtra("post_id", getIntent().getIntExtra("post_id", -1)));
                                }
                            });
                        } else {
                            Toast.makeText(PostActivity.this, "Error!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAllCommentPostResponse> call, Throwable t) {
                        Toast.makeText(PostActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        Log.d("App", t.getMessage());
                    }
                });
        }
    }
}