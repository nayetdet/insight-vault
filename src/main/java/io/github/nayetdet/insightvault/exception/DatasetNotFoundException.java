package io.github.nayetdet.insightvault.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DatasetNotFoundException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "Dataset not found";

    public DatasetNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
