package com.socialMedia.demo.mapper;

import com.socialMedia.demo.dto.UserDto;
import com.socialMedia.demo.model.Users;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public UserDto mapToUserDto(Users user) {
        return new UserDto(
                user.getId(),
                user.getUsername()
        );
    }

    public List<UserDto> mapToUserDtoList(List<Users> users) {
        return users.stream()
                .map(this::mapToUserDto)
                .toList();
    }
}
