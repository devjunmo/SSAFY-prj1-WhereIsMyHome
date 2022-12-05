package com.ssafy.home.user.dto;

import java.sql.Timestamp;

public class UserRefreshTokenDto {
    private int userId;
    private String refreshToken;
    private Timestamp createdAt;

    public UserRefreshTokenDto() {}

    public UserRefreshTokenDto(int userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    public UserRefreshTokenDto(int userId, String refreshToken, Timestamp createdAt) {
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UserRefreshTokenDto{" +
                "userId=" + userId +
                ", refreshToken='" + refreshToken + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
