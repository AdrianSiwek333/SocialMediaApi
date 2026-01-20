package com.socialMedia.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    public Long id;
    public String firstName;
    public String lastName;
    public String sex;
    public String email;
    public String avatarUrl;
    public String bgUrl;
}
