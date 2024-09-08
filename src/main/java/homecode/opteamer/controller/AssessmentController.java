package homecode.opteamer.controller;

import homecode.opteamer.model.Assessment;
import homecode.opteamer.model.dtos.AssessmentDTO;
import homecode.opteamer.service.AssessmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        Optional<AssessmentDTO> assessmentDTOOptional = assessmentService.getAssessmentById(teamMemberId, preOpAName, patientId);
        return assessmentDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AssessmentDTO>> getAllAssessments() {
        List<AssessmentDTO> assessmentDTOList = assessmentService.getAllAssessments();
        return ResponseEntity.status(HttpStatus.CREATED).body(assessmentDTOList);
    }

    @PostMapping()
    public ResponseEntity<AssessmentDTO> createAssessment(@RequestBody AssessmentDTO assessmentDTO) {
        AssessmentDTO assessmentDTOCreated = assessmentService.createAssessment(assessmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(assessmentDTOCreated);
    }

    @PutMapping("/{teamMemberId}/{patientId}/{preOpAName}")
    public ResponseEntity<AssessmentDTO> updateAssessment(@PathVariable Long teamMemberId,
                                                          @PathVariable Long patientId,
                                                          @PathVariable String preOpAName,
                                                          @RequestBody AssessmentDTO assessmentDTO) {
        Optional<AssessmentDTO> assessmentDTOOptional = assessmentService.updateAssessment(teamMemberId,
                patientId,
                preOpAName,
                assessmentDTO);
        return assessmentDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{teamMemberId}/{patientId}/{preOpAName}")
    public ResponseEntity<AssessmentDTO> deleteAssessment(@PathVariable Long teamMemberId,
                                                          @PathVariable Long patientId,
                                                          @PathVariable String preOpAName) {
        boolean deleted = assessmentService.deleteAssessment(teamMemberId, patientId, preOpAName);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
