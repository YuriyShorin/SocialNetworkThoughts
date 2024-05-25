package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.dto.IdResponseDto;
import hse.coursework.socialnetworkthoughts.dto.comment.CommentPostRequestDto;
import hse.coursework.socialnetworkthoughts.dto.comment.CommentResponseDto;
import hse.coursework.socialnetworkthoughts.dto.comment.UpdateCommentRequestDto;
import hse.coursework.socialnetworkthoughts.dto.post.CreatePostRequestDto;
import hse.coursework.socialnetworkthoughts.dto.post.UpdatePostRequestDto;
import hse.coursework.socialnetworkthoughts.enums.ExceptionMessageEnum;
import hse.coursework.socialnetworkthoughts.exception.*;
import hse.coursework.socialnetworkthoughts.mapper.CommentMapper;
import hse.coursework.socialnetworkthoughts.model.Comment;
import hse.coursework.socialnetworkthoughts.model.Id;
import hse.coursework.socialnetworkthoughts.model.Post;
import hse.coursework.socialnetworkthoughts.model.Profile;
import hse.coursework.socialnetworkthoughts.repository.*;
import hse.coursework.socialnetworkthoughts.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostImageService postFileService;

    private final ProfileRepository profileRepository;

    private final PostRepository postRepository;

    private final LikeRepository likeRepository;

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @Transactional
    public ResponseEntity<IdResponseDto> createPost(CreatePostRequestDto createPostRequestDto, User user) {
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.PROFILE_NOT_FOUND_MESSAGE.getValue()));

        Id postId = postRepository.save(new Post()
                .setProfileId(profile.getId())
                .setTheme(createPostRequestDto.getTheme())
                .setContent(createPostRequestDto.getContent())
                .setAuthorId(profile.getId()));

        MultipartFile[] images = createPostRequestDto.getImages();

        if (images != null) {
            Arrays.stream(images)
                    .filter(Objects::nonNull)
                    .forEach(image -> postFileService.save(image, postId.getId()));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponseDto(postId.getId()));
    }

    @Transactional
    public ResponseEntity<?> updatePost(UpdatePostRequestDto updatePostRequestDto, User user) {
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.PROFILE_NOT_FOUND_MESSAGE.getValue()));

        Post post = postRepository.findByIdAndProfileId(updatePostRequestDto.getId(), profile.getId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.CONFLICT.value(), ExceptionMessageEnum.NOT_POST_OWNER_MESSAGE.getValue()));

        post.setTheme(updatePostRequestDto.getTheme());
        post.setContent(updatePostRequestDto.getContent());
        postRepository.update(post);

        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> deletePostById(UUID id, User user) {
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.PROFILE_NOT_FOUND_MESSAGE.getValue()));

        postRepository.findByIdAndProfileId(id, profile.getId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.CONFLICT.value(), ExceptionMessageEnum.NOT_POST_OWNER_MESSAGE.getValue()));

        postRepository.delete(id);
        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> likePost(UUID postId, User user) {
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.PROFILE_NOT_FOUND_MESSAGE.getValue()));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.POST_NOT_FOUND_MESSAGE.getValue()));

        if (likeRepository.findByProfileId(profile.getId(), postId).isPresent()) {
            throw new CommonRuntimeException(HttpStatus.CONFLICT.value(), ExceptionMessageEnum.POST_ALREADY_LIKED_MESSAGE.getValue());
        }

        likeRepository.save(profile.getId(), postId);
        post.setLikes(post.getLikes() + 1);
        postRepository.update(post);

        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> unlikePost(UUID postId, User user) {
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.PROFILE_NOT_FOUND_MESSAGE.getValue()));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.POST_NOT_FOUND_MESSAGE.getValue()));

        if (likeRepository.findByProfileId(profile.getId(), postId).isEmpty()) {
            throw new CommonRuntimeException(HttpStatus.CONFLICT.value(), ExceptionMessageEnum.POST_NOT_LIKED_MESSAGE.getValue());
        }

        likeRepository.delete(profile.getId(), postId);
        post.setLikes(post.getLikes() - 1);
        postRepository.update(post);

        return ResponseEntity.ok().build();
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getComments(UUID postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentResponseDto> commentResponseDtos = comments.stream()
                .map(comment -> {
                    Profile profile = profileRepository.findById(comment.getProfileId()).orElseThrow();
                    CommentResponseDto commentResponseDto = commentMapper.toCommentResponseDto(comment);
                    return commentResponseDto.setNickname(profile.getNickname());
                }).toList();

        return ResponseEntity.ok().body(commentResponseDtos);
    }

    @Transactional
    public ResponseEntity<?> commentPost(CommentPostRequestDto commentPostRequestDto, User user) {
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.PROFILE_NOT_FOUND_MESSAGE.getValue()));

        Post post = postRepository.findById(commentPostRequestDto.getPostId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.POST_NOT_FOUND_MESSAGE.getValue()));

        Comment comment = commentMapper.toComment(commentPostRequestDto);
        comment.setProfileId(profile.getId());

        Id id = commentRepository.save(comment);
        post.setComments(post.getComments() + 1);
        postRepository.update(post);

        return ResponseEntity.ok(new IdResponseDto(id.getId()));
    }

    @Transactional
    public ResponseEntity<?> updateComment(UpdateCommentRequestDto updateCommentRequestDto, User user) {
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.PROFILE_NOT_FOUND_MESSAGE.getValue()));

        Comment comment = commentRepository.findByIdAndProfileId(updateCommentRequestDto.getCommentId(), profile.getId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.COMMENT_NOT_FOUND_MESSAGE.getValue()));

        postRepository.findById(comment.getPostId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.POST_NOT_FOUND_MESSAGE.getValue()));

        comment.setContent(updateCommentRequestDto.getContent());
        commentRepository.update(comment);

        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> deleteCommentById(UUID commentId, User user) {
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.PROFILE_NOT_FOUND_MESSAGE.getValue()));

        Comment comment = commentRepository.findByIdAndProfileId(commentId, profile.getId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.NOT_COMMENT_OWNER_MESSAGE.getValue()));

        Post post = postRepository.findById(comment.getPostId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.POST_ALREADY_LIKED_MESSAGE.getValue()));

        commentRepository.deleteById(commentId);
        post.setComments(post.getComments() - 1);
        postRepository.update(post);

        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> repost(UUID postId, User user) {
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.PROFILE_NOT_FOUND_MESSAGE.getValue()));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.POST_ALREADY_LIKED_MESSAGE.getValue()));

        if (post.getAuthorId() == profile.getId()) {
            throw new CommonRuntimeException(HttpStatus.CONFLICT.value(), ExceptionMessageEnum.REPOSTING_OWN_POST_MESSAGE.getValue());
        }

        Post repost = new Post()
                .setProfileId(profile.getId())
                .setContent(post.getContent())
                .setLikes(post.getLikes())
                .setComments(post.getComments())
                .setReposts(post.getReposts())
                .setViews(post.getViews())
                .setIsRepost(true)
                .setAuthorId(post.getProfileId())
                .setCreatedAt(post.getCreatedAt())
                .setEditedAt(post.getEditedAt());
        postRepository.save(repost);

        return ResponseEntity.ok().build();
    }
}
