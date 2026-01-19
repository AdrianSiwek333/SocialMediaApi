package com.socialMedia.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long postId;
    private String content;
    private String imageUrl;
    private Long userId;
    private String firstName;
    private String lastName;
    private String avatarUrl;
    private LocalDateTime createdAt;
    private long likesCount;
    private long commentsCount;
    private boolean isLikedByCurrentUser;
}
