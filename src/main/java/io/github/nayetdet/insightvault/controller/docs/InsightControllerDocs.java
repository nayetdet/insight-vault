package io.github.nayetdet.insightvault.controller.docs;

import io.github.nayetdet.insightvault.payload.dto.InsightDTO;
import io.github.nayetdet.insightvault.payload.dto.simplified.SimplifiedInsightDTO;
import io.github.nayetdet.insightvault.payload.query.InsightQuery;
import io.github.nayetdet.insightvault.payload.query.page.ApplicationPage;
import io.github.nayetdet.insightvault.payload.request.InsightRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface InsightControllerDocs {

    @Operation(
            summary = "Search all insights",
            tags = {"Insight"},
            responses = {
                    @ApiResponse(
                            description = "Ok",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApplicationPage.class)
                            )
                    ),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<ApplicationPage<SimplifiedInsightDTO>> search(@ParameterObject @Valid InsightQuery query);

    @Operation(
            summary = "Find a insight by ID",
            tags = {"Insight"},
            responses = {
                    @ApiResponse(
                            description = "Ok",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = InsightDTO.class)
                            )
                    ),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<InsightDTO> find(@PathVariable Long id);

    @Operation(
            summary = "Create a insight",
            tags = {"Insight"},
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = InsightDTO.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<InsightDTO> create(@RequestBody @Valid InsightRequest request);

    @Operation(
            summary = "Delete a insight by ID",
            tags = {"Insight"},
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<Void> delete(@PathVariable Long id);

}
