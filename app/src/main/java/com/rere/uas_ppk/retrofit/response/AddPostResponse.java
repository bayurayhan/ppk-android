package com.rere.uas_ppk.retrofit.response;

import com.rere.uas_ppk.model.Post;

public class AddPostResponse {
    private String message;
    private Post data;

    public String getMessage() {
        return message;
    }

    public AddPostResponse(String message, Post data) {
        this.message = message;
        this.data = data;
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
}
