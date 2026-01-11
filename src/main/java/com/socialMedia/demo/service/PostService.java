package com.socialMedia.demo.service;

import com.socialMedia.demo.dto.PostDto;
import com.socialMedia.demo.dto.request.AddPostRequest;
import com.socialMedia.demo.exception.PostNotFoundException;
import com.socialMedia.demo.mapper.PostMapper;
import com.socialMedia.demo.model.Post;
import com.socialMedia.demo.model.Users;
import com.socialMedia.demo.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UsersService usersService;

    public PostDto addPost(AddPostRequest addPostRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersService.findUserEntityByEmail(username);
        Post post = new Post();
        post.setAuthor(user);
        post.setContent(addPostRequest.getContent());

        postRepository.save(post);
        return postMapper.mapToPostDto(post);
    }

    public void removePost(Post post) {
        postRepository.delete(post);
    }

    public PostDto findPostById(Long postId) {
        return postMapper.mapToPostDto(postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Post not found")
        ));
    }

    public Post findPostByIdRaw(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Post not found")
        );
    }

    public List<PostDto> findAllPosts(int page, int size) {
        return postRepository.findAll(PageRequest.of(page, size)).getContent()
                .stream()
                .map(postMapper::mapToPostDto)
                .toList();
    }
}
