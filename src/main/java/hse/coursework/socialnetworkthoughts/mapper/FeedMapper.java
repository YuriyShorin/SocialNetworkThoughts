package hse.coursework.socialnetworkthoughts.mapper;

import hse.coursework.socialnetworkthoughts.dto.feed.FeedResponse;
import hse.coursework.socialnetworkthoughts.model.Feed;
import hse.coursework.socialnetworkthoughts.model.FilePath;
import hse.coursework.socialnetworkthoughts.service.ProfileFileService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class FeedMapper {

    @Autowired
    private ProfileFileService profileFileService;

    @Mapping(source = "feed.profileId", target = "profileImage", qualifiedByName = "getProfileImage")
    public abstract FeedResponse toFeedResponse(Feed feed, Boolean isLiked);

    @Named("getProfileImage")
    protected byte[] getProfileImage(UUID profileId) {
        FilePath profileImage = profileFileService.findPathsByProfileId(profileId);

        if (profileImage == null) {
            return new byte[0];
        }

        return profileFileService.loadFile(profileImage.getPath());
    }
}
