package com.socialMedia.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddPostRequest {

    private String content;
    private String imageUrl;
}
