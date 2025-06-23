package com.socialMedia.demo.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialMedia.demo.dto.UserDto;
import com.socialMedia.demo.mapper.UserMapper;
import com.socialMedia.demo.model.ERole;
import com.socialMedia.demo.model.Role;
import com.socialMedia.demo.model.Users;
import com.socialMedia.demo.service.JwtService;
import com.socialMedia.demo.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.List;

@WebMvcTest(UsersController.class)
@WithMockUser(username = "user", roles = {"USER"})
@AutoConfigureMockMvc(addFilters = false)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsersService usersService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserMapper userMapper;

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
        Mockito.when(usersService.findUserEntityByUsername("testUser1")).thenReturn(user1);
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

/*    @Test
    public void updateUserTest() throws Exception
    {
        Users editedUser = Users.builder()
                .id(1L)
                .username("updatedUser")
                .email("updatedEmail@gmail.com")
                .password("pass")
                .role(role)
                .build();
        UserDto updatedUserDto = new UserDto(1L, "updatedUser", "updatedEmail@gmail.com");

        Mockito.when(usersService.findUserEntityById(1L)).thenReturn(user1);
        Mockito.when(usersService.isAuthenticatedUserOwner(user1)).thenReturn(true);
        Mockito.when(userMapper.mapToUserDto(editedUser)).thenReturn(updatedUserDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/edit")
                        .contentType("application/json")
                        .content(jacksonObjectMapper.writeValueAsString(editedUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("updatedUser"));
    }*/

    @Test
    public void deleteUserTest() throws Exception
    {
        Mockito.when(usersService.findUserEntityById(1L)).thenReturn(user1);
        Mockito.when(usersService.isAuthenticatedUserOwner(user1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
