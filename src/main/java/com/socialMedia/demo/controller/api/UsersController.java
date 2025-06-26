package com.socialMedia.demo.controller.api;

import com.socialMedia.demo.dto.UserDto;
import com.socialMedia.demo.mapper.UserMapper;
import com.socialMedia.demo.model.Role;
import com.socialMedia.demo.model.Users;
import com.socialMedia.demo.service.RoleService;
import com.socialMedia.demo.service.UsersService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;

    private final UserMapper userMapper;

    private final RoleService roleService;

    private PasswordEncoder passwordEncoder;

    public UsersController(UsersService usersService, UserMapper userMapper, RoleService roleService,
                           PasswordEncoder passwordEncoder) {
        this.usersService = usersService;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/all")
    public List<UserDto> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        return usersService.findAllUsersPaginated(page, size);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return usersService.findUserById(userId);
    }

    @GetMapping("/me")
    public UserDto getAuthenticatedUser() {
        String username = usersService.getAuthenticatedUsername();
        Users user = usersService.findUserEntityByUsername(username);
        return userMapper.mapToUserDto(user);
    }

    @GetMapping("/search")
    public List<UserDto> searchUsers(@RequestParam String username,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        return usersService.findUsersListByUsername(username, page, size);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody Users editedUser) {
        if (editedUser == null || editedUser.getId() == null) {
            throw new IllegalArgumentException("Edited user or user ID cannot be null");
        }

        Users user = usersService.findUserEntityById(editedUser.getId());
        if (!usersService.isAuthenticatedUserOwner(user)) {
            throw new IllegalArgumentException("You are not authorized to update this user");
        }

        if (editedUser.getUsername() != null) {
            user.setUsername(editedUser.getUsername());
        }
        if (editedUser.getEmail() != null) {
            user.setEmail(editedUser.getEmail());
        }
        if (editedUser.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(editedUser.getPassword()));
        }

        usersService.saveUser(user);
        UserDto userDto = userMapper.mapToUserDto(user);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        Users user = usersService.findUserEntityById(userId);
        if(!usersService.isAuthenticatedUserOwner(user)) {
            throw new IllegalArgumentException("You are not authorized to delete this user");
        }
        usersService.deleteUser(userId);
    }

}
