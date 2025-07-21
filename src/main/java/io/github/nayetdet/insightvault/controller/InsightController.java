package io.github.nayetdet.insightvault.controller;

import io.github.nayetdet.insightvault.controller.docs.InsightControllerDocs;
import io.github.nayetdet.insightvault.payload.dto.InsightDTO;
import io.github.nayetdet.insightvault.payload.dto.simplified.SimplifiedInsightDTO;
import io.github.nayetdet.insightvault.payload.query.InsightQuery;
import io.github.nayetdet.insightvault.payload.query.page.ApplicationPage;
import io.github.nayetdet.insightvault.payload.request.InsightRequest;
import io.github.nayetdet.insightvault.security.annotation.PreAuthorizeUser;
import io.github.nayetdet.insightvault.service.InsightService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/insights")
@Tag(name = "Insight", description = "Endpoints for managing insights")
public class InsightController implements InsightControllerDocs {

    private final InsightService insightService;

    @Override
    @GetMapping
    public ResponseEntity<ApplicationPage<SimplifiedInsightDTO>> search(@ParameterObject @Valid InsightQuery query) {
        ApplicationPage<SimplifiedInsightDTO> responses = insightService.search(query);
        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<InsightDTO> find(@PathVariable Long id) {
        Optional<InsightDTO> insight = insightService.find(id);
        return insight.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @PreAuthorizeUser
    @PostMapping
    public ResponseEntity<InsightDTO> create(@RequestBody @Valid InsightRequest request) {
        InsightDTO response = insightService.create(request);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @Override
    @PreAuthorizeUser
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        insightService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
