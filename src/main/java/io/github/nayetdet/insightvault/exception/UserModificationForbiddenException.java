package io.github.nayetdet.insightvault.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserModificationForbiddenException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "You are not allowed to modify this user";

    public UserModificationForbiddenException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
