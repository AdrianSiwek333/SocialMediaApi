package com.socialMedia.demo.controller.api;

import com.socialMedia.demo.dto.UserDto;
import com.socialMedia.demo.mapper.UserMapper;
import com.socialMedia.demo.model.Users;
import com.socialMedia.demo.service.UsersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;

    private final UserMapper userMapper;

    public UsersController(UsersService usersService, UserMapper userMapper) {
        this.usersService = usersService;
        this.userMapper = userMapper;
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

    @PutMapping
    public UserDto updateUser(@RequestBody UserDto userDto) {
        Users user = usersService.findUserEntityById(userDto.getId());
        if(!isCurrentUserOwner(user)) {
            throw new IllegalArgumentException("You are not authorized to update this user");
        }
        usersService.saveUser(user);
        return userMapper.mapToUserDto(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        Users user = usersService.findUserEntityById(userId);
        if(!isCurrentUserOwner(user)) {
            throw new IllegalArgumentException("You are not authorized to delete this user");
        }
        usersService.deleteUser(userId);
    }

    private boolean isCurrentUserOwner(Users user) {
        return usersService.isAuthenticatedUserOwner(user);
    }
}
