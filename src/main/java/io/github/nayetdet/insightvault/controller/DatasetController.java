package io.github.nayetdet.insightvault.controller;

import io.github.nayetdet.insightvault.controller.docs.DatasetControllerDocs;
import io.github.nayetdet.insightvault.payload.dto.DatasetDTO;
import io.github.nayetdet.insightvault.payload.query.DatasetQuery;
import io.github.nayetdet.insightvault.payload.query.page.ApplicationPage;
import io.github.nayetdet.insightvault.payload.request.DatasetRequest;
import io.github.nayetdet.insightvault.security.annotation.PreAuthorizeUser;
import io.github.nayetdet.insightvault.service.DatasetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/datasets")
@Tag(name = "Dataset", description = "Endpoints for managing datasets")
public class DatasetController implements DatasetControllerDocs {

    private final DatasetService datasetService;

    @Override
    @GetMapping
    public ResponseEntity<ApplicationPage<DatasetDTO>> search(@ParameterObject @Valid DatasetQuery query) {
        ApplicationPage<DatasetDTO> responses = datasetService.search(query);
        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<DatasetDTO> find(@PathVariable Long id) {
        Optional<DatasetDTO> response = datasetService.find(id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @PreAuthorizeUser
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DatasetDTO> create(@RequestPart @Valid DatasetRequest request, @RequestParam MultipartFile file) {
        DatasetDTO response = datasetService.create(request, file);
        URI datasetUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        URI datasetRecordUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/datasets/records/{id}")
                .buildAndExpand(response.getRecordId())
                .toUri();

        return ResponseEntity
                .created(datasetUri)
                .header("X-Record-Location", datasetRecordUri.toString())
                .body(response);
    }

    @Override
    @PreAuthorizeUser
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        datasetService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
