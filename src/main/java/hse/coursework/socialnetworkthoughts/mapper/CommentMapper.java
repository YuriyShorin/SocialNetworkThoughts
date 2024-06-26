package hse.coursework.socialnetworkthoughts.mapper;

import hse.coursework.socialnetworkthoughts.dto.comment.CommentPostRequestDto;
import hse.coursework.socialnetworkthoughts.dto.comment.CommentResponseDto;
import hse.coursework.socialnetworkthoughts.model.Comment;
import hse.coursework.socialnetworkthoughts.model.ImagePath;
import hse.coursework.socialnetworkthoughts.service.ProfileImageService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    @Autowired
    private ProfileImageService profileFileService;

    public abstract Comment toComment(CommentPostRequestDto commentPostRequestDto);

    @Mapping(source = "profileId", target = "profileImage", qualifiedByName = "getProfileImage")
    public abstract CommentResponseDto toCommentResponseDto(Comment comment);

    @Named("getProfileImage")
    protected byte[] getProfileImage(UUID profileId) {
        ImagePath profileImage = profileFileService.findPathByProfileId(profileId);

        if (profileImage == null) {
            return new byte[0];
        }

        return profileFileService.loadImage(profileImage.getPath());
    }
}

