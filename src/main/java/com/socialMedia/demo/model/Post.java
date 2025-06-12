package com.socialMedia.demo.model;

import com.socialMedia.demo.dto.CommentDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "post")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(length = 255, nullable = false)
    private String content;

    @OneToMany(mappedBy = "parentPost", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private List<Comment> childComments = new ArrayList<>();

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Users author;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }


    public Post(String content, Users author) {
        this.content = content;
        this.author = author;
    }

    public List<CommentDto> getChildCommentsDto() {
        List<CommentDto> commentDto = new ArrayList<>();
        for (Comment comment : childComments) {
            commentDto.add(new CommentDto(comment.getPostId(), comment.getContent(),
                    comment.getAuthor().getUsername(), comment.getCreatedAt()));
        }
        return commentDto;
    }

}