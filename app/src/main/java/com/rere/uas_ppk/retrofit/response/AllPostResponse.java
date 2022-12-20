package com.rere.uas_ppk.retrofit.response;

import com.rere.uas_ppk.model.Post;

import java.util.List;

public class AllPostResponse {
    private String message;
    private List<Post> data;

    public String getMessage() {
        return message;
    }

    public List<Post> getData() {
        return data;
    }

    public AllPostResponse(String message, List<Post> data) {
        this.message = message;
        this.data = data;
    }
}
