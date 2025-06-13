package com.socialMedia.demo.service;

import com.socialMedia.demo.model.Follow;
import com.socialMedia.demo.repository.FollowRepository;
import org.springframework.stereotype.Service;

@Service
public class FollowService {

    private final FollowRepository followRepository;

    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    public void followUser(Follow follow) {
        followRepository.save(follow);
    }

    public void unfollowUser(Follow follow) {
        followRepository.delete(follow);
    }
}
