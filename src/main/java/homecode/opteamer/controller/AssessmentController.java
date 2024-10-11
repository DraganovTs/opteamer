package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.AssessmentDTO;
import homecode.opteamer.model.dtos.ErrorResponseDTO;
import homecode.opteamer.service.AssessmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Assessments",
        description = "CRUD REST APIs in Opteamer Application to CREATE, UPDATE, FETCH, AND DELETE Assessments"
)
@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @Operation(
            summary = "Fetch Assessment by ID",
            description = "Fetch an assessment by team member ID, patient ID, and pre-operative assessment name."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assessment fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Assessment not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{teamMemberId}/{preOpAName}/{patientId}")
    public ResponseEntity<AssessmentDTO> getAssessment(@PathVariable Long teamMemberId,
                                                       @PathVariable Long patientId,
                                                       @PathVariable String preOpAName) {
        return assessmentService.getAssessmentById(teamMemberId, preOpAName, patientId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Fetch All Assessments",
            description = "Fetch all assessments available in the Opteamer application."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assessments fetched successfully")
    })
    @GetMapping
    public ResponseEntity<List<AssessmentDTO>> getAllAssessments() {
        List<AssessmentDTO> assessments = assessmentService.getAllAssessments();
        return ResponseEntity.ok(assessments);
    }

    @Operation(
            summary = "Create a New Assessment",
            description = "Create a new assessment with the provided data in the Opteamer application."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Assessment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<AssessmentDTO> createAssessment(@Valid @RequestBody AssessmentDTO assessmentDTO) {
        AssessmentDTO createdAssessment = assessmentService.createAssessment(assessmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssessment);
    }

    @Operation(
            summary = "Update an Assessment",
            description = "Update an existing assessment by team member ID, patient ID, and pre-operative assessment name."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assessment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Assessment not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{teamMemberId}/{patientId}/{preOpAName}")
    public ResponseEntity<AssessmentDTO> updateAssessment(@PathVariable Long teamMemberId,
                                                          @PathVariable Long patientId,
                                                          @PathVariable String preOpAName,
                                                          @RequestBody AssessmentDTO assessmentDTO) {
        return assessmentService.updateAssessment(teamMemberId, patientId, preOpAName, assessmentDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete an Assessment",
            description = "Delete an assessment by team member ID, patient ID, and pre-operative assessment name."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assessment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Assessment not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{teamMemberId}/{patientId}/{preOpAName}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable Long teamMemberId,
                                                 @PathVariable Long patientId,
                                                 @PathVariable String preOpAName) {
        boolean deleted = assessmentService.deleteAssessment(teamMemberId, patientId, preOpAName);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
