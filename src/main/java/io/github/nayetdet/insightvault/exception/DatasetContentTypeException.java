package io.github.nayetdet.insightvault.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DatasetContentTypeException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "Invalid dataset content type";

    public DatasetContentTypeException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
