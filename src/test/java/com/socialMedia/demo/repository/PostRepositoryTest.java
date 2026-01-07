//package com.socialMedia.demo.repository;
//
//import com.socialMedia.demo.model.ERole;
//import com.socialMedia.demo.model.Post;
//import com.socialMedia.demo.model.Role;
//import com.socialMedia.demo.model.Users;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.List;
//
//@ActiveProfiles("test")
//@DataJpaTest
//public class PostRepositoryTest {
//
//    @Autowired
//    private PostRepository postRepository;
//
//    @Autowired
//    private UsersRepository usersRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    private Post post1;
//
//    private Users user;
//
//    @BeforeEach
//    public void init() {
//        Role role = new Role(ERole.ROLE_USER);
//        roleRepository.save(role);
//
//        user = Users.builder()
//                .username("testUser")
//                .email("testmail@gmail.com")
//                .password("testPassword")
//                .role(role)
//                .build();
//        usersRepository.save(user);
//
//        post1 = Post.builder()
//                .content("Test content")
//                .author(user)
//                .build();
//        postRepository.save(post1);
//    }
//
//    @Test
//    public void testSavePost() {
//        Post savedPost = postRepository.save(post1);
//
//        assertThat(savedPost).isNotNull();
//        assertThat(savedPost.getContent()).isEqualTo("Test content");
//        assertThat(savedPost.getAuthor()).isEqualTo(user);
//    }
//
//    @Test
//    public void testDeleteAllByAuthor() {
//        List<Post> posts = postRepository.findAll();
//        assertThat(posts.size()).isEqualTo(1);
//
//        postRepository.deleteAllByAuthor(user);
//
//        posts = postRepository.findAll();
//        assertThat(posts.size()).isEqualTo(0);
//    }
//}