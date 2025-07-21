package io.github.nayetdet.insightvault.service;

import io.github.nayetdet.insightvault.exception.UserNotFoundException;
import io.github.nayetdet.insightvault.mapper.UserMapper;
import io.github.nayetdet.insightvault.model.User;
import io.github.nayetdet.insightvault.payload.dto.TokenDTO;
import io.github.nayetdet.insightvault.payload.request.UserLoginRequest;
import io.github.nayetdet.insightvault.payload.request.UserRefreshTokenRequest;
import io.github.nayetdet.insightvault.payload.request.UserRegistrationRequest;
import io.github.nayetdet.insightvault.repository.UserRepository;
import io.github.nayetdet.insightvault.service.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidator userValidator;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional(readOnly = true)
    public TokenDTO login(UserLoginRequest request) {
        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        return tokenService.accessToken(user.getUsername(), user.getRoles());
    }

    @Transactional
    public TokenDTO register(UserRegistrationRequest request) {
        User user = userMapper.toModel(request, passwordEncoder.encode(request.getPassword()));
        userValidator.validate(user);
        userRepository.save(user);
        return tokenService.accessToken(user.getUsername(), user.getRoles());
    }

    @Transactional(readOnly = true)
    public TokenDTO refresh(UserRefreshTokenRequest request) {
        if (Boolean.FALSE.equals(userRepository.existsByUsername(request.getUsername()))) {
            throw new UserNotFoundException();
        }

        return tokenService.refreshToken(request.getRefreshToken());
    }

}
