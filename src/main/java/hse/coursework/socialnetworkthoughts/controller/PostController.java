package hse.coursework.socialnetworkthoughts.controller;

import hse.coursework.socialnetworkthoughts.dto.IdResponseDto;
import hse.coursework.socialnetworkthoughts.dto.comment.CommentPostRequestDto;
import hse.coursework.socialnetworkthoughts.dto.comment.UpdateCommentRequestDto;
import hse.coursework.socialnetworkthoughts.dto.post.CreatePostRequestDto;
import hse.coursework.socialnetworkthoughts.dto.post.UpdatePostRequestDto;
import hse.coursework.socialnetworkthoughts.security.model.User;
import hse.coursework.socialnetworkthoughts.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Post controller", description = "API для постов")
@RequestMapping("/api/v1/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "Создать пост")
    @PostMapping
    public ResponseEntity<IdResponseDto> createPost(
            @ModelAttribute @Valid CreatePostRequestDto createPostRequestDto,
            @AuthenticationPrincipal User user) {
        return postService.createPost(createPostRequestDto, user);
    }

    @Operation(summary = "Изменить пост")
    @PutMapping
    public ResponseEntity<?> updatePost(
            @RequestBody @Valid UpdatePostRequestDto updatePostRequestDto,
            @AuthenticationPrincipal User user) {
        return postService.updatePost(updatePostRequestDto, user);
    }

    @Operation(summary = "Удалить пост")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id поста",
                    required = true,
                    schema = @Schema(example = "86ae734e-87d6-44f1-8e7d-991e308b3121"))
            @PathVariable UUID id,
            @AuthenticationPrincipal User user) {
        return postService.deletePostById(id, user);
    }

    @Operation(summary = "Оценить пост")
    @PostMapping("/like/{postId}")
    public ResponseEntity<?> likePost(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id поста",
                    required = true,
                    schema = @Schema(example = "86ae734e-87d6-44f1-8e7d-991e308b3121"))
            @PathVariable UUID postId,
            @AuthenticationPrincipal User user) {
        return postService.likePost(postId, user);
    }

    @Operation(summary = "Убрать лайк с поста")
    @DeleteMapping("/unlike/{postId}")
    public ResponseEntity<?> unlikePost(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id поста",
                    required = true,
                    schema = @Schema(example = "86ae734e-87d6-44f1-8e7d-991e308b3121"))
            @PathVariable UUID postId,
            @AuthenticationPrincipal User user) {
        return postService.unlikePost(postId, user);
    }

    @Operation(summary = "Получить все комментарии для поста")
    @GetMapping("/{postId}/comments")
    public ResponseEntity<?> getComments(@PathVariable UUID postId) {
        return postService.getComments(postId);
    }

    @Operation(summary = "Прокомментировать пост")
    @PostMapping("/comment")
    public ResponseEntity<?> commentPost(
            @RequestBody CommentPostRequestDto commentPostRequestDto,
            @AuthenticationPrincipal User user) {
        return postService.commentPost(commentPostRequestDto, user);
    }

    @Operation(summary = "Изменить комментарий")
    @PutMapping("/comment")
    public ResponseEntity<?> updateComment(
            @RequestBody UpdateCommentRequestDto updateCommentRequestDto,
            @AuthenticationPrincipal User user) {
        return postService.updateComment(updateCommentRequestDto, user);
    }

    @Operation(summary = "Удалить комментарий")
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteCommentById(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id комментария",
                    required = true,
                    schema = @Schema(example = "86ae734e-87d6-44f1-8e7d-991e308b3121"))
            @PathVariable UUID commentId,
            @AuthenticationPrincipal User user) {
        return postService.deleteCommentById(commentId, user);
    }
}
