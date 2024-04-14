package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.dto.post.PostResponse;
import hse.coursework.socialnetworkthoughts.dto.profile.ProfileResponse;
import hse.coursework.socialnetworkthoughts.dto.profile.SubscriptionResponseDto;
import hse.coursework.socialnetworkthoughts.exception.ProfileNotFoundException;
import hse.coursework.socialnetworkthoughts.mapper.ProfileMapper;
import hse.coursework.socialnetworkthoughts.model.Profile;
import hse.coursework.socialnetworkthoughts.model.URL;
import hse.coursework.socialnetworkthoughts.repository.ProfileRepository;
import hse.coursework.socialnetworkthoughts.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final SubscriptionsService subscriptionsService;

    private final ProfileMapper profileMapper;

    private final FileService fileService;

    public ResponseEntity<ProfileResponse> getAuthenticatedUserProfile(User user) {
        Profile profile = profileRepository
                .findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);

        ProfileResponse profileResponse = profileMapper.toProfileResponse(profile);

        for (PostResponse post : profileResponse.getPosts()) {
            List<byte[]> files = new ArrayList<>();
            List<URL> urls = fileService.findUrlsByPostId(post.getId());
            for (URL url : urls) {
                files.add(fileService.load(url.getUrl()));
            }

            post.setFiles(files);
        }

        return ResponseEntity.ok(profileResponse);
    }

    public ResponseEntity<ProfileResponse> getProfileById(UUID profileId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(ProfileNotFoundException::new);
        ProfileResponse profileResponse = profileMapper.toProfileResponse(profile);

        return ResponseEntity.ok(profileResponse);
    }

    public ResponseEntity<?> subscribe(UUID profileId, User user) {
        Profile currentProfile = profileRepository.findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);
        Profile profile = profileRepository.findById(profileId).orElseThrow(ProfileNotFoundException::new);

        if (currentProfile.getId().equals(profile.getId())) {
            return new ResponseEntity<>("Нельзя подписаться на самого себя", HttpStatusCode.valueOf(209));
        }

        List<UUID> subscriptions = subscriptionsService.findSubscriptionsByProfileId(currentProfile.getId());
        if (subscriptions.contains(profile.getId())) {
            return new ResponseEntity<>("Вы уже подписаны на данного пользователя", HttpStatusCode.valueOf(209));
        }

        subscriptionsService.save(currentProfile.getId(), profileId);

        currentProfile.setSubscribes(currentProfile.getSubscribes() + 1);
        profile.setSubscribers(profile.getSubscribers() + 1);

        profileRepository.update(currentProfile);
        profileRepository.update(profile);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> unsubscribe(UUID profileId, User user) {
        Profile currentProfile = profileRepository.findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);
        Profile profile = profileRepository.findById(profileId).orElseThrow(ProfileNotFoundException::new);

        subscriptionsService.deleteByProfileIdAndSubscriptionProfileId(currentProfile.getId(), profileId);

        currentProfile.setSubscribes(currentProfile.getSubscribes() - 1);
        profile.setSubscribers(profile.getSubscribers() - 1);

        profileRepository.update(currentProfile);
        profileRepository.update(profile);

        return ResponseEntity.ok().build();
    }

    public List<SubscriptionResponseDto> getProfileSubscriptions(User user) {
        Profile currentProfile = profileRepository.findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);

        List<UUID> profilesSubscriptions = subscriptionsService.findSubscriptionsByProfileId(currentProfile.getId());

        return getSubscriptionResponseDtos(profilesSubscriptions);
    }

    public List<SubscriptionResponseDto> getProfileSubscriptionsByProfileId(UUID profileId, User user) {
        Profile currentProfile = profileRepository.findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);

        Profile profile = profileRepository.findById(profileId).orElseThrow(ProfileNotFoundException::new);

        List<UUID> profileSubscriptions = subscriptionsService.findSubscriptionsByProfileId(profile.getId());
        List<UUID> currentProfileSubscriptions = subscriptionsService.findSubscriptionsByProfileId(currentProfile.getId());

        return getSubscriptionResponseDtos(profileSubscriptions, currentProfileSubscriptions);
    }

    private List<SubscriptionResponseDto> getSubscriptionResponseDtos(List<UUID> profileSubscriptions, List<UUID> currentProfileSubscriptions) {
        return profileSubscriptions.stream()
                .map(profileSubscription -> {
                    String nickname = getProfileNickname(profileSubscription);

                    return new SubscriptionResponseDto()
                            .setProfileId(profileSubscription)
                            .setIsSubscribed(currentProfileSubscriptions.contains(profileSubscription))
                            .setNickname(nickname);
                })
                .collect(Collectors.toList());
    }

    private List<SubscriptionResponseDto> getSubscriptionResponseDtos(List<UUID> currentProfileSubscriptions) {
        return currentProfileSubscriptions.stream()
                .map(profileSubscription -> {
                    String nickname = getProfileNickname(profileSubscription);

                    return new SubscriptionResponseDto()
                            .setProfileId(profileSubscription)
                            .setIsSubscribed(Boolean.TRUE)
                            .setNickname(nickname);
                })
                .collect(Collectors.toList());
    }

    private String getProfileNickname(UUID profileSubscription) {
        return profileRepository.findById(profileSubscription)
                .map(Profile::getNickname)
                .orElse(null);
    }
}
