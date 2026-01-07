package com.socialMedia.demo.service;

import com.socialMedia.demo.dto.request.AuthenticationRequest;
import com.socialMedia.demo.dto.request.RegisterRequest;
import com.socialMedia.demo.dto.response.AuthenticationResponse;
import com.socialMedia.demo.model.ERole;
import com.socialMedia.demo.model.Role;
import com.socialMedia.demo.model.Users;
import com.socialMedia.demo.repository.RoleRepository;
import com.socialMedia.demo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var role = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        var user = Users.builder()
                .email(registerRequest.getEmail())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .sex(registerRequest.getSex())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefresh(new HashMap<>(), user);
        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );
        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefresh(new HashMap<>(), user);
        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse refreshToken(String refreshToken) {

        var user = userRepository.findByEmail(jwtService.getEmailFromToken(refreshToken)).orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
        var jwtToken = jwtService.generateToken(user);
        var newRefreshToken = jwtService.generateRefresh(new HashMap<>(), user);
        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public Boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }
}
