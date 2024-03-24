package hse.coursework.socialnetworkthoughts.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Create post request dto")
@Data
@AllArgsConstructor
public class CreatePostRequest {

    @Schema(description = "Theme of the post", example = "Politics")
    @NotNull(message = "Theme of the post cannot be empty")
    private String theme;

    @Schema(description = "Content of the post",
            example = "The elections will take place in Russia on March 17")
    @NotNull(message = "Content of the post cannot be empty")
    private String content;
}
