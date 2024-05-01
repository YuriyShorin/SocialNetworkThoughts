package hse.coursework.socialnetworkthoughts.security.controller;

import hse.coursework.socialnetworkthoughts.security.dto.JwtTokenDto;
import hse.coursework.socialnetworkthoughts.security.dto.LoginUserCredentialsDto;
import hse.coursework.socialnetworkthoughts.security.dto.RegisterUserCredentialsDto;
import hse.coursework.socialnetworkthoughts.security.model.User;
import hse.coursework.socialnetworkthoughts.security.service.AuthenticationService;
import hse.coursework.socialnetworkthoughts.security.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication controller", description = "Authentication API")
@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegisterUserCredentialsDto registerUserCredentialsDto) {
        return authenticationService.signup(registerUserCredentialsDto);
    }

    @Operation(summary = "Вход")
    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody LoginUserCredentialsDto loginUserCredentialsDTO) {
        User authenticatedUser = authenticationService.authenticate(loginUserCredentialsDTO);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        JwtTokenDto jwtTokenDto = new JwtTokenDto(jwtToken);

        return ResponseEntity.ok(jwtTokenDto);
    }

    @Operation(summary = "Проверка токена")
    @GetMapping("/me")
    public ResponseEntity<?> me() {
        return ResponseEntity.ok().build();
    }
}