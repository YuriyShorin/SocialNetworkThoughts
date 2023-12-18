package hse.coursework.socialnetworkthoughts.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

    private String token;

    private long expiresIn;
}
