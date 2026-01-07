
package com.socialMedia.demo.config;

import com.socialMedia.demo.model.ERole;
import com.socialMedia.demo.model.Role;
import com.socialMedia.demo.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception{
        for (ERole role : ERole.values()) {
            if (roleRepository.findByName(role).isEmpty()) {
                roleRepository.save(new Role(role));
            }
        }
    }
}

