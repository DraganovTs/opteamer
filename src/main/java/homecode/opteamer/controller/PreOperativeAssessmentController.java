package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.PreOperativeAssessmentDTO;
import homecode.opteamer.service.PreOperativeAssessmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/preOperativeAssessments")
public class PreOperativeAssessmentController {

    private final PreOperativeAssessmentService preOperativeAssessmentService;

    public PreOperativeAssessmentController(PreOperativeAssessmentService preOperativeAssessmentService) {
        this.preOperativeAssessmentService = preOperativeAssessmentService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<PreOperativeAssessmentDTO> getPreOperativeAssessmentById(@PathVariable String name) {
        Optional<PreOperativeAssessmentDTO> operativeAssessmentDTOOptional =
                preOperativeAssessmentService.getPreOperativeAssessment(name);
        return operativeAssessmentDTOOptional.map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<PreOperativeAssessmentDTO>> getPreOperativeAssessments() {
        List<PreOperativeAssessmentDTO> list = preOperativeAssessmentService.getAllPreOperativeAssessments();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping
    public ResponseEntity<PreOperativeAssessmentDTO> createPreOperativeAssessment(@RequestBody PreOperativeAssessmentDTO
                                                                                          preOperativeAssessmentDTO) {
        PreOperativeAssessmentDTO preOperativeAssessment = preOperativeAssessmentService
                .createPreOperativeAssessment(preOperativeAssessmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(preOperativeAssessment);
    }

    @PutMapping("/{name}")
    public ResponseEntity<PreOperativeAssessmentDTO> updatePreOperativeAssessment(@PathVariable String name,
                                                                                  @RequestBody PreOperativeAssessmentDTO
                                                                                          preOperativeAssessmentDTO) {
        Optional<PreOperativeAssessmentDTO> operativeAssessmentDTOOptional =
                preOperativeAssessmentService.updatePreOperativeAssessment(name, preOperativeAssessmentDTO);
        return operativeAssessmentDTOOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @DeleteMapping("/{name}")
    public ResponseEntity<PreOperativeAssessmentDTO> deletePreOperativeAssessment(@PathVariable String name) {
        boolean deleted = preOperativeAssessmentService.deletePreOperativeAssessment(name);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
