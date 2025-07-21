package io.github.nayetdet.insightvault.security.provider;

import io.github.nayetdet.insightvault.exception.BaseException;
import io.github.nayetdet.insightvault.exception.UserModificationForbiddenException;
import io.github.nayetdet.insightvault.exception.UserUnauthorizedException;
import io.github.nayetdet.insightvault.model.User;
import io.github.nayetdet.insightvault.model.enums.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.InvocationTargetException;

public class UserContextProvider {

    private UserContextProvider() {
    }

    public static void assertAuthorizedContext(String expectedUsername, Class<? extends BaseException> exception) {
        User user = getContext();
        if (!user.getUsername().equals(expectedUsername) && user.getRole() != UserRole.ADMIN) {
            try {
                throw exception.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                throw new UserModificationForbiddenException();
            }
        }
    }

    private static User getContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof User)) {
            throw new UserUnauthorizedException();
        }

        return (User) authentication.getPrincipal();
    }

}
