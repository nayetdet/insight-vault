package io.github.nayetdet.insightvault.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserEmailConflictException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "A user with the same email already exists";

    public UserEmailConflictException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
