package com.socialMedia.demo.repository;

import com.socialMedia.demo.model.Comment;
import com.socialMedia.demo.model.Post;
import com.socialMedia.demo.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteAllByAuthor(Users author);

    Page<Comment> findByPostAndParentCommentIsNull(Post post, Pageable pageable);
    long countByPost(Post parentPost);
    Page<Comment> findByParentComment(Comment parentComment, Pageable pageable);
    int countByParentComment(Comment comment);
}
