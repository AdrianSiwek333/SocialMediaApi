package com.socialMedia.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@SuperBuilder
public class Comment extends Post {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Post parentPost;

    public Comment() {
    }

    public Comment(String content, Post parentPost, Users author) {
        super(content, author);
        this.parentPost = parentPost;
    }

    public Comment(String content, Users author) {
        super(content, author);
    }

}