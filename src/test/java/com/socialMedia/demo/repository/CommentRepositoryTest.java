//package com.socialMedia.demo.repository;
//
//import com.socialMedia.demo.model.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ActiveProfiles("test")
//@DataJpaTest
//public class CommentRepositoryTest {
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Autowired
//    private UsersRepository usersRepository;
//
//    @Autowired
//    private PostRepository postRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    private Users user;
//
//    private Post post1;
//
//    private Comment comment1;
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
//
//        comment1 = Comment.builder()
//                .content("Test comment")
//                .author(user)
//                .parentPost(post1)
//                .build();
//    }
//
//    @Test
//    public void testSaveComment() {
//        Comment savedComment = commentRepository.save(comment1);
//
//        assertThat(savedComment).isNotNull();
//        assertThat(savedComment.getContent()).isEqualTo("Test comment");
//        assertThat(savedComment.getAuthor()).isEqualTo(user);
//        assertThat(savedComment.getParentPost()).isEqualTo(post1);
//    }
//
//    @Test
//    public void testDeleteAllByAuthor() {
//        commentRepository.save(comment1);
//        assertThat(commentRepository.findAll().size()).isEqualTo(1);
//
//        commentRepository.deleteAllByAuthor(user);
//        assertThat(commentRepository.findAll().size()).isEqualTo(0);
//    }
//
//    @Test
//    public void testFindByParentPost() {
//        commentRepository.save(comment1);
//        var comments = commentRepository.findByParentPost(post1, null);
//
//        assertThat(comments.getContent().size()).isEqualTo(1);
//        assertThat(comments.getContent().getFirst().getContent()).isEqualTo("Test comment");
//        assertThat(comments.getContent().getFirst().getAuthor()).isEqualTo(user);
//        assertThat(comments.getContent().getFirst().getParentPost()).isEqualTo(post1);
//    }
//
//    @Test
//    public void testDeleteComment(){
//        commentRepository.save(comment1);
//        assertThat(commentRepository.findAll().size()).isEqualTo(1);
//
//        commentRepository.delete(comment1);
//        assertThat(commentRepository.findAll().size()).isEqualTo(0);
//    }
//
//
//
//
//}
