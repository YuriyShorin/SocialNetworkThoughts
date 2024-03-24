package hse.coursework.socialnetworkthoughts.dto.feed;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Schema(description = "Feed response dto")
@Data
@AllArgsConstructor
public class FeedResponse {

    @Schema(description = "postId", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    private UUID postId;

    @Schema(description = "Profile Id", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    private UUID profileId;

    @Schema(description = "Profile nickname")
    private String profileNickname;

    @Schema(description = "Theme of the post", example = "Politics")
    private String theme;

    @Schema(description = "Content of the post",
            example = "The elections will take place in Russia on March 17")
    private String content;

    @Schema(description = "Likes", example = "13656")
    private Long likes;

    @Schema(description = "Is liked by current user", example = "true")
    private Boolean isLiked;

    @Schema(description = "Reposts", example = "157")
    private Long reposts;

    @Schema(description = "Comments", example = "1398")
    private Long comments;

    @Schema(description = "Views", example = "25345")
    private Long views;

    @Schema(description = "Author Id", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    private UUID authorId;

    @Schema(description = "Post was created at", example = "2024-15-02 11:58:00")
    private Timestamp createdAt;

    @Schema(description = "Post was edited at", example = "2024-15-02 11:58:00")
    private Timestamp editedAt;
}
