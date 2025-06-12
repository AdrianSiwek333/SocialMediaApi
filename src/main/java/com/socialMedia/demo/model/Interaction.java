package com.socialMedia.demo.model;

import jakarta.persistence.*;

@Entity
@IdClass(InteractionId.class)
@Table(name = "interaction")
public class Interaction {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post postId;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Users userId;

    public Interaction() {
    }

    public Interaction(Post post, Users user) {
        this.postId = post;
        this.userId = user;
    }
}
