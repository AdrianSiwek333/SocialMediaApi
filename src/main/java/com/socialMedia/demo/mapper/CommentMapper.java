package com.socialMedia.demo.mapper;

import com.socialMedia.demo.dto.CommentDto;
import com.socialMedia.demo.model.Comment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentMapper {

    public CommentDto mapToCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getAuthor().getUsername(),
                comment.getCreatedAt(),
                comment.getReplies() != null ? comment.getReplies().size() : 0
        );
    }

    public List<CommentDto> mapToCommentDtoList(List<Comment> comments) {
        return comments.stream()
                .map(this::mapToCommentDto)
                .toList();
    }
}
