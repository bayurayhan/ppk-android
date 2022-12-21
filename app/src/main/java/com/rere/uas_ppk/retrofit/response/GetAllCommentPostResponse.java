package com.rere.uas_ppk.retrofit.response;

import com.rere.uas_ppk.model.Comment;

import java.util.List;

public class GetAllCommentPostResponse {
    private String message;
    private List<Comment> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Comment> getData() {
        return data;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }

    public GetAllCommentPostResponse(String message, List<Comment> data) {
        this.message = message;
        this.data = data;
    }
}
