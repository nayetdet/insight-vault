package io.github.nayetdet.insightvault.utils.json;

import io.github.nayetdet.insightvault.exception.RecordParsingException;
import org.springframework.util.function.ThrowingFunction;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.Tuple;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public enum JsonParsingStrategy {

    CSV(List.of("text/csv", "application/csv"), wrap(JsonParser::fromCsv)),
    PDF(List.of("application/pdf"), wrap(JsonParser::fromPdf));

    private final List<String> contentTypes;
    private final Function<MultipartFile, Tuple<String, List<Map<String, Object>>>> strategy;

    JsonParsingStrategy(List<String> contentTypes, Function<MultipartFile, Tuple<String, List<Map<String, Object>>>> strategy) {
        this.contentTypes = contentTypes;
        this.strategy = strategy;
    }

    public Tuple<String, List<Map<String, Object>>> apply(MultipartFile file) {
        return strategy.apply(file);
    }

    public static Tuple<String, List<Map<String, Object>>> parse(MultipartFile file) {
        return Arrays.stream(values())
                .filter(strategy -> strategy.contentTypes.contains(file.getContentType()))
                .findFirst()
                .map(strategy -> strategy.apply(file))
                .orElseThrow(RecordParsingException::new);
    }

    private static <T, R> Function<T, R> wrap(ThrowingFunction<T, R> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                throw new RecordParsingException();
            }
        };
    }

}
