package com.ssafy.home.board.dto;

import java.sql.Timestamp;

public class CommentDto {
    private int id, postId, userId;
    private String content, nickname;
    private Timestamp createAt, updatedAt;
    private boolean isDeleted;

    public CommentDto() {
    }

    public CommentDto(int id, int postId, int userId, String content, String nickname,
                      Timestamp createAt, Timestamp updatedAt, boolean isDeleted) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.nickname = nickname;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getnickname() {
        return nickname;
    }

    public void setnickname(String nickname) {
        this.nickname = nickname;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "id=" + id +
                ", postId=" + postId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", nickname='" + nickname + '\'' +
                ", createAt=" + createAt +
                ", updatedAt=" + updatedAt +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
