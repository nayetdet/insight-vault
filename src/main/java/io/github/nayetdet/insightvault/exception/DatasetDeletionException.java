package io.github.nayetdet.insightvault.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class DatasetDeletionException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "Failed to delete a dataset from the server";

    public DatasetDeletionException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
