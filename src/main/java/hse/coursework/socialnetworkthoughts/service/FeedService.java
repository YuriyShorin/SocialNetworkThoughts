package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.dto.feed.FeedResponse;
import hse.coursework.socialnetworkthoughts.exception.ProfileNotFoundException;
import hse.coursework.socialnetworkthoughts.model.Feed;
import hse.coursework.socialnetworkthoughts.model.Profile;
import hse.coursework.socialnetworkthoughts.repository.FeedRepository;
import hse.coursework.socialnetworkthoughts.repository.PostRepository;
import hse.coursework.socialnetworkthoughts.repository.ProfileRepository;
import hse.coursework.socialnetworkthoughts.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final ProfileRepository profileRepository;

    private final FeedRepository feedRepository;

    private final PostRepository postRepository;

    public ResponseEntity<?> getFeed(User user) {
        Profile profile = profileRepository.findByUserId(user.getId()).orElseThrow(ProfileNotFoundException::new);
        List<Feed> feeds = feedRepository.getFeedByProfileId(profile.getId());
        List<FeedResponse> feedResponses = new ArrayList<>();

        for (Feed feed : feeds) {
            Boolean isLiked = postRepository.findLike(profile.getId(), feed.getPostId()).isPresent();

            FeedResponse feedResponse = new FeedResponse(
                    feed.getPostId(),
                    feed.getProfileId(),
                    feed.getProfileNickname(),
                    feed.getTheme(),
                    feed.getContent(),
                    feed.getLikes(),
                    isLiked,
                    feed.getReposts(),
                    feed.getComments(),
                    feed.getViews(),
                    feed.getAuthorId(),
                    feed.getCreatedAt(),
                    feed.getEditedAt());

            feedResponses.add(feedResponse);
        }

        return ResponseEntity.ok(feedResponses);
    }
}