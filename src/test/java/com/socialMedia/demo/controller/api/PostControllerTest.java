/*
package com.socialMedia.demo.controller.api;

import com.socialMedia.demo.dto.PostDto;
import com.socialMedia.demo.model.Post;
import com.socialMedia.demo.service.JwtService;
import com.socialMedia.demo.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@WithMockUser(username = "user", roles = {"USER"})
@AutoConfigureMockMvc(addFilters = false)
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    PostService postService;

    @MockitoBean
    private JwtService jwtService;

    private Post post1;
    private PostDto postDto;
    private Post post2;
    private PostDto postDto2;
    private List<PostDto> postDtos;
    private List<Post> posts;

    @BeforeEach
    public void init() {
        post1 = new Post();
        post1.setPostId(1L);
        post1.setContent("Test content");

        postDto = new PostDto();
        postDto.setPostId(1L);
        postDto.setContent("Test content");

        post2 = new Post();
        post2.setPostId(2L);
        post2.setContent("Another test content");

        postDto2 = new PostDto();
        postDto2.setPostId(2L);
        postDto2.setContent("Another test content");

        postDtos = List.of(postDto, postDto2);
        posts = List.of(post1, post2);
    }


    @Test
    public void returnStringWithResponseEntity() throws Exception{
        mockMvc.perform(get("/api/posts/get"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from PostController"));
    }

    @Test
    public void testGetAllPosts() throws Exception {

        Mockito.when(postService.findAllPosts(0, 10)).thenReturn(postDtos);

        mockMvc.perform(get("/api/posts/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("[{\"postId\":1,\"content\":\"Test content\"}," +
                        "{\"postId\":2,\"content\":\"Another test content\"}]"));
    }

    @Test
    public void testGetPostById() throws Exception {

        Mockito.when(postService.findPostById(1L)).thenReturn(postDto);

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{\"postId\":1,\"content\":\"Test content\"}"));
    }

    @Test
    public void testAddPost() throws Exception {
        Post newPost = new Post();
        newPost.setPostId(3L);
        newPost.setContent("New post content");

        mockMvc.perform(post("/api/posts/add")
                        .contentType("application/json")
                        .content("{\"content\":\"New post content\"}"))
                .andExpect(status().isCreated());

        Mockito.verify(postService).addPost(Mockito.any(Post.class));
    }
}
*/
