package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.dto.PostDto;
import hse.coursework.socialnetworkthoughts.exception.ProfileNotFoundException;
import hse.coursework.socialnetworkthoughts.mapper.PostMapper;
import hse.coursework.socialnetworkthoughts.mapper.ProfileMapper;
import hse.coursework.socialnetworkthoughts.model.Post;
import hse.coursework.socialnetworkthoughts.model.Profile;
import hse.coursework.socialnetworkthoughts.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final ProfileMapper profileMapper;

    private final PostMapper postMapper;

    public ResponseEntity<?> createPost(User user, PostDto postDto) {
        Profile profile = profileMapper.findByUserId(user.getId()).orElseThrow(ProfileNotFoundException::new);
        postMapper.save(new Post(profile.getId(), postDto.getTheme(), postDto.getContent(), profile.getId()));

        return ResponseEntity.ok().build();
    }
}
