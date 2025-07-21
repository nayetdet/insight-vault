package io.github.nayetdet.insightvault.service.validator;

import io.github.nayetdet.insightvault.exception.DatasetContentTypeException;
import io.github.nayetdet.insightvault.model.Dataset;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatasetValidator {

    private static final List<String> ALLOWED_CONTENT_TYPES = List.of(
            "text/csv",
            "application/csv",
            "application/pdf"
    );

    public void validate(Dataset dataset) {
        if (isContentTypeInvalid(dataset)) {
            throw new DatasetContentTypeException();
        }
    }

    private boolean isContentTypeInvalid(Dataset dataset) {
        return !ALLOWED_CONTENT_TYPES.contains(dataset.getContentType());
    }

}
