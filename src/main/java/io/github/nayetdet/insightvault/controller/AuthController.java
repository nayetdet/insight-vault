package io.github.nayetdet.insightvault.controller;

import io.github.nayetdet.insightvault.controller.docs.AuthControllerDocs;
import io.github.nayetdet.insightvault.payload.dto.TokenDTO;
import io.github.nayetdet.insightvault.payload.request.UserLoginRequest;
import io.github.nayetdet.insightvault.payload.request.UserRefreshTokenRequest;
import io.github.nayetdet.insightvault.payload.request.UserRegistrationRequest;
import io.github.nayetdet.insightvault.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Auth", description = "Endpoints for managing user authentication")
public class AuthController implements AuthControllerDocs {

    private final AuthService authService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid UserLoginRequest request) {
        TokenDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(@RequestBody @Valid UserRegistrationRequest request) {
        TokenDTO response = authService.register(request);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{username}")
                .buildAndExpand(response.getUsername())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refresh(@RequestBody @Valid UserRefreshTokenRequest request) {
        TokenDTO response = authService.refresh(request);
        return ResponseEntity.ok(response);
    }

}
