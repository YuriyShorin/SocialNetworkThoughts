package hse.coursework.socialnetworkthoughts.security.service;

import hse.coursework.socialnetworkthoughts.repository.ProfileRepository;
import hse.coursework.socialnetworkthoughts.security.dto.LoginUserCredentialsDto;
import hse.coursework.socialnetworkthoughts.security.dto.RegisterUserCredentialsDto;
import hse.coursework.socialnetworkthoughts.security.repository.UserRepository;
import hse.coursework.socialnetworkthoughts.model.Id;
import hse.coursework.socialnetworkthoughts.security.model.Role;
import hse.coursework.socialnetworkthoughts.security.model.User;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final ProfileRepository profileRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Transactional
    public ResponseEntity<?> signup(RegisterUserCredentialsDto registerUserCredentialsDto) {
        if (userRepository.findByUsername(registerUserCredentialsDto.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User user = new User(
                registerUserCredentialsDto.getUsername(),
                passwordEncoder.encode(registerUserCredentialsDto.getPassword()),
                Role.USER.name()
        );

        Id userId = userRepository.save(user);
        profileRepository.save(userId.getId(), registerUserCredentialsDto.getNickname());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Transactional(readOnly = true)
    public User authenticate(LoginUserCredentialsDto loginUserCredentialsDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserCredentialsDTO.getUsername(),
                        loginUserCredentialsDTO.getPassword()
                )
        );

        return userRepository.findByUsername(loginUserCredentialsDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
