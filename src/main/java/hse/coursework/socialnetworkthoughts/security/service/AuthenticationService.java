package hse.coursework.socialnetworkthoughts.security.service;

import hse.coursework.socialnetworkthoughts.mapper.ProfileMapper;
import hse.coursework.socialnetworkthoughts.security.dto.LoginUserCredentialsDto;
import hse.coursework.socialnetworkthoughts.security.dto.RegisterUserCredentialsDto;
import hse.coursework.socialnetworkthoughts.security.mapper.UserMapper;
import hse.coursework.socialnetworkthoughts.security.model.Id;
import hse.coursework.socialnetworkthoughts.security.model.Role;
import hse.coursework.socialnetworkthoughts.security.model.User;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserMapper userMapper;

    private final ProfileMapper profileMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> signup(RegisterUserCredentialsDto registerUserCredentialsDto) {
        if (userMapper.findByUsername(registerUserCredentialsDto.getUsername()).isPresent()) {
            return ResponseEntity.status(409).build();
        }

        User user = new User(
                registerUserCredentialsDto.getUsername(),
                passwordEncoder.encode(registerUserCredentialsDto.getPassword()),
                Role.USER.name()
        );

        Id userId = userMapper.save(user);
        profileMapper.save(userId.getId(), registerUserCredentialsDto.getNickname());

        return ResponseEntity.ok().build();
    }

    public User authenticate(LoginUserCredentialsDto loginUserCredentialsDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserCredentialsDTO.getUsername(),
                        loginUserCredentialsDTO.getPassword()
                )
        );

        return userMapper.findByUsername(loginUserCredentialsDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
