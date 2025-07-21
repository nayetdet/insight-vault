package io.github.nayetdet.insightvault.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InsightNotFoundException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "Insight not found";

    public InsightNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
