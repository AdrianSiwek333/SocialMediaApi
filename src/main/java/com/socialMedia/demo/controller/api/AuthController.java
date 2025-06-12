/*
package com.socialMedia.demo.controller.api;

import com.example.twitterLike.dto.UserCreateDto;
import com.example.twitterLike.model.ERole;
import com.example.twitterLike.model.Role;
import com.example.twitterLike.model.Users;
import com.example.twitterLike.service.AuthService;
import com.example.twitterLike.service.RoleService;
import com.example.twitterLike.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    UsersService usersService;
    PasswordEncoder passwordEncoder;
    AuthService authService;
    RoleService roleService;

    public AuthController(UsersService usersService, PasswordEncoder passwordEncoder, AuthService authService, RoleService roleService) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.roleService = roleService;
    }

    @PostMapping("/signin")
    public String authenticateUser(@RequestBody Users user) {

        String login = user.getUsername();
        String username = login.contains("@")
                ? usersService.findUserByEmail(login).getUsername()
                : login;

        return authService.authenticate(username, user.getPassword());
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserCreateDto user) {
        Role userRole = roleService.findByName(ERole.ROLE_USER);

        Users newUser = new Users(
                null,
                user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                user.getEmail(),
                Set.of(userRole)
        );

        usersService.saveUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }
}
*/
