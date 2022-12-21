package com.rere.uas_ppk.retrofit.request;

public class PostCommentRequest {
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public PostCommentRequest(String body) {
        this.body = body;
    }
}
