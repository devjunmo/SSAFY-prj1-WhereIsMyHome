package com.ssafy.home.user.dto;

import java.sql.Timestamp;

public class UserDto {
    private Integer id;
    private String email, password, nickname, role;
    private Timestamp createdAt, updatedAt;
    private Boolean isVerified;

    public UserDto() {}

    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserDto(Integer id, String email, String password, String nickname, String role,
        Timestamp createdAt, Timestamp updatedAt, Boolean isVerified) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isVerified = isVerified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean isVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickname + '\'' +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isVerified=" + isVerified +
                '}';
    }
}
