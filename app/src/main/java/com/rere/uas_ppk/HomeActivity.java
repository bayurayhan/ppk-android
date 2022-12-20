package com.rere.uas_ppk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
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

    public void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("token");

        editor.apply();

        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mulaiDiskusiButton = findViewById(R.id.mulaiDiskusiButton);
        navigation = findViewById(R.id.bottomNavbarMenu);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_logout) {
                    logout();
                }
                return true;
            }
        });


        PostApi postApi = RetrofitClient.getAuthInstance(this).create(PostApi.class);

        postApi.allPosts().enqueue(new Callback<AllPostResponse>() {
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

                    ArrayList<Post> posts = new ArrayList<Post>(postsList);

//                    posts.sort(new Comparator<Post>() {
//                        @Override
//                        public int compare(Post o1, Post o2) {
//                            return o1.getUpdated_at() > o2.getUpdated_at();
//                        }
//                    });

                    PostRecyclerViewAdapter adapter = new PostRecyclerViewAdapter(posts);

                    postRV.setAdapter(adapter);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
                    postRV.setLayoutManager(layoutManager);
                    Toast.makeText(HomeActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AllPostResponse> call, Throwable t) {

            }
        });


    }
}