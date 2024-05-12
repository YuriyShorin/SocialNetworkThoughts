package hse.coursework.socialnetworkthoughts.dto.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Schema(description = "Dto для получения профиля в поиске")
@Data
@Accessors(chain = true)
public class SearchProfileResponseDto {

    @Schema(description = "Id профиля", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    private UUID id;

    @Schema(description = "Никнейм", example = "loveMyPosts")
    private String nickname;

    @Schema(description = "Аватарка профиля")
    private byte[] profileImage;

    @Schema(description = "Флаг, показывающий подписан ли авторизированный пользователь на этот профиль", example = "true")
    private Boolean isSubscribed;
}