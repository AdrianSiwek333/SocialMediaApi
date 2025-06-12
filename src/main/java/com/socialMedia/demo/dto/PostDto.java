package com.socialMedia.demo.dto;

import java.time.LocalDateTime;

public class PostDto {

    private Long postId;
    private String content;
    private String username;
    private LocalDateTime createdAt;

    public PostDto() {
    }

    public PostDto(Long postId, String content, String username, LocalDateTime createdAt) {
        this.postId = postId;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
