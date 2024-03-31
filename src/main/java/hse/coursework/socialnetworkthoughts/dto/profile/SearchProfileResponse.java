package hse.coursework.socialnetworkthoughts.dto.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Schema(description = "Search profiles response dto")
@Data
@Accessors(chain = true)
public class SearchProfileResponse {

    @Schema(description = "Profile id", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    private UUID id;

    @Schema(description = "Nickname", example = "loveMyPosts")
    private String nickname;

    @Schema(description = "Is subscribed", example = "true")
    private Boolean isSubscribed;
}