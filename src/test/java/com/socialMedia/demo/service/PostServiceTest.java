/*
package com.socialMedia.demo.service;

import com.socialMedia.demo.dto.PostDto;
import com.socialMedia.demo.mapper.PostMapper;
import com.socialMedia.demo.model.Post;
import com.socialMedia.demo.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostServiceTest {

    @Mock
    private PostMapper postMapper;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    public PostServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    private Post post;

    private PostDto postDto;

    @BeforeEach
    public void init() {
        post = new Post();
        post.setPostId(1L);
        post.setContent("Test content");
        postDto = new PostDto();
        postDto.setContent("Test content");
    }

    @Test
    void TestService_testAddPost() {

        when(postRepository.save(Mockito.any(Post.class))).thenReturn(post);
        when(postMapper.mapToPostDto(post)).thenReturn(postDto);

        PostDto savedPost = postService.addPost(post);
        Assertions.assertThat(savedPost).isNotNull();
    }

    @Test
    void TestService_testRemovePost() {
        postService.removePost(post);

        verify(postRepository, times(1)).delete(post);
    }

    @Test
    void TestService_testFindPostById_ReturnsPostDto() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postMapper.mapToPostDto(post)).thenReturn(postDto);

        PostDto result = postService.findPostById(1L);

        assertEquals("Test content", result.getContent());
        verify(postRepository, times(1)).findById(1L);
        verify(postMapper, times(1)).mapToPostDto(post);
    }

    @Test
    void TestService_testFindPostByIdRaw_ReturnsPost(){
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        Post result = postService.findPostByIdRaw(1L);

        assertEquals("Test content", result.getContent());
        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    void TestService_testFindAllPosts(){
        Post post1 = new Post();
        post1.setPostId(2L);
        post1.setContent("Test content 2");

        PostDto postDto1 = new PostDto();
        postDto1.setContent("Test content 2");

        when(postRepository.findAll()).thenReturn(List.of(post, post1));
        when(postMapper.mapToPostDto(post)).thenReturn(postDto);
        when(postMapper.mapToPostDto(post1)).thenReturn(postDto1);

    }
}*/
