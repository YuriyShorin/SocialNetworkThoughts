package hse.coursework.socialnetworkthoughts.mapper;

import hse.coursework.socialnetworkthoughts.dto.feed.FeedResponse;
import hse.coursework.socialnetworkthoughts.model.Feed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class FeedMapper {

    public abstract FeedResponse toFeedResponse(Feed feed, Boolean isLiked);
}
