package com.rere.uas_ppk.model;

public class Comment {
    private int id;
    private int post_id;
    private String body;
    private User author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Comment(int id, int post_id, String body, User author) {
        this.id = id;
        this.post_id = post_id;
        this.body = body;
        this.author = author;
    }
}
