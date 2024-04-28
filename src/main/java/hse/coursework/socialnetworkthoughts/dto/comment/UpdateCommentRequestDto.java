package hse.coursework.socialnetworkthoughts.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Schema(description = "Dto изменения поста")
@Data
@Accessors(chain = true)
public class UpdateCommentRequestDto {

    @NotNull(message = "Id комментария не может быть пустым")
    @Schema(description = "Id поста", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    private UUID commentId;

    @NotNull(message = "Содержимое комментария не может быть пустым")
    @Schema(description = "Содержимое поста", example = "Прекрасный пост!")
    private String content;
}
