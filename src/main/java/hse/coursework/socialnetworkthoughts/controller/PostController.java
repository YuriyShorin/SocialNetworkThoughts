package hse.coursework.socialnetworkthoughts.controller;

import hse.coursework.socialnetworkthoughts.dto.post.CreatePostRequest;
import hse.coursework.socialnetworkthoughts.dto.IdResponse;
import hse.coursework.socialnetworkthoughts.dto.post.CreatePostWithFilesRequest;
import hse.coursework.socialnetworkthoughts.dto.post.UpdatePostRequest;
import hse.coursework.socialnetworkthoughts.security.model.User;
import hse.coursework.socialnetworkthoughts.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Post controller", description = "Post API")
@RequestMapping("/api/v1/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "Create post")
    @ApiResponse(
            responseCode = "201",
            description = "Post created",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = IdResponse.class))})
    @PostMapping
    public ResponseEntity<IdResponse> createPost(
            @RequestBody @Valid CreatePostRequest createPostRequest,
            @AuthenticationPrincipal User user) {
        return postService.createPost(createPostRequest, user);
    }

    @Operation(summary = "Create post with files")
    @ApiResponse(
            responseCode = "201",
            description = "Post created",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = IdResponse.class))})
    @PostMapping("/withFiles")
    public ResponseEntity<IdResponse> createPostWithFiles(
            @ModelAttribute @Valid CreatePostWithFilesRequest createPostWithFilesRequest,
            @AuthenticationPrincipal User user) {
        return postService.createPostWithFiles(createPostWithFilesRequest, user);
    }

    @Operation(summary = "Update post")
    @ApiResponse(
            responseCode = "200",
            description = "Post updated")
    @PutMapping
    public ResponseEntity<?> updatePost(
            @RequestBody @Valid UpdatePostRequest updatePostRequest,
            @AuthenticationPrincipal User user) {
        return postService.updatePost(updatePostRequest, user);
    }

    @Operation(summary = "Delete post")
    @ApiResponse(
            responseCode = "200",
            description = "Post deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Post Id",
                    required = true,
                    schema = @Schema(example = "86ae734e-87d6-44f1-8e7d-991e308b3121"))
            @PathVariable UUID id,
            @AuthenticationPrincipal User user) {
        return postService.deletePostById(id, user);
    }

    @Operation(summary = "Like post")
    @ApiResponse(
            responseCode = "200",
            description = "Post liked"
    )
    @PostMapping("/like/{postId}")
    public ResponseEntity<?> likePost(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Post Id",
                    required = true,
                    schema = @Schema(example = "86ae734e-87d6-44f1-8e7d-991e308b3121"))
            @PathVariable UUID postId,
            @AuthenticationPrincipal User user) {
        return postService.likePost(postId, user);
    }

    @Operation(summary = "Unlike post")
    @ApiResponse(
            responseCode = "200",
            description = "Post unliked"
    )
    @DeleteMapping("/unlike/{postId}")
    public ResponseEntity<?> unlikePost(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Post Id",
                    required = true,
                    schema = @Schema(example = "86ae734e-87d6-44f1-8e7d-991e308b3121"))
            @PathVariable UUID postId,
            @AuthenticationPrincipal User user) {
        return postService.unlikePost(postId, user);
    }
}