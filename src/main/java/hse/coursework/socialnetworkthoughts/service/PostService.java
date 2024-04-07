package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.dto.IdResponse;
import hse.coursework.socialnetworkthoughts.dto.post.CreatePostRequest;
import hse.coursework.socialnetworkthoughts.dto.post.CreatePostWithFilesRequest;
import hse.coursework.socialnetworkthoughts.dto.post.UpdatePostRequest;
import hse.coursework.socialnetworkthoughts.exception.*;
import hse.coursework.socialnetworkthoughts.model.Id;
import hse.coursework.socialnetworkthoughts.model.Post;
import hse.coursework.socialnetworkthoughts.model.Profile;
import hse.coursework.socialnetworkthoughts.repository.PostRepository;
import hse.coursework.socialnetworkthoughts.repository.ProfileRepository;
import hse.coursework.socialnetworkthoughts.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final ProfileRepository profileRepository;

    private final PostRepository postRepository;

    private final FileService fileService;

    public ResponseEntity<IdResponse> createPost(CreatePostRequest createPostRequest, User user) {
        Profile profile = profileRepository.findByUserId(user.getId()).orElseThrow(ProfileNotFoundException::new);
        Id id = postRepository.save(new Post(profile.getId(), createPostRequest.getTheme(), createPostRequest.getContent(), profile.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponse(id.getId()));
    }
    public ResponseEntity<IdResponse> createPostWithFiles(CreatePostWithFilesRequest createPostWithFilesRequest, User user) {
        Profile profile = profileRepository.findByUserId(user.getId()).orElseThrow(ProfileNotFoundException::new);
        Id id = postRepository.save(new Post(profile.getId(), createPostWithFilesRequest.getTheme(), createPostWithFilesRequest.getContent(), profile.getId()));

        MultipartFile[] files = createPostWithFilesRequest.getFiles();
        Arrays.stream(files).forEach(file -> {
            String url = fileService.save(file);
            postRepository.savePicture(id.getId(), url);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponse(id.getId()));
    }

    public ResponseEntity<?> updatePost(UpdatePostRequest updatePostRequest, User user) {
        Profile profile = profileRepository.findByUserId(user.getId()).orElseThrow(ProfileNotFoundException::new);
        Post post = postRepository.findByIdAndProfileId(updatePostRequest.getId(), profile.getId()).orElseThrow(NotPostOwnerException::new);

        post.setTheme(updatePostRequest.getTheme());
        post.setContent(updatePostRequest.getContent());
        postRepository.update(post);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deletePostById(UUID id, User user) {
        Profile profile = profileRepository.findByUserId(user.getId()).orElseThrow(ProfileNotFoundException::new);
        postRepository.findByIdAndProfileId(id, profile.getId()).orElseThrow(NotPostOwnerException::new);

        postRepository.delete(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> likePost(UUID postId, User user) {
        Profile profile = profileRepository.findByUserId(user.getId()).orElseThrow(ProfileNotFoundException::new);
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        if (postRepository.findLike(profile.getId(), postId).isPresent()) {
            throw new PostAlreadyLikedException();
        }

        postRepository.like(profile.getId(), postId);
        post.setLikes(post.getLikes() + 1);
        postRepository.update(post);

        return ResponseEntity.ok().build();
    }


    public ResponseEntity<?> unlikePost(UUID postId, User user) {
        Profile profile = profileRepository.findByUserId(user.getId()).orElseThrow(ProfileNotFoundException::new);
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        if (postRepository.findLike(profile.getId(), postId).isEmpty()) {
            throw new PostNotLikedException();
        }

        postRepository.unlike(profile.getId(), postId);
        post.setLikes(post.getLikes() - 1);
        postRepository.update(post);

        return ResponseEntity.ok().build();
    }
}
