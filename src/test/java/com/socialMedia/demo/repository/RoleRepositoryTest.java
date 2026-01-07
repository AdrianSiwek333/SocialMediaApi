/*
package com.socialMedia.demo.repository;

import com.socialMedia.demo.model.ERole;
import com.socialMedia.demo.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testAddRole(){
        Role role = new Role(ERole.ROLE_USER);
        roleRepository.save(role);
        Role foundRole = roleRepository.findByName(ERole.ROLE_USER).orElse(null);
        assert foundRole != null;
    }

    @Test
    public void DeleteRole(){
        Role role = new Role(ERole.ROLE_USER);
        roleRepository.save(role);
        Role foundRole = roleRepository.findByName(ERole.ROLE_USER).orElse(null);
        assert foundRole != null;
        roleRepository.delete(foundRole);
        Role deletedRole = roleRepository.findByName(ERole.ROLE_USER).orElse(null);
        assert deletedRole == null;
    }

    @Test
    public void testFindByName(){
        Role role = new Role(ERole.ROLE_USER);
        roleRepository.save(role);
        Role foundRole = roleRepository.findByName(ERole.ROLE_USER).orElse(null);
        assert foundRole != null;
        assert foundRole.getName() == ERole.ROLE_USER;
    }
}
*/
