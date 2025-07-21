package io.github.nayetdet.insightvault.controller;

import io.github.nayetdet.insightvault.controller.docs.DatasetRecordControllerDocs;
import io.github.nayetdet.insightvault.payload.dto.DatasetRecordDTO;
import io.github.nayetdet.insightvault.payload.dto.simplified.SimplifiedDatasetRecordDTO;
import io.github.nayetdet.insightvault.payload.query.DatasetRecordQuery;
import io.github.nayetdet.insightvault.payload.query.page.ApplicationPage;
import io.github.nayetdet.insightvault.service.DatasetRecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/datasets/records")
@Tag(name = "Dataset Record", description = "Endpoints for managing dataset records")
public class DatasetRecordController implements DatasetRecordControllerDocs {

    private final DatasetRecordService datasetRecordService;

    @Override
    @GetMapping
    public ResponseEntity<ApplicationPage<SimplifiedDatasetRecordDTO>> search(@ParameterObject @Valid DatasetRecordQuery query) {
        ApplicationPage<SimplifiedDatasetRecordDTO> responses = datasetRecordService.search(query);
        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<DatasetRecordDTO> find(@PathVariable String id) {
        Optional<DatasetRecordDTO> response = datasetRecordService.find(id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
