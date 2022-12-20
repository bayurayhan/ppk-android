package com.rere.uas_ppk.model;

public class Post {
    private final int id;
    private String title;
    private String body;
    private User author;
    private String updated_at;

    public Post(int id, String title, String body, User author, String updated_at) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.author = author;
        this.updated_at = updated_at;
    }

    public String getTitle() {
        return title;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
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

}
