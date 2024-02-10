package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.dto.PostResponse;
import hse.coursework.socialnetworkthoughts.dto.ProfileDto;
import hse.coursework.socialnetworkthoughts.exception.ProfileNotFoundException;
import hse.coursework.socialnetworkthoughts.mapper.ProfileMapper;
import hse.coursework.socialnetworkthoughts.model.Post;
import hse.coursework.socialnetworkthoughts.model.Profile;

import hse.coursework.socialnetworkthoughts.security.model.User;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileMapper profileMapper;

    public ResponseEntity<?> getProfile(User user) {
        Profile profile = profileMapper
                .findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);

        List<PostResponse> postResponses = new ArrayList<>();
        for (Post post : profile.getPosts()) {
            postResponses.add(new PostResponse(post.getId(), post.getProfileId(), post.getTheme(), post.getContent(), post.getLikes(), post.getReposts(), post.getComments(), post.getViews(), post.getAuthorId(), post.getCreatedAt(), post.getEditedAt()));
        }

        ProfileDto profileDto = new ProfileDto(profile.getId(), profile.getNickname(), profile.getStatus(), profile.getDescription(), profile.getSubscribes(), profile.getSubscribers(), postResponses);
        return ResponseEntity.ok(profileDto);
    }
}
