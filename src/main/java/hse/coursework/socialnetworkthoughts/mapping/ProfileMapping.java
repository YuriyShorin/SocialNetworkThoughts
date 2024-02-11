package hse.coursework.socialnetworkthoughts.mapping;

import hse.coursework.socialnetworkthoughts.dto.ProfileDto;
import hse.coursework.socialnetworkthoughts.model.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProfileMapping {

    @Mapping(source = "posts", target = "postDtos")
    public abstract ProfileDto toProfileDto(Profile profile);
}
