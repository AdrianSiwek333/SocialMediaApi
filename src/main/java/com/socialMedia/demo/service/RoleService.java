package com.socialMedia.demo.service;

import com.socialMedia.demo.model.ERole;
import com.socialMedia.demo.model.Role;
import com.socialMedia.demo.repository.RoleRepository;
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

    public Role saveRole(Role role)
    {
        return roleRepository.save(role);
    }
}
