package com.rere.uas_ppk.retrofit.response;

import com.rere.uas_ppk.model.Post;

public class GetPostResponse {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Post getData() {
        return data;
    }

    public void setData(Post data) {
        this.data = data;
    }

    public GetPostResponse(String message, Post data) {
        this.message = message;
        this.data = data;
    }

    private Post data;


}
