package hse.coursework.socialnetworkthoughts.dto.profile;

import hse.coursework.socialnetworkthoughts.dto.post.PostResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Schema(description = "Profile response dto")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ProfileResponse {

    @Schema(description = "id", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    private UUID id;

    @Schema(description = "Nickname", example = "loveMyPosts")
    private String nickname;

    @Schema(description = "Status", example = "Sleeping...")
    private String status;

    @Schema(description = "Description", example = "Best account ever")
    private String description;

    @Schema(description = "Subscribes", example = "156")
    private Long subscribes;

    @Schema(description = "Subscribers", example = "3050134")
    private Long subscribers;

    @Schema(description = "Posts")
    List<PostResponse> posts;
}
