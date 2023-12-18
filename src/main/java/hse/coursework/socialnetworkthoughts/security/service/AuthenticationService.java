package hse.coursework.socialnetworkthoughts.security.service;

import hse.coursework.socialnetworkthoughts.security.dto.LoginDTO;
import hse.coursework.socialnetworkthoughts.security.dto.RegisterDTO;
import hse.coursework.socialnetworkthoughts.security.exception.UserAlreadyExistsException;
import hse.coursework.socialnetworkthoughts.security.mapper.UserMapper;
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

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public ResponseEntity<User> signup(RegisterDTO registerDTO) {
        if (userMapper.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        User user = new User(
                registerDTO.getUsername(),
                passwordEncoder.encode(registerDTO.getPassword()),
                Role.USER.name()
        );
        userMapper.save(user);

        return ResponseEntity.ok(user);
    }

    public User authenticate(LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );

        return userMapper.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
