package hse.coursework.socialnetworkthoughts.dto.feed;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Schema(description = "Dto для получения ленты")
@Data
@Accessors(chain = true)
public class FeedResponseDto {

    @Schema(description = "Id поста", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    private UUID postId;

    @Schema(description = "Id профиля", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    private UUID profileId;

    @Schema(description = "Никнейм")
    private String profileNickname;

    @Schema(description = "Аватарка профиля")
    private byte[] profileImage;

    @Schema(description = "Тема поста", example = "Politics")
    private String theme;

    @Schema(description = "Содержимое поста",
            example = "The elections will take place in Russia on March 17")
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

    @Schema(description = "Изображения")
    private List<byte[]> images;
}
