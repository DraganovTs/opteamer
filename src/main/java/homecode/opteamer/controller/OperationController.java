package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.ErrorResponseDTO;
import homecode.opteamer.model.dtos.OperationDTO;
import homecode.opteamer.service.OperationService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "CRUD REST APIs for Operations",
        description = "CRUD REST APIs for managing operations in the Opteamer Application"
)
@RestController
@RequestMapping("/api/operations")
@Validated
public class OperationController {

    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @Operation(
            summary = "Get Operation by ID",
            description = "REST API to get a specific operation by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operation found and retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Operation not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<OperationDTO> getOperation(@PathVariable Long id) {
        return operationService.getOperationById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "Get All Operations",
            description = "REST API to fetch all operations"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all operations"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<OperationDTO>> getAllOperations() {
        List<OperationDTO> operationDTOS = operationService.getAllOperations();
        return ResponseEntity.ok(operationDTOS);
    }

    @Operation(
            summary = "Create a New Operation",
            description = "REST API to create a new operation"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Operation created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<OperationDTO> createOperation(@Valid @RequestBody OperationDTO operationDTO) {
        OperationDTO savedOperation = operationService.createOperation(operationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOperation);
    }

    @Operation(
            summary = "Update an Existing Operation",
            description = "REST API to update an operation by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operation updated successfully"),
            @ApiResponse(responseCode = "404", description = "Operation not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<OperationDTO> updateOperation(@PathVariable Long id, @Valid @RequestBody OperationDTO operationDTO) {
        return operationService.updateOperation(id, operationDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "Delete an Operation",
            description = "REST API to delete an operation by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Operation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Operation not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperation(@PathVariable Long id) {
        if (operationService.deleteOperationById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
