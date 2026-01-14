package com.socialMedia.demo.repository;

import com.socialMedia.demo.model.Post;
import com.socialMedia.demo.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.net.ContentHandler;

public interface PostRepository extends JpaRepository<Post, Long> {

    void deleteAllByAuthor(Users author);

    Page<Post> findByAuthor(Users author, Pageable pageable);

}
