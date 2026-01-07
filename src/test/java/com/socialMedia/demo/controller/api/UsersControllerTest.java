/*
package com.socialMedia.demo.controller.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialMedia.demo.dto.ChangePasswordDto;
import com.socialMedia.demo.dto.UserDto;
import com.socialMedia.demo.mapper.UserMapper;
import com.socialMedia.demo.model.ERole;
import com.socialMedia.demo.model.Role;
import com.socialMedia.demo.model.Users;
import com.socialMedia.demo.service.JwtService;
import com.socialMedia.demo.service.RoleService;
import com.socialMedia.demo.service.UsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

@WebMvcTest(UsersController.class)
@WithMockUser(username = "user", roles = {"USER"})
@AutoConfigureMockMvc(addFilters = false)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsersService usersService;

    @MockitoBean
    private RoleService roleService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserMapper userMapper;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    private Users user1;

    private Users user2;

    private Role role;

    private UserDto userDto1;

    private UserDto userDto2;

    private List<UserDto> userDtos;
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @BeforeEach
    public void init(){
        role = Role.builder()
                .name(ERole.ROLE_USER)
                .build();

        user1 = Users.builder()
                .username("testUser1")
                .email("testmail1@gmail.com")
                .password("testPassword")
                .role(role)
                .build();

        user2 = Users.builder()
                .username("testUser2")
                .email("testmail2@gmail.com")
                .password("testPassword")
                .role(role)
                .build();

        userDto1 = new UserDto(1L, "testUser1", "testmail1@gmail.com");

        userDto2 = new UserDto(2L, "testUser2", "testmail2@gmail.com");

        userDtos = List.of(userDto1, userDto2);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        Mockito.when(usersService.findAllUsersPaginated(0, 10)).thenReturn(userDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("testUser1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].username").value("testUser2"));

        Mockito.verify(usersService, Mockito.times(1)).findAllUsersPaginated(0, 10);
    }

    @Test
    public void testGetUserById() throws Exception
    {
        Mockito.when(usersService.findUserById(1L)).thenReturn(userDto1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testUser1"));
    }

    @Test
    public void getAuthenticatedUserTest() throws Exception
    {
        Mockito.when(usersService.getAuthenticatedUsername()).thenReturn("testUser1");
        Mockito.when(usersService.findUserEntityByEmail("testUser1")).thenReturn(user1);
        Mockito.when(userMapper.mapToUserDto(user1)).thenReturn(userDto1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/me"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testUser1"));
    }

    @Test
    public void searchUsersTest() throws Exception
    {
        Mockito.when(usersService.findUsersListByUsername("testUser", 0, 10)).thenReturn(userDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/search")
                        .param("username", "testUser")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("testUser1"))
                .andExpect(MockMvcResultMatchers.jsonPath("[1].username").value("testUser2"));
    }

    @Test
    public void updateUserTest() throws Exception {
        Users editedUser = Users.builder()
                .id(1L)
                .username("updatedUser")
                .email("updatedEmail@gmail.com")
                .password("newPassword123")
                .role(role)
                .build();

        UserDto updatedUserDto = new UserDto(1L, "updatedUser", "updatedEmail@gmail.com");

        Mockito.when(usersService.findUserEntityById(1L)).thenReturn(user1);
        Mockito.when(usersService.isAuthenticatedUserOwner(user1)).thenReturn(true);
        Mockito.when(userMapper.mapToUserDto(user1)).thenReturn(updatedUserDto);
        Mockito.when(passwordEncoder.encode("newPassword123")).thenReturn("encodedPassword123");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/update")
                        .contentType("application/json")
                        .content(jacksonObjectMapper.writeValueAsString(editedUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("updatedUser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("updatedEmail@gmail.com"));

        Mockito.verify(usersService, Mockito.times(1)).findUserEntityById(1L);
        Mockito.verify(usersService, Mockito.times(1)).isAuthenticatedUserOwner(user1);
        Mockito.verify(usersService, Mockito.times(1)).saveUser(user1);
        Mockito.verify(userMapper, Mockito.times(1)).mapToUserDto(user1);

        Assertions.assertEquals("updatedUser", user1.getUsername());
        Assertions.assertEquals("updatedEmail@gmail.com", user1.getEmail());
        Assertions.assertEquals("encodedPassword123", user1.getPassword());
    }

    @Test
    public void changePasswordTest() throws Exception
    {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto(1L, "old", "NewPassword123");

        Mockito.when(usersService.findUserEntityById(1L)).thenReturn(user1);
        Mockito.when(usersService.isAuthenticatedUserOwner(user1)).thenReturn(true);
        Mockito.when(passwordEncoder.matches(changePasswordDto.getOldPassword(), user1.getPassword())).thenReturn(true);

        String responseBody = mockMvc.perform(MockMvcRequestBuilders.put("/api/users/changePassword")
                .contentType("application/json")
                .content(jacksonObjectMapper.writeValueAsString(changePasswordDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<String, String> responseMap = jacksonObjectMapper.readValue(responseBody, new TypeReference<Map<String, String>>() {});

        Assertions.assertEquals("Password changed successfully", responseMap.get("message"));

        Mockito.verify(usersService, Mockito.times(1)).findUserEntityById(1L);
        Mockito.verify(usersService, Mockito.times(1)).isAuthenticatedUserOwner(user1);
        Mockito.verify(usersService, Mockito.times(1)).saveUser(user1);
    }

    @Test
    public void changePasswordUnauthorizedUserTest() throws Exception {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto(1L, "old", "NewPassword123");

        Mockito.when(usersService.findUserEntityById(1L)).thenReturn(user1);
        Mockito.when(usersService.isAuthenticatedUserOwner(user1)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/changePassword")
                        .contentType("application/json")
                        .content(jacksonObjectMapper.writeValueAsString(changePasswordDto)))
                .andExpect(status().isBadRequest());

        Mockito.verify(usersService, Mockito.times(1)).findUserEntityById(1L);
        Mockito.verify(usersService, Mockito.times(1)).isAuthenticatedUserOwner(user1);
        Mockito.verify(usersService, Mockito.never()).saveUser(user1);
    }

    @Test
    public void changePasswordIncorrectOldPasswordTest() throws Exception {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto(1L, "wrongOldPassword", "NewPassword123");

        Mockito.when(usersService.findUserEntityById(1L)).thenReturn(user1);
        Mockito.when(usersService.isAuthenticatedUserOwner(user1)).thenReturn(true);
        Mockito.when(passwordEncoder.matches("wrongOldPassword", user1.getPassword())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/changePassword")
                        .contentType("application/json")
                        .content(jacksonObjectMapper.writeValueAsString(changePasswordDto)))
                .andExpect(status().isBadRequest());

        Mockito.verify(usersService, Mockito.times(1)).findUserEntityById(1L);
        Mockito.verify(usersService, Mockito.times(1)).isAuthenticatedUserOwner(user1);
        Mockito.verify(usersService, Mockito.never()).saveUser(user1);
    }

    @Test
    public void changePasswordEmptyNewPasswordTest() throws Exception {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto(1L, "oldPassword", "");

        Mockito.when(usersService.findUserEntityById(1L)).thenReturn(user1);
        Mockito.when(usersService.isAuthenticatedUserOwner(user1)).thenReturn(true);
        Mockito.when(passwordEncoder.matches("oldPassword", user1.getPassword())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/changePassword")
                        .contentType("application/json")
                        .content(jacksonObjectMapper.writeValueAsString(changePasswordDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void changeUsernameTest() throws Exception
    {
        Mockito.when(usersService.findUserEntityById(1L)).thenReturn(user1);
        Mockito.when(usersService.isAuthenticatedUserOwner(user1)).thenReturn(true);

        String responseBody = mockMvc.perform(MockMvcRequestBuilders.put("/api/users/changeUsername")
                        .param("userId", "1")
                        .param("newUsername", "freshUsername")
                        .contentType("application/json")
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<String, String> responseMap = jacksonObjectMapper.readValue(responseBody, new TypeReference<Map<String, String>>() {});
        Assertions.assertEquals("Username changed successfully", responseMap.get("message"));

        Mockito.verify(usersService, Mockito.times(1)).findUserEntityById(1L);
        Mockito.verify(usersService, Mockito.times(1)).isAuthenticatedUserOwner(user1);
        Mockito.verify(usersService, Mockito.times(1)).saveUser(user1);
    }

    @Test
    public void changeUsernameUnauthorizedUserTest() throws Exception
    {
        Mockito.when(usersService.findUserEntityById(1L)).thenReturn(user1);
        Mockito.when(usersService.isAuthenticatedUserOwner(user1)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/changeUsername")
                        .param("userId", "1")
                        .param("newUsername", "freshUsername")
                        .contentType("application/json")
                )
                .andExpect(status().isBadRequest());

        Mockito.verify(usersService, Mockito.times(1)).findUserEntityById(1L);
        Mockito.verify(usersService, Mockito.times(1)).isAuthenticatedUserOwner(user1);
        Mockito.verify(usersService, Mockito.never()).saveUser(user1);
    }

    @Test
    public void deleteUserTest() throws Exception
    {
        Mockito.when(usersService.findUserEntityById(1L)).thenReturn(user1);
        Mockito.when(usersService.isAuthenticatedUserOwner(user1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
*/
