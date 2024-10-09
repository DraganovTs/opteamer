package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.OperationTypeDTO;
import homecode.opteamer.service.OperationTypeService;
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
        name = "CRUD REST APIs for Operation Types",
        description = "CRUD REST APIs for managing operation types in the Opteamer Application"
)
@RestController
@RequestMapping("/api/operationTypes")
public class OperationTypeController {

    private final OperationTypeService operationTypeService;

    public OperationTypeController(OperationTypeService operationTypeService) {
        this.operationTypeService = operationTypeService;
    }

    @Operation(
            summary = "Get Operation Type by Name",
            description = "REST API to fetch a specific operation type by its name"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operation Type found and retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Operation Type not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{name}")
    public ResponseEntity<OperationTypeDTO> findByName(@PathVariable String name) {
        OperationTypeDTO operationTypeDTO = operationTypeService.getOperationTypeByName(name);
        return ResponseEntity.ok(operationTypeDTO);
    }

    @Operation(
            summary = "Get All Operation Types",
            description = "REST API to fetch all operation types"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all operation types"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<OperationTypeDTO>> findAll() {
        List<OperationTypeDTO> operationTypeDTOS = operationTypeService.getAllOperationTypes();
        return ResponseEntity.ok(operationTypeDTOS);
    }

    @Operation(
            summary = "Create a New Operation Type",
            description = "REST API to create a new operation type"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Operation Type created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<OperationTypeDTO> create(@Valid @RequestBody OperationTypeDTO operationTypeDTO) {
        OperationTypeDTO createdOperationType = operationTypeService.createOperationType(operationTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOperationType);
    }

    @Operation(
            summary = "Update an Existing Operation Type",
            description = "REST API to update an operation type by its name"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operation Type updated successfully"),
            @ApiResponse(responseCode = "404", description = "Operation Type not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{name}")
    public ResponseEntity<OperationTypeDTO> update(@PathVariable String name,
                                                   @Valid @RequestBody OperationTypeDTO operationTypeDTO) {
        OperationTypeDTO updatedOperationType = operationTypeService.updateOperationType(name, operationTypeDTO);
        return ResponseEntity.ok(updatedOperationType);
    }

    @Operation(
            summary = "Delete an Operation Type",
            description = "REST API to delete an operation type by its name"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Operation Type deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Operation Type not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        operationTypeService.deleteOperationType(name);
        return ResponseEntity.noContent().build();
    }
}
