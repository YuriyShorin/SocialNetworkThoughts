package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.dto.feed.FeedResponse;
import hse.coursework.socialnetworkthoughts.exception.ProfileNotFoundException;
import hse.coursework.socialnetworkthoughts.mapper.FeedMapper;
import hse.coursework.socialnetworkthoughts.model.Feed;
import hse.coursework.socialnetworkthoughts.model.Profile;
import hse.coursework.socialnetworkthoughts.repository.FeedRepository;
import hse.coursework.socialnetworkthoughts.repository.LikeRepository;
import hse.coursework.socialnetworkthoughts.repository.ProfileRepository;
import hse.coursework.socialnetworkthoughts.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;

    private final FeedMapper feedMapper;

    private final ProfileRepository profileRepository;

    private final LikeRepository likeRepository;

    public ResponseEntity<?> getFeed(User user) {
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);

        List<Feed> feeds = feedRepository.getFeedByProfileId(profile.getId());
        List<FeedResponse> feedResponses = feeds.stream()
                .map(feed -> getFeedResponse(feed, profile))
                .collect(Collectors.toList());

        return ResponseEntity.ok(feedResponses);
    }

    private FeedResponse getFeedResponse(Feed feed, Profile profile) {
        Boolean isLiked = likeRepository.findByProfileId(profile.getId(), feed.getPostId()).isPresent();
        return feedMapper.toFeedResponse(feed, isLiked);
    }
}