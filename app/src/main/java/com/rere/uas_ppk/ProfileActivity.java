package com.rere.uas_ppk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rere.uas_ppk.adapter.UserProfileRecyclerViewAdapter;
import com.rere.uas_ppk.model.Post;
import com.rere.uas_ppk.retrofit.RetrofitClient;
import com.rere.uas_ppk.retrofit.apiInterface.PostApi;
import com.rere.uas_ppk.retrofit.apiInterface.UserApi;
import com.rere.uas_ppk.retrofit.response.AllPostResponse;
import com.rere.uas_ppk.retrofit.response.GetProfileResponse;
import com.rere.uas_ppk.util.BottomNavigationUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextView namaTextView, emailTextView, bioTextView;
    private Button editProfileButton, changePasswordButton;
    private RecyclerView postProfileRV;
    private BottomNavigationView navbarBottom;

    private ProgressBar loading;

    private UserProfileRecyclerViewAdapter postsAdapter;

    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        namaTextView = findViewById(R.id.namaTextView);
        emailTextView = findViewById(R.id.emailTextView);
        bioTextView = findViewById(R.id.bioTextView);
        editProfileButton = findViewById(R.id.editProfilButton);
        changePasswordButton = findViewById(R.id.gantiPasswordButton);
        postProfileRV = findViewById(R.id.postProfileRV);
        navbarBottom = findViewById(R.id.bottomNavbarMenu);
        loading = findViewById(R.id.loadingProgressBar);

        postsAdapter = new UserProfileRecyclerViewAdapter(this);

        postProfileRV.setAdapter(postsAdapter);
        postProfileRV.setLayoutManager(new LinearLayoutManager(this));

        navbarBottom.setSelectedItemId(R.id.menu_profile);

        BottomNavigationUtil bottomNavigation = new BottomNavigationUtil(this, navbarBottom);
        bottomNavigation.setup();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loading.setVisibility(View.VISIBLE);
        
        RetrofitClient.getAuthInstance(this).create(UserApi.class)
                .getProfile()
                .enqueue(new Callback<GetProfileResponse>() {
                    @Override
                    public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                        if (response.isSuccessful()) {
                            userId = response.body().getData().getId();

                            String name = response.body().getData().getName();
                            String email = response.body().getData().getEmail();
                            String bio = response.body().getData().getBio();

                            namaTextView.setText(name);
                            emailTextView.setText(email);
                            bioTextView.setText(bio);
                            loading.setVisibility(View.GONE);


                            changePasswordButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(ProfileActivity.this, ChangePasswordActivity.class));
                                }
                            });

                            editProfileButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
                                }
                            });
                        } else {
                            Toast.makeText(ProfileActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                        Toast.makeText(ProfileActivity.this, "Failed to fetch", Toast.LENGTH_SHORT).show();
                        Log.d("App", t.getMessage());
                    }
                });

        RetrofitClient.getAuthInstance(this).create(PostApi.class)
                .getPostByUser()
                .enqueue(new Callback<AllPostResponse>() {
                    @Override
                    public void onResponse(Call<AllPostResponse> call, Response<AllPostResponse> response) {
                        if (response.isSuccessful()) {
                            List<Post> posts = response.body().getData();

                            postsAdapter.setPosts(posts);
                            postsAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(ProfileActivity.this, "Failed to fetch", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<AllPostResponse> call, Throwable t) {
                        Toast.makeText(ProfileActivity.this, "Failed to fetch", Toast.LENGTH_SHORT).show();
                        Log.d("App", t.getMessage());
                    }
                });
    }
}