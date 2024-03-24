package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.dto.post.SearchPostResponse;
import hse.coursework.socialnetworkthoughts.dto.profile.SearchProfileResponse;
import hse.coursework.socialnetworkthoughts.exception.ProfileNotFoundException;
import hse.coursework.socialnetworkthoughts.model.Profile;
import hse.coursework.socialnetworkthoughts.repository.ProfileRepository;
import hse.coursework.socialnetworkthoughts.repository.SubscriptionRepository;
import hse.coursework.socialnetworkthoughts.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ProfileRepository profileRepository;

    private final SubscriptionRepository subscriptionRepository;

    public ResponseEntity<List<SearchProfileResponse>> searchProfilesByNickname(String nickname, User user) {
        Profile currentProfile = profileRepository.findByUserId(user.getId()).orElseThrow(ProfileNotFoundException::new);

        List<Profile> profiles = profileRepository.findByNickname(nickname);
        List<SearchProfileResponse> searchProfileResponses = new ArrayList<>();

        for (Profile profile : profiles) {
            SearchProfileResponse searchProfileResponse = new SearchProfileResponse();
            searchProfileResponse.setId(profile.getId());
            searchProfileResponse.setNickname(profile.getNickname());
            searchProfileResponse.setIsSubscribed(
                    subscriptionRepository
                            .findByProfileIdAndSubscriptionProfileId(
                                    currentProfile.getId(), profile.getId())
                            .isPresent());

            searchProfileResponses.add(searchProfileResponse);
        }

        return ResponseEntity.ok(searchProfileResponses);
    }

    public ResponseEntity<List<SearchPostResponse>> searchPostsByTheme(String theme) {
        return ResponseEntity.ok(new ArrayList<>());
    }
}
