package hse.coursework.socialnetworkthoughts.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "Dto для создания поста")
@Data
@Accessors(chain = true)
public class CreatePostRequestDto {

    @Schema(description = "Тема поста", example = "Спорт")
    @NotNull(message = "Тема поста не может быть пустой")
    private String theme;

    @Schema(description = "Содержимоке поста",
            example = "Металлург стал чемпионом КХЛ")
    @NotNull(message = "Содержисое поста не может быть пустым")
    private String content;

    @Schema(description = "Изображения")
    private MultipartFile[] images;
}
