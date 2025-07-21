package io.github.nayetdet.insightvault.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class DatasetUploadException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "Failed to upload a dataset from the server";

    public DatasetUploadException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
