package com.socialMedia.demo.service;

import com.socialMedia.demo.dto.CommentDto;
import com.socialMedia.demo.exception.PostNotFoundException;
import com.socialMedia.demo.mapper.CommentMapper;
import com.socialMedia.demo.model.Comment;
import com.socialMedia.demo.repository.CommentRepository;
import com.socialMedia.demo.repository.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper,
                          PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.postRepository = postRepository;
    }

    public void addComment(Comment comment) {
        commentRepository.save(comment);
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

        return commentMapper.mapToCommentDtoList(commentRepository.findByParentPost(
                postRepository.findById(postId).orElseThrow(
                        () -> new PostNotFoundException("Post not found")),
                PageRequest.of(page, size)).getContent());
    }
}
