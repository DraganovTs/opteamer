package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.ErrorResponseDTO;
import homecode.opteamer.model.dtos.OperationProviderDTO;
import homecode.opteamer.service.OperationProviderService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "CRUD REST APIs for Operation Providers",
        description = "CRUD REST APIs for managing operation providers in the Opteamer Application"
)
@RestController
@RequestMapping("/api/operationProviders")
public class OperationProviderController {

    private final OperationProviderService operationProviderService;

    public OperationProviderController(OperationProviderService operationProviderService) {
        this.operationProviderService = operationProviderService;
    }

    @Operation(
            summary = "Get Operation Provider by ID",
            description = "REST API to get a specific operation provider by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operation Provider found and retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Operation Provider not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<OperationProviderDTO> getOperationProvider(@PathVariable String id) {
        return operationProviderService.getOperationProviderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "Get All Operation Providers",
            description = "REST API to fetch all operation providers"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all operation providers"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<OperationProviderDTO>> getAllOperationProviders() {
        List<OperationProviderDTO> operationProviders = operationProviderService.getAllOperationProviders();
        return ResponseEntity.ok(operationProviders);
    }

    @Operation(
            summary = "Create a New Operation Provider",
            description = "REST API to create a new operation provider"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Operation Provider created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<OperationProviderDTO> createOperationProvider(@Valid @RequestBody OperationProviderDTO operationProviderDTO) {
        OperationProviderDTO createdOperationProvider = operationProviderService.createOperationProvider(operationProviderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOperationProvider);
    }

    @Operation(
            summary = "Update an Existing Operation Provider",
            description = "REST API to update an operation provider by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operation Provider updated successfully"),
            @ApiResponse(responseCode = "404", description = "Operation Provider not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<OperationProviderDTO> updateOperationProvider(@PathVariable String id,
                                                                        @Valid @RequestBody OperationProviderDTO operationProviderDTO) {
        return operationProviderService.updateOperationProvider(id, operationProviderDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "Delete an Operation Provider",
            description = "REST API to delete an operation provider by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Operation Provider deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Operation Provider not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperationProvider(@PathVariable String id) {
        return operationProviderService.deleteOperationProvider(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
