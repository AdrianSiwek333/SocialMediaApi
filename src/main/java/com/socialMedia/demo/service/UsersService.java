package com.socialMedia.demo.service;

import com.socialMedia.demo.dto.UserDto;
import com.socialMedia.demo.dto.request.UserUpdateRequest;
import com.socialMedia.demo.mapper.UserMapper;
import com.socialMedia.demo.model.Users;
import com.socialMedia.demo.repository.CommentRepository;
import com.socialMedia.demo.repository.PostRepository;
import com.socialMedia.demo.repository.UsersRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserMapper userMapper;

    public UsersService(UsersRepository usersRepository, PostRepository postRepository, CommentRepository commentRepository, UserMapper userMapper) {
        this.usersRepository = usersRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userMapper = userMapper;
    }

    public UserDto findUserById(Long userId) {
        return userMapper.mapToUserDto(usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found")));
    }

    public Users findUserEntityByEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public Users findUserEntityById(Long userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<UserDto> findAllUsersPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Users> usersPage = usersRepository.findAll(pageable);
        return userMapper.mapToUserDtoList(usersPage.getContent());
    }

    public UserDto saveUser(Users user) {
        try {
            usersRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("username")) {
                throw new IllegalArgumentException("Username already exists");
            } else if (e.getMessage().contains("email")) {
                throw new IllegalArgumentException("Email already exists");
            } else {
                throw e;
            }
        }
        return userMapper.mapToUserDto(user);
    }

    public void deleteUser(Long userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        postRepository.deleteAllByAuthor(user);
        commentRepository.deleteAllByAuthor(user);
        usersRepository.deleteById(userId);
    }

    public Users findUserByEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /*public List<UserDto> findUsersListByUsername(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userMapper.mapToUserDtoList(usersRepository.findByUsernameCustom(username, pageable).getContent());
    }*/

    public boolean isAuthenticatedUserOwner(Users user) {
        return false;
    }

    public String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public void updateAvatar(Long userId, String avatarUrl){
        Users user = findUserEntityById(userId);
        Users user2 = findUserByEmail(getAuthenticatedUsername());
        if(!user.equals(user2))
        {
            throw new UsernameNotFoundException("You are not allowed to this user profile");
        }
        user.setAvatarUrl(avatarUrl);
        usersRepository.save(user);
    }

    public void updateBackground(Long userId, String background) {
        Users user = findUserEntityById(userId);
        Users user2 = findUserByEmail(getAuthenticatedUsername());
        if(!user.equals(user2))
        {
            throw new UsernameNotFoundException("You are not allowed to this user profile");
        }
        user.setBgUrl(background);
        usersRepository.save(user);
    }

    public void updateUser(Long userId, UserUpdateRequest userData) {
        Users user = findUserEntityById(userId);
        Users user2 = findUserByEmail(getAuthenticatedUsername());
        if(!user.equals(user2))
        {
            throw new UsernameNotFoundException("You are not allowed to this user profile");
        }
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        usersRepository.save(user);
    }
}
