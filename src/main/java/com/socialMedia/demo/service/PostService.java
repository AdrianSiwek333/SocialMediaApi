package com.socialMedia.demo.service;

import com.socialMedia.demo.dto.PostDto;
import com.socialMedia.demo.dto.request.AddPostRequest;
import com.socialMedia.demo.exception.PostNotFoundException;
import com.socialMedia.demo.mapper.PostMapper;
import com.socialMedia.demo.model.Interaction;
import com.socialMedia.demo.model.Post;
import com.socialMedia.demo.model.Users;
import com.socialMedia.demo.repository.InteractionRepository;
import com.socialMedia.demo.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UsersService usersService;
    private final InteractionRepository interactionRepository;

    public PostDto addPost(AddPostRequest addPostRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersService.findUserEntityByEmail(username);

        Post post = new Post();
        post.setAuthor(user);
        post.setContent(addPostRequest.getContent());
        post.setImageUrl(addPostRequest.getImageUrl());

        postRepository.save(post);
        return postMapper.mapToPostDto(post);
    }

    public void removePost(Post post) {
        postRepository.delete(post);
    }

    public PostDto findPostById(Long postId) {
        return postMapper.mapToPostDto(postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Post not found")
        ));
    }

    public Post findPostByIdRaw(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Post not found")
        );
    }

    public List<PostDto> findAllPosts(int page, int size) {
        return postRepository.findAll(PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "createdAt"))).getContent()
                .stream()
                .map(postMapper::mapToPostDto)
                .toList();
    }

    public long toggleLike(Long postId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersService.findUserEntityByEmail(email);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        Optional<Interaction> interaction = interactionRepository.findByPostIdAndUserId(post, user);

        if (interaction.isPresent()) {
            interactionRepository.delete(interaction.get());
        } else {
            interactionRepository.save(new Interaction(post, user));
        }

        return interactionRepository.countByPostId(post);
    }

    public List<PostDto> findAllPostsByUser(Long userId, int page, int size) {
        Users user = usersService.findUserEntityById(userId);
        return postMapper.mapToPostDtoList(
                postRepository.findByAuthor(user, PageRequest.of(
                        page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
                ).stream().toList());
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).
                orElseThrow(()-> new PostNotFoundException("Post not found"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersService.findUserEntityByEmail(email);

        if(user.getId().equals(post.getAuthor().getId()))
        {
//            interactionRepository.deleteAllByPostId(post);
            List<Interaction> interactions = interactionRepository.findAllByPostId(post);
            for (Interaction interaction: interactions)
            {
                interactionRepository.delete(interaction);
            }
            postRepository.deleteById(postId);
        }
        else
        {
            throw new AuthorizationDeniedException("You are not owner of this post");
        }
    }
}
