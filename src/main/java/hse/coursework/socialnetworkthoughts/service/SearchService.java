package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.dto.feed.FeedResponse;
import hse.coursework.socialnetworkthoughts.dto.profile.SearchProfileResponseDto;
import hse.coursework.socialnetworkthoughts.enums.ExceptionMessageEnum;
import hse.coursework.socialnetworkthoughts.exception.CommonRuntimeException;
import hse.coursework.socialnetworkthoughts.mapper.FeedMapper;
import hse.coursework.socialnetworkthoughts.mapper.ProfileMapper;
import hse.coursework.socialnetworkthoughts.model.Feed;
import hse.coursework.socialnetworkthoughts.model.Profile;
import hse.coursework.socialnetworkthoughts.repository.FeedRepository;
import hse.coursework.socialnetworkthoughts.repository.LikeRepository;
import hse.coursework.socialnetworkthoughts.repository.ProfileRepository;
import hse.coursework.socialnetworkthoughts.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ProfileRepository profileRepository;

    private final FeedRepository feedRepository;

    private final LikeRepository likeRepository;

    private final ProfileMapper profileMapper;

    private final FeedMapper feedMapper;

    public ResponseEntity<List<SearchProfileResponseDto>> searchProfilesByNickname(String nickname, User user) {
        Profile currentProfile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.PROFILE_NOT_FOUND_MESSAGE.getValue()));

        List<Profile> profiles = profileRepository.findByNickname(nickname);
        List<SearchProfileResponseDto> searchProfileResponsDtos = profiles.stream()
                .map(profile -> profileMapper.toSearchProfileResponse(profile, currentProfile.getId()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(searchProfileResponsDtos);
    }

    public ResponseEntity<List<FeedResponse>> searchPostsByTheme(String theme, User user) {
        Profile currentProfile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.PROFILE_NOT_FOUND_MESSAGE.getValue()));

        List<Feed> feeds = feedRepository.getFeedByTheme(theme);
        List<FeedResponse> feedResponses = feeds.stream()
                .map(feed -> getFeedResponse(feed, currentProfile))
                .collect(Collectors.toList());

        return ResponseEntity.ok(feedResponses);
    }

    private FeedResponse getFeedResponse(Feed feed, Profile currentProfile) {
        Boolean isLiked = likeRepository.findByProfileId(currentProfile.getId(), feed.getPostId()).isPresent();
        return feedMapper.toFeedResponse(feed, isLiked);
    }
}
