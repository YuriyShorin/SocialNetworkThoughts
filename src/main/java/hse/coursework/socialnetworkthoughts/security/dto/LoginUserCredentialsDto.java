package hse.coursework.socialnetworkthoughts.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "Login credentials")
@Data
public class LoginUserCredentialsDto {

    @Schema(description = "username", example = "PostMaker")
    @NotNull
    private String username;

    @Schema(description = "password", example = "KnpsZ6AfFUHb3uNy")
    @NotNull
    private String password;
}
