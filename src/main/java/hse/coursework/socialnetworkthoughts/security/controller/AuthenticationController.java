package hse.coursework.socialnetworkthoughts.security.controller;

import hse.coursework.socialnetworkthoughts.security.dto.LoginUserCredentialsDto;
import hse.coursework.socialnetworkthoughts.security.dto.JwtTokenDto;
import hse.coursework.socialnetworkthoughts.security.dto.RegisterUserCredentialsDto;
import hse.coursework.socialnetworkthoughts.security.model.User;
import hse.coursework.socialnetworkthoughts.security.service.AuthenticationService;
import hse.coursework.socialnetworkthoughts.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;


    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterUserCredentialsDto registerUserCredentialsDto) {
        return authenticationService.signup(registerUserCredentialsDto);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> authenticate(@RequestBody LoginUserCredentialsDto loginUserCredentialsDTO) {
        User authenticatedUser = authenticationService.authenticate(loginUserCredentialsDTO);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        JwtTokenDto jwtTokenDto = new JwtTokenDto(jwtToken, jwtService.getExpirationTime());

        return ResponseEntity.ok(jwtTokenDto);
    }
}