package com.socialMedia.demo.model;

import java.util.Objects;

public class FollowId {

    private Long follower;
    private Long followed;

    public FollowId() {
    }

    public FollowId(Long userId, Long followingId) {
        this.followed = userId;
        this.follower = followingId;
    }

    public Long getFollowed() {
        return followed;
    }

    public void setFollowed(Long followed) {
        this.followed = followed;
    }

    public Long getFollower() {
        return follower;
    }

    public void setFollower(Long follower) {
        this.follower = follower;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        FollowId that = (FollowId) o;
        return Objects.equals(followed, that.followed) &&
                Objects.equals(follower, that.follower);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followed, follower);
    }
}