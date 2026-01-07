//package com.socialMedia.demo.repository;
//
//import com.socialMedia.demo.dto.UserDto;
//import com.socialMedia.demo.mapper.UserMapper;
//import com.socialMedia.demo.model.ERole;
//import com.socialMedia.demo.model.Role;
//import com.socialMedia.demo.model.Users;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//
//@ActiveProfiles("test")
//@DataJpaTest
//public class UsersRepositoryTest {
//
//    @Autowired
//    private UsersRepository usersRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Mock
//    private UserMapper userMapper;
//
//    private Users user1;
//
//    private Users user2;
//
//    private Role role;
//
//    private UserDto userDto1;
//
//    private UserDto userDto2;
//
//    @BeforeEach
//    public void init(){
//        role = Role.builder()
//                .name(ERole.ROLE_USER)
//                .build();
//
//        roleRepository.save(role);
//
//        user1 = Users.builder()
//                .username("testUser1")
//                .email("testmail1@gmail.com")
//                .password("testPassword")
//                .role(role)
//                .build();
//
//        user2 = Users.builder()
//                .username("testUser2")
//                .email("testmail2@gmail.com")
//                .password("testPassword")
//                .role(role)
//                .build();
//
//        userDto1 = userMapper.mapToUserDto(user1);
//
//        userDto2 = userMapper.mapToUserDto(user2);
//    }
//
//    @Test
//    public void testSaveUser() {
//        Users savedUser = usersRepository.save(user1);
//
//        assert savedUser != null;
//        assert savedUser.getUsername().equals(user1.getUsername());
//        assert savedUser.getEmail().equals(user1.getEmail());
//    }
//
//    @Test
//    public void testDeleteUser() {
//        usersRepository.save(user1);
//        usersRepository.delete(user1);
//
//        Users deletedUser = usersRepository.findById(user1.getId()).orElse(null);
//
//        assert deletedUser == null;
//    }
//
//    @Test
//    public void testFindByUsername() {
//        usersRepository.save(user1);
//        usersRepository.save(user2);
//
//        Users foundUser = usersRepository.findByUsername(user1.getUsername()).orElse(null);
//
//        assert foundUser != null;
//        assert foundUser.getUsername().equals(user1.getUsername());
//    }
//
//    @Test
//    public void testFindByEmail(){
//        usersRepository.save(user1);
//        usersRepository.save(user2);
//
//        Users foundUser = usersRepository.findByEmail(user1.getEmail()).orElse(null);
//
//        assert foundUser != null;
//        assert foundUser.getEmail().equals(user1.getEmail());
//    }
//
//    @Test
//    public void testExistsByUsername() {
//        usersRepository.save(user1);
//
//        Boolean exists = usersRepository.existsByUsername(user1.getUsername());
//
//        assert exists;
//    }
//
//    @Test
//    public void testExistsByEmail(){
//        usersRepository.save(user1);
//
//        Boolean exists = usersRepository.existsByEmail(user1.getEmail());
//
//        assert exists;
//    }
//
//    @Test
//    public void testFindByUsernameCustom() {
//        usersRepository.save(user1);
//        usersRepository.save(user2);
//
//        var pageable = org.springframework.data.domain.PageRequest.of(0, 10);
//        var page = usersRepository.findByUsernameCustom(user1.getUsername(), pageable);
//
//        assert page.getContent().size() == 1;
//        assert page.getContent().get(0).getUsername().equals(user1.getUsername());
//    }
//}
