package com.socialMedia.demo.repository;

import com.socialMedia.demo.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
