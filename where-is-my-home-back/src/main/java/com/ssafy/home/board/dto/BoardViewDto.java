package com.ssafy.home.board.dto;

import java.sql.Timestamp;

public class BoardViewDto {
    private int id;
    private int userId;
    private String title;
    private String content;
    private int hit;
    private String type;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String nickname;
    private boolean isDeleted;

    public BoardViewDto() {
    }

    public BoardViewDto(int id, int userId, String title, String content, int hit,
                        String type, Timestamp createdAt, Timestamp updatedAt, String nickname, boolean isDeleted) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.hit = hit;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.nickname = nickname;
        this.isDeleted = isDeleted;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "BoardViewDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", hit=" + hit +
                ", type='" + type + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", nickname='" + nickname + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
