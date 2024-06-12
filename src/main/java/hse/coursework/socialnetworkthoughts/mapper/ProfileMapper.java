package hse.coursework.socialnetworkthoughts.mapper;

import hse.coursework.socialnetworkthoughts.dto.post.PostResponseDto;
import hse.coursework.socialnetworkthoughts.dto.profile.ProfileResponseDto;
import hse.coursework.socialnetworkthoughts.dto.profile.SearchProfileResponseDto;
import hse.coursework.socialnetworkthoughts.model.ImagePath;
import hse.coursework.socialnetworkthoughts.model.Post;
import hse.coursework.socialnetworkthoughts.model.Profile;
import hse.coursework.socialnetworkthoughts.repository.SubscriptionRepository;
import hse.coursework.socialnetworkthoughts.service.ProfileImageService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = PostMapper.class)
public abstract class ProfileMapper {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ProfileImageService profileFileService;

    @Autowired
    private PostMapper postMapper;

    @Mapping(target = "posts", expression = "java(toPostResponseList(profile.getPosts(), profile.getId()))")
    @Mapping(source = "id", target = "profileImage", qualifiedByName = "getProfileImage")
    public abstract ProfileResponseDto toProfileResponse(Profile profile);

    @Mapping(target = "isSubscribed", expression = "java(getSubscribeStatus(profile, currentProfileId))")
    @Mapping(source = "profile.id", target = "profileImage", qualifiedByName = "getProfileImage")
    public abstract SearchProfileResponseDto toSearchProfileResponse(Profile profile, UUID currentProfileId);

    protected List<PostResponseDto> toPostResponseList(List<Post> posts, UUID profileId){
        return postMapper.postListToPostResponseList(posts, profileId);
    }

    protected Boolean getSubscribeStatus(Profile profile, UUID currentProfileId){
        return subscriptionRepository.findByProfileIdAndSubscriptionProfileId(currentProfileId, profile.getId())
                .isPresent();
    }

    @Named("getProfileImage")
    protected byte[] getProfileImage(UUID profileId) {
        ImagePath profileImage = profileFileService.findPathByProfileId(profileId);

        if (profileImage == null) {
            return new byte[0];
        }

        return profileFileService.loadImage(profileImage.getPath());
    }
}
