package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.dto.post.SearchPostResponse;
import hse.coursework.socialnetworkthoughts.dto.profile.SearchProfileResponse;
import hse.coursework.socialnetworkthoughts.exception.ProfileNotFoundException;
import hse.coursework.socialnetworkthoughts.mapper.ProfileMapper;
import hse.coursework.socialnetworkthoughts.model.Profile;
import hse.coursework.socialnetworkthoughts.repository.ProfileRepository;
import hse.coursework.socialnetworkthoughts.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public ResponseEntity<List<SearchProfileResponse>> searchProfilesByNickname(String nickname, User user) {
        Profile currentProfile = profileRepository.findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);

        List<Profile> profiles = profileRepository.findByNickname(nickname);
        List<SearchProfileResponse> searchProfileResponses = profiles.stream()
                .map(profile -> profileMapper.toSearchProfileResponse(profile, currentProfile.getId()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(searchProfileResponses);
    }

    public ResponseEntity<List<SearchPostResponse>> searchPostsByTheme(String theme) {
        return ResponseEntity.ok(new ArrayList<>());
    }
}
