package hse.coursework.socialnetworkthoughts.security.controller;

import hse.coursework.socialnetworkthoughts.security.dto.LoginDTO;
import hse.coursework.socialnetworkthoughts.security.dto.LoginResponseDTO;
import hse.coursework.socialnetworkthoughts.security.dto.RegisterDTO;
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
    public ResponseEntity<User> register(@RequestBody RegisterDTO registerDTO) {
        return authenticationService.signup(registerDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginDTO loginDTO) {
        User authenticatedUser = authenticationService.authenticate(loginDTO);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(jwtToken, jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponseDTO);
    }
}