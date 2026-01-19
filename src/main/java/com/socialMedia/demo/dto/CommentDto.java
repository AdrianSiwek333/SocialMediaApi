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
public class CommentDto {
    private Long id;
    private String content;
    private Long userId;
    private String firstName;
    private String lastName;
    private String sex;
    private String avatarUrl;
    private LocalDateTime createdAt;
    private int replyNumber;
}
