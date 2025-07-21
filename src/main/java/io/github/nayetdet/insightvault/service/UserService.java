package io.github.nayetdet.insightvault.service;

import io.github.nayetdet.insightvault.exception.UserModificationForbiddenException;
import io.github.nayetdet.insightvault.exception.UserNotFoundException;
import io.github.nayetdet.insightvault.mapper.UserMapper;
import io.github.nayetdet.insightvault.model.User;
import io.github.nayetdet.insightvault.payload.dto.UserDTO;
import io.github.nayetdet.insightvault.payload.query.UserQuery;
import io.github.nayetdet.insightvault.payload.query.page.ApplicationPage;
import io.github.nayetdet.insightvault.payload.request.UserPasswordChangeRequest;
import io.github.nayetdet.insightvault.payload.request.UserUpdateRequest;
import io.github.nayetdet.insightvault.repository.UserRepository;
import io.github.nayetdet.insightvault.security.provider.UserContextProvider;
import io.github.nayetdet.insightvault.service.validator.UserValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidator userValidator;

    @Transactional(readOnly = true)
    public ApplicationPage<UserDTO> search(UserQuery query) {
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize(), query.getSort());
        Page<User> page = userRepository.findAll(query.getSpecification(), pageable);
        return new ApplicationPage<>(page.map(userMapper::toDTO));
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> find(String username) {
        return userRepository.findByUsername(username).map(userMapper::toDTO);
    }

    @Transactional
    public UserDTO update(String username, UserUpdateRequest request) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        UserContextProvider.assertAuthorizedContext(user.getUsername(), UserModificationForbiddenException.class);
        entityManager.detach(user);
        userMapper.update(user, request);
        userValidator.validate(user);
        return userMapper.toDTO(userRepository.save(entityManager.merge(user)));
    }

    @Transactional
    public void changePassword(String username, UserPasswordChangeRequest request) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        UserContextProvider.assertAuthorizedContext(user.getUsername(), UserModificationForbiddenException.class);
        userRepository.resetPassword(user.getId(), passwordEncoder.encode(request.getPassword()));
    }

    @Transactional
    public void delete(String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        UserContextProvider.assertAuthorizedContext(user.getUsername(), UserModificationForbiddenException.class);
        userRepository.delete(user);
    }

}
