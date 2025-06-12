package com.socialMedia.demo.repository;

import com.socialMedia.demo.model.Post;
import com.socialMedia.demo.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class PostRepositoryTest {

    @Mock
    private PostRepository postRepository;

    private Post post1;

    private Users user;

    @BeforeEach
    public void init(){

        MockitoAnnotations.openMocks(this);
        post1 = Post.builder()
                .content("Test content")
                .author(user)
                .build();
        user = Users.builder()
                .username("testUser")
                .email("testmail@gmail.com")
                .password("testPassword")
                .build();
    }

    @Test
    public void testSavePost() {
        when(postRepository.save(post1)).thenReturn(post1);

        Post savedPost = postRepository.save(post1);

        assertNotNull(savedPost, "Saved post should not be null");
        verify(postRepository, times(1)).save(post1);
    }
}
