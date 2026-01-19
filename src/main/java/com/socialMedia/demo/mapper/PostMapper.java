package com.socialMedia.demo.mapper;

import com.socialMedia.demo.dto.PostDto;
import com.socialMedia.demo.model.Post;
import com.socialMedia.demo.repository.CommentRepository;
import com.socialMedia.demo.repository.InteractionRepository;
import com.socialMedia.demo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final CommentRepository commentRepository;
    private final InteractionRepository interactionRepository;
    private final UsersRepository usersRepository;

    public PostDto mapToPostDto(Post post) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isLiked = false;

        if (email != null && !email.equals("anonymousUser")) {
            usersRepository.findByEmail(email).ifPresent(user -> {
                if (interactionRepository.existsByPostIdAndUserId(post, user)) {
                }
            });
            isLiked = usersRepository.findByEmail(email)
                    .map(user -> interactionRepository.existsByPostIdAndUserId(post, user))
                    .orElse(false);
        }

        return new PostDto(
                post.getPostId(),
                post.getContent(),
                post.getImageUrl(),
                post.getAuthor().getId(),
                post.getAuthor().getFirstName(),
                post.getAuthor().getLastName(),
                post.getAuthor().getAvatarUrl(),
                post.getCreatedAt(),
                interactionRepository.countByPostId(post),
                commentRepository.countByPost(post),
                isLiked
        );
    }

    public List<PostDto> mapToPostDtoList(List<Post> posts) {
        return posts.stream()
                .map(this::mapToPostDto)
                .toList();
    }
}
