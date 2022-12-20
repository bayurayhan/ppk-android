package com.rere.uas_ppk.retrofit.response;

import com.rere.uas_ppk.model.User;

public class GetProfileResponse {
    private String message;
    private User data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public GetProfileResponse(String message, User data) {
        this.message = message;
        this.data = data;
    }
}
