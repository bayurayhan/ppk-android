package com.rere.uas_ppk.retrofit.apiInterface;

import com.rere.uas_ppk.retrofit.request.PostCommentRequest;
import com.rere.uas_ppk.retrofit.response.GetAllCommentPostResponse;
import com.rere.uas_ppk.retrofit.response.Response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommentApi {
    @GET("api/1/comments/{post_id}")
    Call<GetAllCommentPostResponse> getAllCommentPost(@Path("post_id") int postId);

    @POST("api/1/comment/{post_id}")
    Call<Response> postComment(@Path("post_id") int postId, @Body PostCommentRequest body);
}
