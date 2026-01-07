/*
package com.socialMedia.demo.service;

import com.socialMedia.demo.dto.UserDto;
import com.socialMedia.demo.mapper.UserMapper;
import com.socialMedia.demo.model.Users;
import com.socialMedia.demo.repository.CommentRepository;
import com.socialMedia.demo.repository.PostRepository;
import com.socialMedia.demo.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

public class UsersServiceTest {

    @InjectMocks
    private UsersService usersService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    public UsersServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    private Users user1;
    private Users user2;
    private UserDto userDto1;
    private UserDto userDto2;

    @BeforeEach
    public void init() {
        user1 = new Users();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setEmail("user1@mail.com");

        user2 = new Users();
        user2.setId(2L);
        user2.setUsername("user2");

        userDto1 = new UserDto();
        userDto1.setId(1L);
        userDto1.setUsername("user1");

        userDto2 = new UserDto();
        userDto2.setId(2L);
        userDto2.setUsername("user2");
    }

    @Test
    void testFindUserById() {
        given(usersRepository.findById(1L)).willReturn(java.util.Optional.of(user1));
        when(userMapper.mapToUserDto(user1)).thenReturn(userDto1);
        UserDto foundUser = usersService.findUserById(1L);
        assertNotNull(foundUser);
        assertEquals("user1", foundUser.getUsername());
    }

    @Test
    void testFindUserEntityByUsername() {
        given(usersRepository.findByUsername("user1")).willReturn(Optional.of(user1));
        Users foundUser = usersService.findUserEntityByEmail("user1");
        assertNotNull(foundUser);
        assertEquals("user1", foundUser.getUsername());
    }

    @Test
    void testFindUserEntityById() {
        given(usersRepository.findById(1L)).willReturn(Optional.of(user1));
        Users foundUser = usersService.findUserEntityById(1L);
        assertNotNull(foundUser);
        assertEquals("user1", foundUser.getUsername());
    }

    @Test
    void testFindAllUsersPaginated() {
        given(usersRepository.findAll(PageRequest.of(0, 10)))
                .willReturn(new PageImpl<>(List.of(user1, user2)));

        when(userMapper.mapToUserDtoList(List.of(user1, user2)))
                .thenReturn(List.of(userDto1, userDto2));

        List<UserDto> foundUsers = usersService.findAllUsersPaginated(0, 10);

        assertNotNull(foundUsers);
        assertEquals(2, foundUsers.size());
        assertEquals("user1", foundUsers.get(0).getUsername());
        assertEquals("user2", foundUsers.get(1).getUsername());
    }

    @Test
    void testSaveUser() {
        when(userMapper.mapToUserDto(user1)).thenReturn(userDto1);
        given(usersRepository.save(user1)).willReturn(user1);

        UserDto savedUser = usersService.saveUser(user1);
        assertThat(savedUser).isNotNull();
    }

    @Test
    void testDeleteUser() {
        given(usersRepository.findById(1L)).willReturn(Optional.of(user1));
        willDoNothing().given(usersRepository).deleteById(1L);
        usersService.deleteUser(1L);

        verify(usersRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindUserByEmail() {
        when(usersRepository.findByEmail("user1@mail.com"))
                .thenReturn(java.util.Optional.ofNullable(user1));
        Users foundUser = usersService.findUserByEmail("user1@mail.com");
        assertNotNull(foundUser);
        assertEquals("user1", foundUser.getUsername());
    }

    @Test
    void testFindUsersListByUsername() {
        given(usersRepository.findByUsernameCustom("user1", PageRequest.of(0, 10)))
                .willReturn(new PageImpl<>(List.of(user1), PageRequest.of(0, 10), 1));
        when(userMapper.mapToUserDtoList(List.of(user1)))
                .thenReturn(List.of(userDto1));
        List<UserDto> foundUsers = usersService.findUsersListByUsername("user1", 0, 10);
        assertNotNull(foundUsers);
        assertEquals(1, foundUsers.size());
        assertEquals("user1", foundUsers.get(0).getUsername());
    }

    @Test
    void testIsAuthenticatedUserOwner() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("user1");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Users user = new Users();
        user.setUsername("user1");

        boolean isOwner = usersService.isAuthenticatedUserOwner(user);

        assertTrue(isOwner);
    }

    @Test
    void testGetAuthenticatedUsername() {

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("user1");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        String authenticatedUsername = usersService.getAuthenticatedUsername();

        assertEquals("user1", authenticatedUsername);
    }

}
*/
