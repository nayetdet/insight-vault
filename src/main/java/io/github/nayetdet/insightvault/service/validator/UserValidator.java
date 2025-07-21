package io.github.nayetdet.insightvault.service.validator;

import io.github.nayetdet.insightvault.exception.UserEmailConflictException;
import io.github.nayetdet.insightvault.exception.UserUsernameConflictException;
import io.github.nayetdet.insightvault.model.User;
import io.github.nayetdet.insightvault.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserValidator {

    private final UserRepository userRepository;

    public void validate(User user) {
        if (isUsernameDuplicated(user)) {
            throw new UserUsernameConflictException();
        }

        if (isEmailDuplicated(user)) {
            throw new UserEmailConflictException();
        }
    }

    private boolean isUsernameDuplicated(User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        return existingUser.isPresent() && !existingUser.get().getId().equals(user.getId());
    }

    private boolean isEmailDuplicated(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        return existingUser.isPresent() && !existingUser.get().getId().equals(user.getId());
    }

}
