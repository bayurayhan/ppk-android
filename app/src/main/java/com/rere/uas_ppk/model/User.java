package com.rere.uas_ppk.model;

public class User {
    private final int id;
    private String name;
    private String email;
    private String bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public User(int id, String name, String email, String bio) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bio = bio;
    }
}
