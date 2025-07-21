package io.github.nayetdet.insightvault.controller;

import io.github.nayetdet.insightvault.controller.docs.UserControllerDocs;
import io.github.nayetdet.insightvault.payload.dto.UserDTO;
import io.github.nayetdet.insightvault.payload.query.UserQuery;
import io.github.nayetdet.insightvault.payload.query.page.ApplicationPage;
import io.github.nayetdet.insightvault.payload.request.UserPasswordChangeRequest;
import io.github.nayetdet.insightvault.payload.request.UserUpdateRequest;
import io.github.nayetdet.insightvault.security.annotation.PreAuthorizeUser;
import io.github.nayetdet.insightvault.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
@Tag(name = "User", description = "Endpoints for managing users")
public class UserController implements UserControllerDocs {

    private final UserService userService;

    @Override
    @GetMapping
    public ResponseEntity<ApplicationPage<UserDTO>> search(@ParameterObject @Valid UserQuery query) {
        ApplicationPage<UserDTO> responses = userService.search(query);
        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> find(@PathVariable String username) {
        Optional<UserDTO> response = userService.find(username);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @PreAuthorizeUser
    @PutMapping("/{username}")
    public ResponseEntity<UserDTO> update(@PathVariable String username, @RequestBody @Valid UserUpdateRequest request) {
        UserDTO response = userService.update(username, request);
        return ResponseEntity.ok(response);
    }

    @Override
    @PreAuthorizeUser
    @PatchMapping("/{username}/password")
    public ResponseEntity<Void> changePassword(@PathVariable String username, @RequestBody @Valid UserPasswordChangeRequest request) {
        userService.changePassword(username, request);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PreAuthorizeUser
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> delete(@PathVariable String username) {
        userService.delete(username);
        return ResponseEntity.noContent().build();
    }

}
