package hse.coursework.socialnetworkthoughts.dto.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Schema(description = "Дто для изменения профиля")
public class UpdateProfileRequestDto {

    @Schema(description = "Никнейм профиля")
    private String nickname;

    @Schema(description = "Статус профиля")
    private String status;

    @Schema(description = "Фотография профиля")
    private MultipartFile profilePicture;
}
