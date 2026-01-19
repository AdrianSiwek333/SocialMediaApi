package com.socialMedia.demo.mapper;

import com.socialMedia.demo.dto.CommentDto;
import com.socialMedia.demo.model.Comment;
import com.socialMedia.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final CommentRepository commentRepository;

    public CommentDto mapToCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getAuthor().getId(),
                comment.getAuthor().getFirstName(),
                comment.getAuthor().getLastName(),
                comment.getAuthor().getSex(),
                comment.getAuthor().getAvatarUrl(),
                comment.getCreatedAt(),
                commentRepository.countByParentComment(comment)
        );
    }

    public List<CommentDto> mapToCommentDtoList(List<Comment> comments) {
        return comments.stream()
                .map(this::mapToCommentDto)
                .toList();
    }
}
