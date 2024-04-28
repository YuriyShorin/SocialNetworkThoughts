package hse.coursework.socialnetworkthoughts.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Schema(description = "Dto для получения поста")
@Data
@Accessors(chain = true)
public class PostResponseDto {

    @Schema(description = "Id поста", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    private UUID id;

    @Schema(description = "Тема поста", example = "Спорт")
    private String theme;

    @Schema(description = "Содержимое поста",
            example = "Металлург стал чемпионом КХЛ")
    @NotNull(message = "Содержиое поста не может быть пустым")
    private String content;

    @Schema(description = "Количество лайков", example = "13656")
    private Long likes;

    @Schema(description = "Флаг, показывающий лайкнул ли этот пост авторизированный пользователь", example = "true")
    private Boolean isLiked;

    @Schema(description = "Количество репостов", example = "157")
    private Long reposts;

    @Schema(description = "Количество комментариев", example = "1398")
    private Long comments;

    @Schema(description = "Количество просмотров", example = "25345")
    private Long views;

    @Schema(description = "Id автора", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    private UUID authorId;

    @Schema(description = "Время создания поста", example = "2024-15-02 11:58:00")
    private Timestamp createdAt;

    @Schema(description = "Последнее время редактирования поста", example = "2024-15-02 11:58:00")
    private Timestamp editedAt;

    @Schema(description = "Файлы")
    private List<byte[]> files;
}
