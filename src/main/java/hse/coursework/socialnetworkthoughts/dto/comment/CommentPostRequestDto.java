package hse.coursework.socialnetworkthoughts.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Schema(description = "Dto для создания поста")
@Data
@Accessors(chain = true)
public class CommentPostRequestDto {

    @NotNull(message = "Id поста не может быть пустым")
    @Schema(description = "Id поста", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    private UUID postId;

    @NotNull(message = "Содержимое комментария не может быть пустым")
    @Schema(description = "Содержимое поста", example = "Прекрасные пост!")
    private String content;
}
