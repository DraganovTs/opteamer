package homecode.opteamer.controller;


import homecode.opteamer.model.dtos.PreOperativeAssessmentDTO;
import homecode.opteamer.service.PreOperativeAssessmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/preOperativeAssessments")
public class PreOperativeAssessmentController {

    private final PreOperativeAssessmentService preOperativeAssessmentService;

    public PreOperativeAssessmentController(PreOperativeAssessmentService preOperativeAssessmentService) {
        this.preOperativeAssessmentService = preOperativeAssessmentService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<PreOperativeAssessmentDTO> getPreOperativeAssessmentById(@PathVariable String name) {
        PreOperativeAssessmentDTO operativeAssessmentDTO = preOperativeAssessmentService.getPreOperativeAssessment(name);
        return ResponseEntity.ok(operativeAssessmentDTO);
    }

    @GetMapping
    public ResponseEntity<List<PreOperativeAssessmentDTO>> getPreOperativeAssessments() {
        List<PreOperativeAssessmentDTO> list = preOperativeAssessmentService.getAllPreOperativeAssessments();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<PreOperativeAssessmentDTO> createPreOperativeAssessment(@Valid @RequestBody PreOperativeAssessmentDTO preOperativeAssessmentDTO) {
        PreOperativeAssessmentDTO preOperativeAssessment = preOperativeAssessmentService.createPreOperativeAssessment(preOperativeAssessmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(preOperativeAssessment);
    }

    @PutMapping("/{name}")
    public ResponseEntity<PreOperativeAssessmentDTO> updatePreOperativeAssessment(@PathVariable String name,
                                                                                  @Valid @RequestBody PreOperativeAssessmentDTO preOperativeAssessmentDTO) {
        PreOperativeAssessmentDTO updatedAssessmentDTO = preOperativeAssessmentService.updatePreOperativeAssessment(name, preOperativeAssessmentDTO);
        return ResponseEntity.ok(updatedAssessmentDTO);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deletePreOperativeAssessment(@PathVariable String name) {
        preOperativeAssessmentService.deletePreOperativeAssessment(name);
        return ResponseEntity.noContent().build();
    }
}
