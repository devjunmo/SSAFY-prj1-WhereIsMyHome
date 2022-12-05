package com.ssafy.home.map.dto;

import java.util.Date;
import lombok.ToString;

@ToString
public class FavoriteDto {
    int id, userId;
    Long aptCode;
    Date createdAt;

    public FavoriteDto() {
    }

    public FavoriteDto(int userId, Long aptCode) {
        this.userId = userId;
        this.aptCode = aptCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Long getAptCode() {
        return aptCode;
    }

    public void setAptCode(Long aptCode) {
        this.aptCode = aptCode;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
