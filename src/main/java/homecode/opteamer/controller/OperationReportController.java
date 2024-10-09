package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.ErrorResponseDTO;
import homecode.opteamer.model.dtos.OperationReportDTO;
import homecode.opteamer.service.OperationReportService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "CRUD REST APIs for Operation Reports",
        description = "CRUD REST APIs for managing operation reports in the Opteamer Application"
)
@RestController
@RequestMapping("/api/operationReport")
public class OperationReportController {

    private final OperationReportService operationReportService;

    public OperationReportController(OperationReportService operationReportService) {
        this.operationReportService = operationReportService;
    }

    @Operation(
            summary = "Get Operation Report by Team Member ID and Operation ID",
            description = "REST API to fetch a specific operation report by team member ID and operation ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operation Report found and retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Operation Report not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{teamMemberId}/{operationId}")
    public ResponseEntity<OperationReportDTO> getOperationReportById(@PathVariable Long teamMemberId,
                                                                     @PathVariable Long operationId) {
        Optional<OperationReportDTO> operationReportDTOOptional = operationReportService.findById(teamMemberId, operationId);
        return operationReportDTOOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "Get All Operation Reports",
            description = "REST API to fetch all operation reports"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all operation reports"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<OperationReportDTO>> getAllOperationReports() {
        List<OperationReportDTO> operationReportDTOList = operationReportService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(operationReportDTOList);
    }

    @Operation(
            summary = "Create a New Operation Report",
            description = "REST API to create a new operation report"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Operation Report created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<OperationReportDTO> createOperationReport(@Valid @RequestBody OperationReportDTO operationReportDTO) {
        OperationReportDTO operationReport = operationReportService.createOperationReport(operationReportDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(operationReport);
    }

    @Operation(
            summary = "Update an Existing Operation Report",
            description = "REST API to update an operation report by team member ID and operation ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operation Report updated successfully"),
            @ApiResponse(responseCode = "404", description = "Operation Report not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{teamMemberId}/{operationId}")
    public ResponseEntity<OperationReportDTO> updateOperationReport(@PathVariable Long teamMemberId,
                                                                    @PathVariable Long operationId,
                                                                    @Valid @RequestBody OperationReportDTO operationReportDTO) {
        Optional<OperationReportDTO> operationReportDTOOptional = operationReportService.updateOperationReport(teamMemberId, operationId, operationReportDTO);
        return operationReportDTOOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "Delete an Operation Report",
            description = "REST API to delete an operation report by team member ID and operation ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Operation Report deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Operation Report not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{teamMemberId}/{operationId}")
    public ResponseEntity<Void> deleteOperationReport(@PathVariable Long teamMemberId,
                                                      @PathVariable Long operationId) {
        boolean isDeleted = operationReportService.deleteOperationReport(teamMemberId, operationId);
        return isDeleted
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
