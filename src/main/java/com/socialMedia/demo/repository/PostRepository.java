package com.socialMedia.demo.repository;

import com.socialMedia.demo.model.Post;
import com.socialMedia.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    public void deleteAllByAuthor(Users author);
}
