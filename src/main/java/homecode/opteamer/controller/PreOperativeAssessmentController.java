package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.ErrorResponseDTO;
import homecode.opteamer.model.dtos.PreOperativeAssessmentDTO;
import homecode.opteamer.service.PreOperativeAssessmentService;
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
        name = "CRUD REST APIs for Pre-Operative Assessments",
        description = "CRUD REST APIs for managing pre-operative assessments in the Opteamer Application"
)
@RestController
@RequestMapping("/api/preOperativeAssessments")
public class PreOperativeAssessmentController {

    private final PreOperativeAssessmentService preOperativeAssessmentService;

    public PreOperativeAssessmentController(PreOperativeAssessmentService preOperativeAssessmentService) {
        this.preOperativeAssessmentService = preOperativeAssessmentService;
    }

    @Operation(
            summary = "Get Pre-Operative Assessment by Name",
            description = "REST API to fetch a specific pre-operative assessment by its name"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pre-operative assessment found and retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Pre-operative assessment not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{name}")
    public ResponseEntity<PreOperativeAssessmentDTO> getPreOperativeAssessmentById(@PathVariable String name) {
        PreOperativeAssessmentDTO operativeAssessmentDTO = preOperativeAssessmentService.getPreOperativeAssessment(name);
        return ResponseEntity.ok(operativeAssessmentDTO);
    }

    @Operation(
            summary = "Get All Pre-Operative Assessments",
            description = "REST API to fetch all pre-operative assessments"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all pre-operative assessments"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<PreOperativeAssessmentDTO>> getPreOperativeAssessments() {
        List<PreOperativeAssessmentDTO> list = preOperativeAssessmentService.getAllPreOperativeAssessments();
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Create a New Pre-Operative Assessment",
            description = "REST API to create a new pre-operative assessment"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pre-operative assessment created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<PreOperativeAssessmentDTO> createPreOperativeAssessment(@Valid @RequestBody PreOperativeAssessmentDTO preOperativeAssessmentDTO) {
        PreOperativeAssessmentDTO preOperativeAssessment = preOperativeAssessmentService.createPreOperativeAssessment(preOperativeAssessmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(preOperativeAssessment);
    }

    @Operation(
            summary = "Update an Existing Pre-Operative Assessment",
            description = "REST API to update a pre-operative assessment by its name"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pre-operative assessment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Pre-operative assessment not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{name}")
    public ResponseEntity<PreOperativeAssessmentDTO> updatePreOperativeAssessment(@PathVariable String name,
                                                                                  @Valid @RequestBody PreOperativeAssessmentDTO preOperativeAssessmentDTO) {
        PreOperativeAssessmentDTO updatedAssessmentDTO = preOperativeAssessmentService.updatePreOperativeAssessment(name, preOperativeAssessmentDTO);
        return ResponseEntity.ok(updatedAssessmentDTO);
    }

    @Operation(
            summary = "Delete a Pre-Operative Assessment",
            description = "REST API to delete a pre-operative assessment by its name"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pre-operative assessment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Pre-operative assessment not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deletePreOperativeAssessment(@PathVariable String name) {
        preOperativeAssessmentService.deletePreOperativeAssessment(name);
        return ResponseEntity.noContent().build();
    }
}
