package com.socialMedia.demo.model;

import jakarta.persistence.*;

@Entity
@IdClass(BookmarkId.class)
@Table(name = "bookmarks")
public class Bookmark {

    @ManyToOne
    @Id
    @JoinColumn(name = "post_id")
    private Post postId;

    @ManyToOne
    @Id
    @JoinColumn(name = "user_id")
    private Users userId;

    public Bookmark() {
    }

    public Bookmark(Post postId, Users userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public Post getPostId() {
        return postId;
    }

    public void setPostId(Post postId) {
        this.postId = postId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }
}
