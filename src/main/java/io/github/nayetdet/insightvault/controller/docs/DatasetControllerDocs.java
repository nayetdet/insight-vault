package io.github.nayetdet.insightvault.controller.docs;

import io.github.nayetdet.insightvault.payload.dto.DatasetDTO;
import io.github.nayetdet.insightvault.payload.query.DatasetQuery;
import io.github.nayetdet.insightvault.payload.query.page.ApplicationPage;
import io.github.nayetdet.insightvault.payload.request.DatasetRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface DatasetControllerDocs {

    @Operation(
            summary = "Search all datasets",
            tags = {"Dataset"},
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
    ResponseEntity<ApplicationPage<DatasetDTO>> search(@ParameterObject @Valid DatasetQuery query);

    @Operation(
            summary = "Find a dataset by ID",
            tags = {"Dataset"},
            responses = {
                    @ApiResponse(
                            description = "Ok",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DatasetDTO.class)
                            )
                    ),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<DatasetDTO> find(@PathVariable Long id);

    @Operation(
            summary = "Create a dataset",
            tags = {"Dataset"},
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @RequestBody(
                    content = @Content(
                            encoding = @Encoding(
                                    name = "request",
                                    contentType = "application/json"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DatasetDTO.class)
                            )
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
                    @ApiResponse(description = "Bad Gateway", responseCode = "502", content = @Content)
            }
    )
    ResponseEntity<DatasetDTO> create(@RequestPart @Valid DatasetRequest request, @RequestParam MultipartFile file);

    @Operation(
            summary = "Delete a dataset by ID",
            tags = {"Dataset"},
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
