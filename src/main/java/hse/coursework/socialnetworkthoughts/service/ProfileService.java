package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.dto.post.PostResponse;
import hse.coursework.socialnetworkthoughts.dto.profile.ProfileResponse;
import hse.coursework.socialnetworkthoughts.exception.ProfileNotFoundException;
import hse.coursework.socialnetworkthoughts.model.Post;
import hse.coursework.socialnetworkthoughts.repository.PostRepository;
import hse.coursework.socialnetworkthoughts.repository.ProfileRepository;
import hse.coursework.socialnetworkthoughts.model.Profile;
import hse.coursework.socialnetworkthoughts.repository.SubscriptionRepository;
import hse.coursework.socialnetworkthoughts.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final SubscriptionRepository subscriptionRepository;

    private final PostRepository postRepository;

    public ResponseEntity<ProfileResponse> getAuthenticatedUserProfile(User user) {
        Profile profile = profileRepository
                .findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);

        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setId(profile.getId());
        profileResponse.setNickname(profile.getNickname());
        profileResponse.setStatus(profile.getStatus());
        profileResponse.setDescription(profile.getDescription());

        List<PostResponse> postResponses = new ArrayList<>();
        for (Post post : profile.getPosts()) {
            Boolean isLiked = postRepository.findLike(profile.getId(), post.getId()).isPresent();

            PostResponse postResponse = new PostResponse(
                    post.getId(),
                    post.getTheme(),
                    post.getContent(),
                    post.getLikes(),
                    isLiked,
                    post.getReposts(),
                    post.getComments(),
                    post.getViews(),
                    post.getAuthorId(),
                    post.getCreatedAt(),
                    post.getEditedAt()
            );

            postResponses.add(postResponse);
        }
        profileResponse.setPosts(postResponses);

        return ResponseEntity.ok(profileResponse);
    }

    public ResponseEntity<?> subscribe(UUID profileId, User user) {
        Profile currentProfile = profileRepository.findByUserId(user.getId()).orElseThrow(ProfileNotFoundException::new);
        Profile profile = profileRepository.findById(profileId).orElseThrow(ProfileNotFoundException::new);

        subscriptionRepository.save(currentProfile.getId(), profileId);

        currentProfile.setSubscribes(currentProfile.getSubscribes() + 1);
        profile.setSubscribers(profile.getSubscribers() + 1);

        profileRepository.update(currentProfile);
        profileRepository.update(profile);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> unsubscribe(UUID profileId, User user) {
        Profile currentProfile = profileRepository.findByUserId(user.getId()).orElseThrow(ProfileNotFoundException::new);
        Profile profile = profileRepository.findById(profileId).orElseThrow(ProfileNotFoundException::new);

        subscriptionRepository.deleteByProfileIdAndSubscriptionProfileId(currentProfile.getId(), profileId);

        currentProfile.setSubscribes(currentProfile.getSubscribes() - 1);
        profile.setSubscribers(profile.getSubscribers() - 1);

        profileRepository.update(currentProfile);
        profileRepository.update(profile);

        return ResponseEntity.ok().build();
    }
}
