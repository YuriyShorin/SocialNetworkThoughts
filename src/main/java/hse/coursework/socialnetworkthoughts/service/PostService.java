package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.dto.IdResponseDto;
import hse.coursework.socialnetworkthoughts.dto.post.CreatePostRequestDto;
import hse.coursework.socialnetworkthoughts.dto.post.UpdatePostRequestDto;
import hse.coursework.socialnetworkthoughts.exception.*;
import hse.coursework.socialnetworkthoughts.model.Id;
import hse.coursework.socialnetworkthoughts.model.Post;
import hse.coursework.socialnetworkthoughts.model.Profile;
import hse.coursework.socialnetworkthoughts.repository.FileRepository;
import hse.coursework.socialnetworkthoughts.repository.LikeRepository;
import hse.coursework.socialnetworkthoughts.repository.PostRepository;
import hse.coursework.socialnetworkthoughts.repository.ProfileRepository;
import hse.coursework.socialnetworkthoughts.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final ProfileRepository profileRepository;

    private final PostRepository postRepository;

    private final LikeRepository likeRepository;

    private final FileRepository fileRepository;

    private final FileService fileService;

    @Transactional
    public ResponseEntity<IdResponseDto> createPost(CreatePostRequestDto createPostRequestDto, User user) {
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);

        Id id = postRepository.save(new Post()
                .setProfileId(profile.getId())
                .setTheme(createPostRequestDto.getTheme())
                .setContent(createPostRequestDto.getContent())
                .setAuthorId(profile.getId()));

        MultipartFile[] files = createPostRequestDto.getFiles();

        if (files != null) {
            Arrays.stream(files)
                    .filter(Objects::nonNull)
                    .forEach(file -> {
                        String url = fileService.save(file);
                        fileRepository.savePicture(id.getId(), url);
                    });
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponseDto(id.getId()));
    }

    public ResponseEntity<?> updatePost(UpdatePostRequestDto updatePostRequestDto, User user) {
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);

        Post post = postRepository.findByIdAndProfileId(updatePostRequestDto.getId(), profile.getId())
                .orElseThrow(NotPostOwnerException::new);

        post.setTheme(updatePostRequestDto.getTheme());
        post.setContent(updatePostRequestDto.getContent());
        postRepository.update(post);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deletePostById(UUID id, User user) {
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);

        postRepository.findByIdAndProfileId(id, profile.getId())
                .orElseThrow(NotPostOwnerException::new);

        postRepository.delete(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> likePost(UUID postId, User user) {
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if (likeRepository.findByProfileId(profile.getId(), postId).isPresent()) {
            throw new PostAlreadyLikedException();
        }

        likeRepository.save(profile.getId(), postId);
        post.setLikes(post.getLikes() + 1);
        postRepository.update(post);

        return ResponseEntity.ok().build();
    }


    public ResponseEntity<?> unlikePost(UUID postId, User user) {
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if (likeRepository.findByProfileId(profile.getId(), postId).isEmpty()) {
            throw new PostNotLikedException();
        }

        likeRepository.delete(profile.getId(), postId);
        post.setLikes(post.getLikes() - 1);
        postRepository.update(post);

        return ResponseEntity.ok().build();
    }
}
