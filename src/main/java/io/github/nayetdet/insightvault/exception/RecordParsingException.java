package io.github.nayetdet.insightvault.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class RecordParsingException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "Failed to parse the dataset record to JSON";

    public RecordParsingException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
