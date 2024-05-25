package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.dto.feed.FeedResponseDto;
import hse.coursework.socialnetworkthoughts.dto.profile.SearchProfileResponseDto;
import hse.coursework.socialnetworkthoughts.enums.ExceptionMessageEnum;
import hse.coursework.socialnetworkthoughts.exception.CommonRuntimeException;
import hse.coursework.socialnetworkthoughts.mapper.FeedMapper;
import hse.coursework.socialnetworkthoughts.mapper.ProfileMapper;
import hse.coursework.socialnetworkthoughts.model.Feed;
import hse.coursework.socialnetworkthoughts.model.ImagePath;
import hse.coursework.socialnetworkthoughts.model.Profile;
import hse.coursework.socialnetworkthoughts.repository.FeedRepository;
import hse.coursework.socialnetworkthoughts.repository.LikeRepository;
import hse.coursework.socialnetworkthoughts.repository.ProfileRepository;
import hse.coursework.socialnetworkthoughts.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final PostImageService postImageService;

    private final ProfileRepository profileRepository;

    private final FeedRepository feedRepository;

    private final LikeRepository likeRepository;

    private final ProfileMapper profileMapper;

    private final FeedMapper feedMapper;

    @Transactional(readOnly = true)
    public ResponseEntity<List<SearchProfileResponseDto>> searchProfilesByNickname(String nickname, User user) {
        Profile currentProfile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.PROFILE_NOT_FOUND_MESSAGE.getValue()));

        List<Profile> profiles = profileRepository.findByNickname(nickname, currentProfile.getId());
        List<SearchProfileResponseDto> searchProfileResponseDtos = profiles.stream()
                .map(profile -> profileMapper.toSearchProfileResponse(profile, currentProfile.getId()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(searchProfileResponseDtos);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<FeedResponseDto>> searchPostsByTheme(String theme, User user) {
        Profile currentProfile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CommonRuntimeException(HttpStatus.NOT_FOUND.value(), ExceptionMessageEnum.PROFILE_NOT_FOUND_MESSAGE.getValue()));

        List<Feed> feeds = feedRepository.getFeedByTheme(theme);
        List<FeedResponseDto> feedResponses = feeds.stream()
                .map(feed -> getFeedResponse(feed, currentProfile))
                .collect(Collectors.toList());

        feedResponses.forEach(feed -> {
            List<byte[]> images = new ArrayList<>();
            List<ImagePath> paths = postImageService.findPathsByPostId(feed.getPostId());
            for (ImagePath path : paths) {
                images.add(postImageService.loadImage(path.getPath()));
            }
            feed.setImages(images);
        });

        return ResponseEntity.ok(feedResponses);
    }

    private FeedResponseDto getFeedResponse(Feed feed, Profile currentProfile) {
        Boolean isLiked = likeRepository.findByProfileId(currentProfile.getId(), feed.getPostId()).isPresent();
        return feedMapper.toFeedResponse(feed, isLiked);
    }
}
