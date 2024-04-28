package hse.coursework.socialnetworkthoughts.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Schema(description = "Dto для изменения поста")
@Data
@Accessors(chain = true)
public class UpdatePostRequestDto {

    @Schema(description = "Id поста", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    @NotNull(message = "Id не может быть пустым")
    private UUID id;

    @Schema(description = "Тема поста", example = "Спорт")
    private String theme;

    @Schema(description = "Содержимое поста",
            example = "Металлург стал чемпионом КХЛ")
    @NotNull(message = "Содержиое поста не может быть пустым")
    private String content;
}
