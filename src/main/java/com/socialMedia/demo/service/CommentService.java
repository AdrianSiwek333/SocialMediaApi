package com.socialMedia.demo.service;

import com.socialMedia.demo.dto.CommentDto;
import com.socialMedia.demo.exception.PostNotFoundException;
import com.socialMedia.demo.mapper.CommentMapper;
import com.socialMedia.demo.model.Comment;
import com.socialMedia.demo.model.Post;
import com.socialMedia.demo.model.Users;
import com.socialMedia.demo.repository.CommentRepository;
import com.socialMedia.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final UsersService usersService;

    public CommentDto addComment(Long postId, String content) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersService.findUserEntityByEmail(email);

        Post parent = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        Comment comment = Comment.builder()
                .content(content)
                .author(user)
                .post(parent)
                .parentComment(null)
                .build();
        commentRepository.save(comment);

        return commentMapper.mapToCommentDto(comment);
    }

    public CommentDto addReply(Long parentCommentId, String content) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersService.findUserEntityByEmail(email);

        Comment parent = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        Comment reply = Comment.builder()
                .content(content)
                .author(user)
                .post(parent.getPost())
                .parentComment(parent)
                .build();

        return commentMapper.mapToCommentDto(commentRepository.save(reply));
    }

    public List<CommentDto> getReplies(Long parentId, int page, int size) {
        Comment parent = commentRepository.findById(parentId).orElseThrow();
        return commentRepository.findByParentComment(parent, PageRequest.of(page, size)).stream()
                .map(commentMapper::mapToCommentDto).toList();
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public CommentDto getCommentById(Long commentId) {
        return commentMapper.mapToCommentDto(
                commentRepository.findById(commentId).orElseThrow(
                        () -> new PostNotFoundException("Comment not found")
                )
        );
    }

    public List<CommentDto> getCommentsByPostId(Long postId, int page, int size) {

        if (postId == null) {
            throw new IllegalArgumentException("Post ID cannot be null");
        }

        return commentMapper.mapToCommentDtoList(commentRepository.findByPostAndParentCommentIsNull(
                postRepository.findById(postId).orElseThrow(
                        () -> new PostNotFoundException("Post not found")),
                PageRequest.of(page, size)).getContent());
    }
}
