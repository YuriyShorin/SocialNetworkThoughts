package hse.coursework.socialnetworkthoughts.dto.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
@Schema(description = "Dto для ответа о подписках пользователя")
public class SubscriptionResponseDto {

    @Schema(description = "Id профиля, на которого подписан запрашиваемый профиль")
    private UUID profileId;

    @Schema(description = "nickname профиля, на которого подписан запрашиваемый профиль.")
    private String nickname;

    @Schema(description = "Подписан ли авторизированный пользователь на этот профиль")
    private Boolean isSubscribed;
}
