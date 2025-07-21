package io.github.nayetdet.insightvault.controller.docs;

import io.github.nayetdet.insightvault.payload.dto.DatasetRecordDTO;
import io.github.nayetdet.insightvault.payload.dto.simplified.SimplifiedDatasetRecordDTO;
import io.github.nayetdet.insightvault.payload.query.DatasetRecordQuery;
import io.github.nayetdet.insightvault.payload.query.page.ApplicationPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface DatasetRecordControllerDocs {

    @Operation(
            summary = "Search all dataset records",
            tags = {"Dataset Record"},
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
    ResponseEntity<ApplicationPage<SimplifiedDatasetRecordDTO>> search(@ParameterObject @Valid DatasetRecordQuery query);

    @Operation(
            summary = "Find a dataset record by ID",
            tags = {"Dataset Record"},
            responses = {
                    @ApiResponse(
                            description = "Ok",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DatasetRecordDTO.class)
                            )
                    ),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<DatasetRecordDTO> find(@PathVariable String id);

}
