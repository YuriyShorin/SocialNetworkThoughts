package hse.coursework.socialnetworkthoughts.dto.profile;

import hse.coursework.socialnetworkthoughts.dto.post.PostResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Schema(description = "Dto для получения профиля")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ProfileResponseDto {

    @Schema(description = "Id профиля", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    private UUID id;

    @Schema(description = "Никнейм", example = "LoveMyPosts")
    private String nickname;

    @Schema(description = "Статус", example = "Сплю...")
    private String status;

    @Schema(description = "Количество подписок", example = "156")
    private Long subscribes;

    @Schema(description = "Количество подписчиков", example = "3050134")
    private Long subscribers;

    @Schema(description = "Аватарка профиля")
    private byte[] profileImage;

    @Schema(description = "Список постов")
    List<PostResponseDto> posts;
}
