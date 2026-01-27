package com.socialMedia.demo.repository;

import com.socialMedia.demo.model.Interaction;
import com.socialMedia.demo.model.Post;
import com.socialMedia.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {
    long countByPostId(Post postId);

    Optional<Interaction> findByPostIdAndUserId(Post post, Users user);

    boolean existsByPostIdAndUserId(Post postId, Users userId);

    List<Interaction> findAllByPostId(Post postId);
}
