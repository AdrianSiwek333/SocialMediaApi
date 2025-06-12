package com.socialMedia.demo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@IdClass(FollowId.class)
@Table(name = "follows")
public class Follow {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private Users follower;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followed_id")
    private Users followed;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Follow() {
    }

    public Follow(Users follower, Users followed) {
        this.follower = follower;
        this.followed = followed;
    }

    public Users getFollower() {
        return follower;
    }

    public void setFollower(Users follower) {
        this.follower = follower;
    }

    public Users getFollowed() {
        return followed;
    }

    public void setFollowed(Users followed) {
        this.followed = followed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}