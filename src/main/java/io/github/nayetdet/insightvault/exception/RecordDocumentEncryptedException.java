package io.github.nayetdet.insightvault.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecordDocumentEncryptedException extends BaseException {

    private static final String DEFAULT_ERROR_MESSAGE = "Failed to read the dataset record: the document is encrypted";

    public RecordDocumentEncryptedException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
