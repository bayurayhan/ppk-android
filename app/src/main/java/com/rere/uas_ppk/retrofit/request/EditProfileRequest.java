package com.rere.uas_ppk.retrofit.request;

public class EditProfileRequest {
    private String name;
    private String bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public EditProfileRequest(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }
}
