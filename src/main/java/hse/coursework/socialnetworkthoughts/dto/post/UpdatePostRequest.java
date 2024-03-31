package hse.coursework.socialnetworkthoughts.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Schema(description = "Update post request dto")
@Data
@Accessors(chain = true)
public class UpdatePostRequest {

    @Schema(description = "id", example = "e75e1be9-aadd-4144-9941-7b180cdbcff4")
    @NotNull(message = "Id cannot be null")
    private UUID id;

    @Schema(description = "Theme of the post", example = "Politics")
    @NotNull(message = "Theme of the post cannot be empty")
    private String theme;

    @Schema(description = "Content of the post",
            example = "The elections will take place in Russia on March 17")
    @NotNull(message = "Content of the post cannot be empty")
    private String content;
}
