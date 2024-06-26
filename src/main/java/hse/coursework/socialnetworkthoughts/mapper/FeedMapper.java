package hse.coursework.socialnetworkthoughts.mapper;

import hse.coursework.socialnetworkthoughts.dto.feed.FeedResponseDto;
import hse.coursework.socialnetworkthoughts.model.Feed;
import hse.coursework.socialnetworkthoughts.model.ImagePath;
import hse.coursework.socialnetworkthoughts.service.ProfileImageService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class FeedMapper {

    @Autowired
    private ProfileImageService profileFileService;

    @Mapping(source = "feed.profileId", target = "profileImage", qualifiedByName = "getProfileImage")
    public abstract FeedResponseDto toFeedResponse(Feed feed, Boolean isLiked);

    @Named("getProfileImage")
    protected byte[] getProfileImage(UUID profileId) {
        ImagePath profileImage = profileFileService.findPathByProfileId(profileId);

        if (profileImage == null) {
            return new byte[0];
        }

        return profileFileService.loadImage(profileImage.getPath());
    }
}
