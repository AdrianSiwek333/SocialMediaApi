package com.socialMedia.demo.service;

import com.socialMedia.demo.dto.PostDto;
import com.socialMedia.demo.exception.PostNotFoundException;
import com.socialMedia.demo.mapper.PostMapper;
import com.socialMedia.demo.model.Post;
import com.socialMedia.demo.repository.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public PostDto addPost(Post post) {
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
