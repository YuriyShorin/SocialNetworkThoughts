package hse.coursework.socialnetworkthoughts.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.UUID;

@Schema(description = "Dto для создания поста")
@Data
@Accessors(chain = true)
public class CommentResponseDto {

    @Schema(description = "Id комментария", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    private UUID id;

    @Schema(description = "Id профиля", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    private UUID profileId;

    @Schema(description = "Id поста", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    private UUID postId;

    @Schema(description = "Содержимое комментария", example = "Прекрасный пост")
    private String content;

    @Schema(description = "Количество лайков комментария", example = "156")
    private Long likes;

    @Schema(description = "Время создания комментария", example = "2024-15-02 11:58:00")
    private Timestamp createdAt;

    @Schema(description = "Последнее время редактирования комментария", example = "2024-15-02 11:58:00")
    private Timestamp editedAt;
}
