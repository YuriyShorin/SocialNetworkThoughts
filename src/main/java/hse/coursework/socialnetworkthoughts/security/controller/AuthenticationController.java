package hse.coursework.socialnetworkthoughts.security.controller;

import hse.coursework.socialnetworkthoughts.security.dto.LoginUserCredentialsDto;
import hse.coursework.socialnetworkthoughts.security.dto.JwtTokenDto;
import hse.coursework.socialnetworkthoughts.security.dto.RegisterUserCredentialsDto;
import hse.coursework.socialnetworkthoughts.security.model.User;
import hse.coursework.socialnetworkthoughts.security.service.AuthenticationService;
import hse.coursework.socialnetworkthoughts.security.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Signup")
    @ApiResponse(
            responseCode = "201",
            description = "Signed up successfully")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegisterUserCredentialsDto registerUserCredentialsDto) {
        return authenticationService.signup(registerUserCredentialsDto);
    }

    @Operation(summary = "Login")
    @ApiResponse(
            responseCode = "200",
            description = "Login is successful",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = JwtTokenDto.class))})
    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody LoginUserCredentialsDto loginUserCredentialsDTO) {
        User authenticatedUser = authenticationService.authenticate(loginUserCredentialsDTO);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        JwtTokenDto jwtTokenDto = new JwtTokenDto(jwtToken);

        return ResponseEntity.ok(jwtTokenDto);
    }
}