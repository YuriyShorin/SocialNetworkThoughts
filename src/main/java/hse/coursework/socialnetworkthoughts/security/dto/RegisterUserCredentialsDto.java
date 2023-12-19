package hse.coursework.socialnetworkthoughts.security.dto;

import lombok.Data;

@Data
public class RegisterUserCredentialsDto {

    private String username;

    private String password;

    private String nickname;
}
