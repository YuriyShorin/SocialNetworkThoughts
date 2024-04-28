package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.dto.post.PostResponseDto;
import hse.coursework.socialnetworkthoughts.dto.profile.ProfileResponseDto;
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

    public ResponseEntity<ProfileResponseDto> getAuthenticatedUserProfile(User user) {
        Profile profile = profileRepository
                .findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);

        ProfileResponseDto profileResponseDto = buildProfileResponse(profile);

        return ResponseEntity.ok(profileResponseDto);
    }

    public ResponseEntity<ProfileResponseDto> getProfileById(UUID profileId) {
        Profile profile = profileRepository
                .findById(profileId)
                .orElseThrow(ProfileNotFoundException::new);

        ProfileResponseDto profileResponseDto = buildProfileResponse(profile);

        return ResponseEntity.ok(profileResponseDto);
    }

    public ResponseEntity<?> subscribe(UUID profileId, User user) {
        Profile currentProfile = profileRepository.findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);
        Profile profile = profileRepository.findById(profileId).orElseThrow(ProfileNotFoundException::new);

        if (currentProfile.getId().equals(profile.getId())) {
            return new ResponseEntity<>("Нельзя подписаться на самого себя", HttpStatusCode.valueOf(209));
        }

        List<UUID> subscriptions = getSubscriptionsByProfileId(currentProfile);
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

        List<UUID> profilesSubscriptions = getSubscriptionsByProfileId(currentProfile);

        return buildSubscriptionResponseDtos(profilesSubscriptions);
    }

    public List<SubscriptionResponseDto> getProfileSubscriptionsByProfileId(UUID profileId, User user) {
        Profile currentProfile = profileRepository.findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);

        List<UUID> profileSubscriptions = subscriptionsService.findSubscriptionsByProfileId(profileId);
        List<UUID> currentProfileSubscriptions = getSubscriptionsByProfileId(currentProfile);

        return buildSubscriptionResponseDtos(profileSubscriptions, currentProfileSubscriptions);
    }

    public List<SubscriptionResponseDto> getProfileSubscribers(User user) {
        Profile currentProfile = profileRepository.findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);

        List<UUID> profilesSubscribers = getSubscribersByProfileId(currentProfile.getId());
        List<UUID> currentProfileSubscriptions = getSubscriptionsByProfileId(currentProfile);

        return buildSubscriptionResponseDtos(profilesSubscribers, currentProfileSubscriptions);
    }

    public List<SubscriptionResponseDto> getProfileSubscribersByProfileId(UUID profileId, User user) {
        Profile currentProfile = profileRepository.findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);

        List<UUID> profilesSubscribers = getSubscribersByProfileId(profileId);
        List<UUID> currentProfileSubscriptions = getSubscriptionsByProfileId(currentProfile);

        return buildSubscriptionResponseDtos(profilesSubscribers, currentProfileSubscriptions);
    }

    private ProfileResponseDto buildProfileResponse(Profile profile) {
        ProfileResponseDto profileResponseDto = profileMapper.toProfileResponse(profile);

        for (PostResponseDto post : profileResponseDto.getPosts()) {
            List<byte[]> files = new ArrayList<>();
            List<URL> urls = fileService.findUrlsByPostId(post.getId());
            for (URL url : urls) {
                files.add(fileService.load(url.getUrl()));
            }

            post.setFiles(files);
        }
        return profileResponseDto;
    }

    private List<SubscriptionResponseDto> buildSubscriptionResponseDtos(List<UUID> profileSubs, List<UUID> currentProfileSubscriptions) {
        return profileSubs.stream()
                .map(profileSub -> {
                    String nickname = getProfileNickname(profileSub);

                    return new SubscriptionResponseDto()
                            .setProfileId(profileSub)
                            .setIsSubscribed(currentProfileSubscriptions.contains(profileSub))
                            .setNickname(nickname);
                })
                .collect(Collectors.toList());
    }

    private List<SubscriptionResponseDto> buildSubscriptionResponseDtos(List<UUID> currentProfileSubscriptions) {
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

    private String getProfileNickname(UUID profileId) {
        return profileRepository.findById(profileId)
                .map(Profile::getNickname)
                .orElse(null);
    }

    private List<UUID> getSubscribersByProfileId(UUID profileId) {
        return subscriptionsService.findSubscribersByProfileId(profileId);
    }

    private List<UUID> getSubscriptionsByProfileId(Profile currentProfile) {
        return subscriptionsService.findSubscriptionsByProfileId(currentProfile.getId());
    }
}
