package com.ssafy.home.user.dto;

public class UserPasswordDto {
    private String password, newPassword;

    public UserPasswordDto() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
