package com.socialMedia.demo.controller.api;

import com.socialMedia.demo.dto.PostDto;
import com.socialMedia.demo.model.Post;
import com.socialMedia.demo.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;

    }
    @GetMapping("/all")
    public List<PostDto> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        return postService.findAllPosts(page, size);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        PostDto post = postService.findPostById(postId);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPost(@RequestBody Post post){
        postService.addPost(post);
    }

    @GetMapping("/get")
    public ResponseEntity<String> getPost() {
        return ResponseEntity.ok("Hello from PostController");
    }

}

