package hse.coursework.socialnetworkthoughts.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Schema(description = "Dto для создания поста")
@Data
@Accessors(chain = true)
public class AuthorResponseDto {

    @Schema(description = "Id автора", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    private UUID id;

    @Schema(description = "Никнейм автора", example = "LoveMyPosts")
    private String nickname;

    @Schema(description = "Аватарка автора")
    private byte[] authorImage;
}
