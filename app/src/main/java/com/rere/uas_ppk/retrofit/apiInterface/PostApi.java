package com.rere.uas_ppk.retrofit.apiInterface;

import com.rere.uas_ppk.retrofit.request.AddPostRequest;
import com.rere.uas_ppk.retrofit.response.AddPostResponse;
import com.rere.uas_ppk.retrofit.response.AllPostResponse;
import com.rere.uas_ppk.retrofit.response.GetPostResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostApi {
    @GET("api/1/posts")
    Call<AllPostResponse> allPosts();

    @POST("api/1/post")
    Call<AddPostResponse> addPost(@Body AddPostRequest request);

    @GET("/api/1/post/{post_id}")
    Call<GetPostResponse> getPost(@Path("post_id") int postId);

    @GET("api/1/user-posts")
    Call<AllPostResponse> getPostByUser();
}
