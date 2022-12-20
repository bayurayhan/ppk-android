package com.rere.uas_ppk.retrofit.request;

public class RegisterRequest {
    private String name;
    private String email;
    private String bio;
    private String password;
    private String password_confirmation;

    public RegisterRequest(String name, String email, String bio, String password, String password_confirmation) {
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.password = password;
        this.password_confirmation = password_confirmation;
    }
}
