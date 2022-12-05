package com.ssafy.home.user.dto;

import lombok.ToString;

public class UserAuthRequest {
    private String email, authCode;

    public UserAuthRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
