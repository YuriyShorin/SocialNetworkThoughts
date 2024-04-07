package hse.coursework.socialnetworkthoughts.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "Create post with files request dto")
@Data
@Accessors(chain = true)
public class CreatePostWithFilesRequest {

    @Schema(description = "Theme of the post", example = "Politics")
    @NotNull(message = "Theme of the post cannot be empty")
    private String theme;

    @Schema(description = "Content of the post",
            example = "The elections will take place in Russia on March 17")
    @NotNull(message = "Content of the post cannot be empty")
    private String content;

    @Schema(description = "files")
    private MultipartFile[] files;
}
