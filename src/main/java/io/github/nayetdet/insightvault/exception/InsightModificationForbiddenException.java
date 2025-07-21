package io.github.nayetdet.insightvault.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InsightModificationForbiddenException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "You are not allowed to modify this insight";

    public InsightModificationForbiddenException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
