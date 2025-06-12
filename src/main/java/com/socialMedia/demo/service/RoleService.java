/*
package com.socialMedia.demo.service;

import com.example.twitterLike.model.ERole;
import com.example.twitterLike.model.Role;
import com.example.twitterLike.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(ERole name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
    }
}
*/
