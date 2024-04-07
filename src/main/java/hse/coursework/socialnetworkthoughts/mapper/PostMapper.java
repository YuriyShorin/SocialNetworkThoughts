package hse.coursework.socialnetworkthoughts.mapper;

import hse.coursework.socialnetworkthoughts.dto.post.PostResponse;
import hse.coursework.socialnetworkthoughts.model.Post;
import hse.coursework.socialnetworkthoughts.repository.LikeRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private LikeRepository likeRepository;

    @Mapping(target = "isLiked", expression = "java(getLikeStatus(post, profileId))")
    public abstract PostResponse toPostResponse(Post post, UUID profileId);

    public List<PostResponse> postListToPostResponseList(List<Post> posts, UUID profileId) {
        return posts.stream()
                .map(post -> toPostResponse(post, profileId))
                .collect(Collectors.toList());
    }

    protected Boolean getLikeStatus(Post post, UUID profileId) {
        return likeRepository.findByProfileId(profileId, post.getId())
                .isPresent();
    }
}
