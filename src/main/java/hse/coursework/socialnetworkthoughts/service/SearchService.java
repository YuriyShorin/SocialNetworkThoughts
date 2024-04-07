package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.dto.feed.FeedResponse;
import hse.coursework.socialnetworkthoughts.dto.profile.SearchProfileResponse;
import hse.coursework.socialnetworkthoughts.exception.ProfileNotFoundException;
import hse.coursework.socialnetworkthoughts.mapper.FeedMapper;
import hse.coursework.socialnetworkthoughts.mapper.ProfileMapper;
import hse.coursework.socialnetworkthoughts.model.Feed;
import hse.coursework.socialnetworkthoughts.model.Profile;
import hse.coursework.socialnetworkthoughts.repository.FeedRepository;
import hse.coursework.socialnetworkthoughts.repository.PostRepository;
import hse.coursework.socialnetworkthoughts.repository.ProfileRepository;
import hse.coursework.socialnetworkthoughts.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ProfileRepository profileRepository;

    private final PostRepository postRepository;

    private final FeedRepository feedRepository;

    private final ProfileMapper profileMapper;

    private final FeedMapper feedMapper;

    public ResponseEntity<List<SearchProfileResponse>> searchProfilesByNickname(String nickname, User user) {
        Profile currentProfile = profileRepository.findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);

        List<Profile> profiles = profileRepository.findByNickname(nickname);
        List<SearchProfileResponse> searchProfileResponses = profiles.stream()
                .map(profile -> profileMapper.toSearchProfileResponse(profile, currentProfile.getId()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(searchProfileResponses);
    }

    public ResponseEntity<List<FeedResponse>> searchPostsByTheme(String theme, User user) {
        Profile currentProfile = profileRepository.findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);

        List<Feed> feeds = feedRepository.getFeedByTheme(theme);
        List<FeedResponse> feedResponses = feeds.stream()
                .map(feed -> getFeedResponse(feed, currentProfile))
                .collect(Collectors.toList());

        return ResponseEntity.ok(feedResponses);
    }

    private FeedResponse getFeedResponse(Feed feed, Profile currentProfile) {
        Boolean isLiked = postRepository.findLike(currentProfile.getId(), feed.getPostId()).isPresent();
        return feedMapper.toFeedResponse(feed, isLiked);
    }
}
