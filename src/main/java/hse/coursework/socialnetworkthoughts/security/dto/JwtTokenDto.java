package hse.coursework.socialnetworkthoughts.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Jwt token dto")
@Data
@AllArgsConstructor
public class JwtTokenDto {

    @Schema(description = "Jwt token")
    private String jwtToken;
}
