package com.rere.uas_ppk.retrofit.response;

public class LoginResponse extends Response {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginResponse(String message, String token) {
        super(message);
        this.token = token;
    }
}
