package hse.coursework.socialnetworkthoughts.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "Register Credentials")
@Data
public class RegisterUserCredentialsDto {

    @Schema(description = "username", example = "PostMaker")
    @NotNull
    private String username;

    @Schema(description = "password", example = "KnpsZ6AfFUHb3uNy")
    @NotNull
    private String password;

    @Schema(description = "nickname", example = "loveMyPosts")
    @NotNull
    private String nickname;
}
