package io.github.nayetdet.insightvault.mapper;

import io.github.nayetdet.insightvault.model.User;
import io.github.nayetdet.insightvault.payload.dto.UserDTO;
import io.github.nayetdet.insightvault.payload.request.UserRegistrationRequest;
import io.github.nayetdet.insightvault.payload.request.UserUpdateRequest;
import io.github.nayetdet.insightvault.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final UserRepository userRepository;

    public User toModel(UserRegistrationRequest request, String encodedPassword) {
        return User
                .builder()
                .username(request.getUsername())
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .build();
    }

    public UserDTO toDTO(User user) {
        return UserDTO
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public void update(User user, UserUpdateRequest request) {
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
    }

    public User toModel(String username) {
        return userRepository
                .findByUsername(username)
                .orElse(null);
    }

}
