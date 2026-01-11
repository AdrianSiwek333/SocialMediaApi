package com.socialMedia.demo.controller.api;

import com.socialMedia.demo.dto.CommentDto;
import com.socialMedia.demo.dto.request.AddCommentRequest;
import com.socialMedia.demo.mapper.CommentMapper;
import com.socialMedia.demo.service.CommentService;
import com.socialMedia.demo.service.PostService;
import com.socialMedia.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final UsersService usersService;
    private final CommentMapper commentMapper;

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
    public CommentDto addComment(@PathVariable Long postId, @RequestBody AddCommentRequest comment) {
        return commentService.addComment(postId, comment.getContent());
    }

    @PostMapping("/{commentId}/reply")
    public CommentDto addReply(@PathVariable Long commentId, @RequestBody AddCommentRequest request) {
        return commentService.addReply(commentId, request.getContent());
    }

    @GetMapping("/{commentId}/replies")
    public List<CommentDto> getReplies(@PathVariable Long commentId, @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return commentService.getReplies(commentId, page, size);
    }

}
