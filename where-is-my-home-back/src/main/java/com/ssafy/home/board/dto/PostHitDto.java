package com.ssafy.home.board.dto;

public class PostHitDto {
    private int id, postId, userId;

    public PostHitDto() {
    }

    public PostHitDto(int id, int postId, int userId) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
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

    @Override
    public String toString() {
        return "PostHitDto{" +
                "id=" + id +
                ", postId=" + postId +
                ", userId=" + userId +
                '}';
    }
}
