package io.github.nayetdet.insightvault.service;

import io.github.nayetdet.insightvault.payload.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TokenService {

    @Value("${security.jwt.token.access.expiry-in-seconds}")
    private Integer accessTokenExpiryInSeconds;

    @Value("${security.jwt.token.refresh.expiry-in-seconds}")
    private Integer refreshTokenExpiryInSeconds;

    @Value("${security.jwt.roles-claim-name}")
    private String rolesClaimName;

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public TokenDTO accessToken(String username, List<String> roles) {
        Instant createdAt = Instant.now();
        Instant accessExpiresAt = createdAt.plusSeconds(accessTokenExpiryInSeconds);
        Instant refreshExpiresAt = accessExpiresAt.plusSeconds(refreshTokenExpiryInSeconds);
        return TokenDTO
                .builder()
                .username(username)
                .accessToken(getAccessToken(username, roles, createdAt, accessExpiresAt))
                .refreshToken(getRefreshToken(username, roles, createdAt, refreshExpiresAt))
                .accessExpiresAt(LocalDateTime.ofInstant(accessExpiresAt, ZoneId.systemDefault()))
                .refreshExpiresAt(LocalDateTime.ofInstant(refreshExpiresAt, ZoneId.systemDefault()))
                .createdAt(LocalDateTime.ofInstant(createdAt, ZoneId.systemDefault()))
                .build();
    }

    public TokenDTO refreshToken(String refreshToken) {
        Jwt jwt = jwtDecoder.decode(refreshToken);
        List<String> roles = Optional.ofNullable(jwt.getClaimAsStringList(rolesClaimName)).orElse(List.of());
        return accessToken(jwt.getSubject(), roles);
    }

    private String getAccessToken(String username, List<String> roles, Instant createdAt, Instant expiresAt) {
        String issuer = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        JwtClaimsSet claims = JwtClaimsSet
                .builder()
                .issuer(issuer)
                .subject(username)
                .issuedAt(createdAt)
                .expiresAt(expiresAt)
                .claim(rolesClaimName, roles)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private String getRefreshToken(String username, List<String> roles, Instant createdAt, Instant expiresAt) {
        JwtClaimsSet claims = JwtClaimsSet
                .builder()
                .subject(username)
                .issuedAt(createdAt)
                .expiresAt(expiresAt)
                .claim(rolesClaimName, roles)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
