package homecode.opteamer.controller;

import homecode.opteamer.exception.InvalidRequestException;
import homecode.opteamer.model.dtos.AssessmentDTO;
import homecode.opteamer.service.AssessmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping("/{teamMemberId}/{patientId}/{preOpAName}")
    public ResponseEntity<AssessmentDTO> getAssessment(@PathVariable Long teamMemberId,
                                                       @PathVariable Long patientId,
                                                       @PathVariable String preOpAName) {
        return assessmentService.getAssessmentById(teamMemberId, preOpAName, patientId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AssessmentDTO>> getAllAssessments() {
        List<AssessmentDTO> assessments = assessmentService.getAllAssessments();
        return ResponseEntity.ok(assessments);
    }

    @PostMapping
    public ResponseEntity<AssessmentDTO> createAssessment(@Valid @RequestBody AssessmentDTO assessmentDTO) {
        if (assessmentDTO.getStartDate() == null) {
            throw new InvalidRequestException("Start date cannot be null");
        }

        AssessmentDTO createdAssessment = assessmentService.createAssessment(assessmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssessment);
    }

    @PutMapping("/{teamMemberId}/{patientId}/{preOpAName}")
    public ResponseEntity<AssessmentDTO> updateAssessment(@PathVariable Long teamMemberId,
                                                          @PathVariable Long patientId,
                                                          @PathVariable String preOpAName,
                                                          @RequestBody AssessmentDTO assessmentDTO) {
        return assessmentService.updateAssessment(teamMemberId, patientId, preOpAName, assessmentDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{teamMemberId}/{patientId}/{preOpAName}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable Long teamMemberId,
                                                 @PathVariable Long patientId,
                                                 @PathVariable String preOpAName) {
        boolean deleted = assessmentService.deleteAssessment(teamMemberId, patientId, preOpAName);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}

