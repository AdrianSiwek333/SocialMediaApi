package com.socialMedia.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordDto {

    private Long userId;
    private String oldPassword;
    private String newPassword;
}
