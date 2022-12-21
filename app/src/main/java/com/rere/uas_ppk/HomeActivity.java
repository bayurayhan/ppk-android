package com.rere.uas_ppk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.rere.uas_ppk.adapter.PostRecyclerViewAdapter;
import com.rere.uas_ppk.model.Post;
import com.rere.uas_ppk.model.User;
import com.rere.uas_ppk.retrofit.RetrofitClient;
import com.rere.uas_ppk.retrofit.apiInterface.PostApi;
import com.rere.uas_ppk.retrofit.response.AllPostResponse;
import com.rere.uas_ppk.util.BottomNavigationUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private FloatingActionButton mulaiDiskusiButton;
    private RecyclerView postRV;
    private BottomNavigationView navigation;
    protected PostRecyclerViewAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        adapter = new PostRecyclerViewAdapter(this);

        mulaiDiskusiButton = findViewById(R.id.mulaiDiskusiButton);
        navigation = findViewById(R.id.bottomNavbarMenu);

        BottomNavigationUtil bottomNavigation = new BottomNavigationUtil(this, navigation);
        bottomNavigation.setup();

        mulaiDiskusiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CreatePostActivity.class));
            }
        });

        RetrofitClient.getAuthInstance(this).create(PostApi.class)
                .allPosts().enqueue(new Callback<AllPostResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<AllPostResponse> call, @NonNull Response<AllPostResponse> response) {
                        if (!response.isSuccessful()) {
                            try {
                                assert response.errorBody() != null;
                                System.out.println(response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(HomeActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        } else {
                            assert response.body() != null;
                            postRV = findViewById(R.id.postRecyclerView);

                            List<Post> postsList = response.body().getData();

                            ArrayList<Post> posts = new ArrayList<>(postsList);

                            adapter.setPosts(posts);
                            adapter.notifyDataSetChanged();

                            postRV.setAdapter(adapter);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
                            postRV.setLayoutManager(layoutManager);

                        }

                        Toast.makeText(HomeActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<AllPostResponse> call, Throwable t) {
                        Toast.makeText(HomeActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                        Log.d("App", t.getMessage());

                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getIntent().hasExtra("refresh")) {
            RetrofitClient.getAuthInstance(this).create(PostApi.class)
                    .allPosts().enqueue(new Callback<AllPostResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<AllPostResponse> call, @NonNull Response<AllPostResponse> response) {
                            if (!response.isSuccessful()) {
                                try {
                                    assert response.errorBody() != null;
                                    System.out.println(response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(HomeActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                            } else {
                                assert response.body() != null;
                                postRV = findViewById(R.id.postRecyclerView);

                                List<Post> postsList = response.body().getData();

                                ArrayList<Post> posts = new ArrayList<>(postsList);

                                adapter.setPosts(posts);
                                adapter.notifyDataSetChanged();
                            }

                        }

                        @Override
                        public void onFailure(Call<AllPostResponse> call, Throwable t) {
                            Toast.makeText(HomeActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                            Log.d("App", t.getMessage());

                        }
                    });
        }
    }
}