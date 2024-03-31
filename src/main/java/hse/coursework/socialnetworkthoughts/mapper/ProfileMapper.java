package hse.coursework.socialnetworkthoughts.mapper;

import hse.coursework.socialnetworkthoughts.dto.post.PostResponse;
import hse.coursework.socialnetworkthoughts.dto.profile.ProfileResponse;
import hse.coursework.socialnetworkthoughts.dto.profile.SearchProfileResponse;
import hse.coursework.socialnetworkthoughts.model.Post;
import hse.coursework.socialnetworkthoughts.model.Profile;
import hse.coursework.socialnetworkthoughts.repository.SubscriptionRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = PostMapper.class)
public abstract class ProfileMapper {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private PostMapper postMapper;

    @Mapping(target = "posts", expression = "java(toPostResponseList(profile.getPosts(), profile.getId()))")
    public abstract ProfileResponse toProfileResponse(Profile profile);

    @Mapping(target = "isSubscribed", expression = "java(getSubscribeStatus(profile, currentProfileId))")
    public abstract SearchProfileResponse toSearchProfileResponse(Profile profile, UUID currentProfileId);

    protected List<PostResponse> toPostResponseList(List<Post> posts, UUID profileId){
        return postMapper.postListToPostResponseList(posts, profileId);
    }
    protected Boolean getSubscribeStatus(Profile profile, UUID currentProfileId){
        return subscriptionRepository.findByProfileIdAndSubscriptionProfileId(currentProfileId, profile.getId())
                .isPresent();
    }
}
