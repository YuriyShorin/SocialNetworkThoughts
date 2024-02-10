package hse.coursework.socialnetworkthoughts.controller;

import hse.coursework.socialnetworkthoughts.dto.PostDto;
import hse.coursework.socialnetworkthoughts.security.model.User;
import hse.coursework.socialnetworkthoughts.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto, @AuthenticationPrincipal User user) {
        return postService.createPost(user, postDto);
    }
}
