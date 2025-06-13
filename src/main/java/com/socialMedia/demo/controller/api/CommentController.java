package com.socialMedia.demo.controller.api;

import com.socialMedia.demo.dto.CommentDto;
import com.socialMedia.demo.mapper.CommentMapper;
import com.socialMedia.demo.model.Comment;
import com.socialMedia.demo.service.CommentService;
import com.socialMedia.demo.service.PostService;
import com.socialMedia.demo.service.UsersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final UsersService usersService;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, PostService postService, UsersService usersService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.postService = postService;
        this.usersService = usersService;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/{commentId}")
    public CommentDto getCommentById(@PathVariable Long commentId) {
        return commentService.getCommentById(commentId);
    }

    @GetMapping("/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable Long postId, @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        return commentService.getCommentsByPostId(postId, page, size);
    }

    @PostMapping("/{postId}/addComment")
    public void addComment(@PathVariable Long postId, @RequestBody Comment comment) {
        comment.setParentPost(postService.findPostByIdRaw(postId));
        comment.setAuthor(usersService.findUserEntityById(1L)); // Assuming the author is set to a default user for now
        commentService.addComment(comment);
    }



}
