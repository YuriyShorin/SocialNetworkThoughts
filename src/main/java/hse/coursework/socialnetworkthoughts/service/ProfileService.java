package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.dto.ProfileDto;
import hse.coursework.socialnetworkthoughts.exception.ProfileNotFoundException;
import hse.coursework.socialnetworkthoughts.mapper.ProfileMapper;
import hse.coursework.socialnetworkthoughts.mapping.ProfileMapping;
import hse.coursework.socialnetworkthoughts.model.Profile;

import hse.coursework.socialnetworkthoughts.security.model.User;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileMapper profileMapper;
    private final ProfileMapping profileMapping;

    public ResponseEntity<?> getProfile(User user) {
        Profile profile = profileMapper
                .findByUserId(user.getId())
                .orElseThrow(ProfileNotFoundException::new);

        ProfileDto profileDto = profileMapping.toProfileDto(profile);
        return ResponseEntity.ok(profileDto);
    }
}
