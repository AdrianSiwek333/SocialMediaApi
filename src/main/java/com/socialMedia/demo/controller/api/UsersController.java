package com.socialMedia.demo.controller.api;

import com.socialMedia.demo.dto.ChangePasswordDto;
import com.socialMedia.demo.dto.UserDto;
import com.socialMedia.demo.dto.request.UserUpdateRequest;
import com.socialMedia.demo.mapper.UserMapper;
import com.socialMedia.demo.model.Users;
import com.socialMedia.demo.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UsersController {

    private final UsersService usersService;

    private final UserMapper userMapper;

    private PasswordEncoder passwordEncoder;

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
        Users user = usersService.findUserEntityByEmail(username);
        return userMapper.mapToUserDto(user);
    }

    /*@GetMapping("/search")
    public List<UserDto> searchUsers(@RequestParam String username,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        return usersService.findUsersListByUsername(username, page, size);
    }*/


    @PutMapping("/changePassword")
    public ResponseEntity<Map<String, String>> changePassword(@RequestBody ChangePasswordDto changePasswordDto){

        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        if (changePasswordDto.getNewPassword() == null || !changePasswordDto.getNewPassword().matches(passwordRegex)) {
            throw new IllegalArgumentException("New password must be at least 8 characters long and contain at least one letter and one number");
        }

        Users user = usersService.findUserEntityById(changePasswordDto.getUserId());

        if(!usersService.isAuthenticatedUserOwner(user)) {
            throw new IllegalArgumentException("You are not authorized to change password");
        }
        if(!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword()))
        {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        usersService.saveUser(user);

        Map<String, String> response = Map.of("message", "Password changed successfully");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        Users user = usersService.findUserEntityById(userId);
        if(!usersService.isAuthenticatedUserOwner(user)) {
            throw new IllegalArgumentException("You are not authorized to delete this user");
        }
        usersService.deleteUser(userId);
    }

    @PostMapping("/{userId}/avatar")
    public void updateAvatar(@PathVariable Long userId, @RequestBody Map<String, String> payload)
    {
        String avatarUrl = payload.get("avatarUrl");
        usersService.updateAvatar(userId, avatarUrl);
    }

    @PostMapping("/{userId}/background")
    public void updateBackground(@PathVariable Long userId, @RequestBody Map<String, String> payload)
    {
        String background = payload.get("bgUrl");
        usersService.updateBackground(userId, background);
    }

    @PutMapping("/{userId}")
    public void updateUser(
            @PathVariable Long userId,
            @RequestBody UserUpdateRequest userData) {

        usersService.updateUser(userId, userData);
    }

}
