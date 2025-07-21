package io.github.nayetdet.insightvault.utils.json;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;
import io.github.nayetdet.insightvault.exception.RecordDocumentEncryptedException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.Tuple;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParser {

    private JsonParser() {
    }

    public static Tuple<String, List<Map<String, Object>>> fromCsv(MultipartFile csv) throws IOException, CsvValidationException {
        StringBuilder dataText = new StringBuilder();
        List<Map<String, Object>> data = new ArrayList<>();

        try (CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new InputStreamReader(csv.getInputStream()))) {
            Map<String, String> row;
            while ((row = reader.readMap()) != null) {
                data.add(Map.copyOf(row));
                row.values().forEach(value -> {
                    if (value != null) {
                        dataText.append(value).append(" ");
                    }
                });
            }
        }

        return new Tuple<>(dataText.toString().trim(), data);
    }

    public static Tuple<String, List<Map<String, Object>>> fromPdf(MultipartFile pdf) throws IOException {
        StringBuilder dataText = new StringBuilder();
        List<Map<String, Object>> data = new ArrayList<>();

        try (PDDocument document = Loader.loadPDF(pdf.getInputStream().readAllBytes())) {
            if (document.isEncrypted()) {
                throw new RecordDocumentEncryptedException();
            }

            PDFTextStripper stripper = new PDFTextStripper();
            for (int i = 1; i <= document.getNumberOfPages(); i++) {
                stripper.setStartPage(i);
                stripper.setEndPage(i);
                String text = stripper.getText(document);

                Map<String, Object> page = new HashMap<>();
                page.put("page", i);
                page.put("text", text.trim());
                data.add(page);
                dataText.append(text.trim()).append(" ");
            }
        }

        return new Tuple<>(dataText.toString().trim(), data);
    }

}
