package com.rere.uas_ppk.retrofit.request;

public class ChangePasswordRequest {
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public ChangePasswordRequest(String password, String password_confirmation) {
        this.password = password;
        this.password_confirmation = password_confirmation;
    }

    private String password_confirmation;
}
