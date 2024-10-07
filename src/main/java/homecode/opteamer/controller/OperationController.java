package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.OperationDTO;
import homecode.opteamer.service.OperationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/operations")
@Validated
public class OperationController {

    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationDTO> getOperation(@PathVariable Long id) {
        return operationService.getOperationById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<OperationDTO>> getAllOperations() {
        List<OperationDTO> operationDTOS = operationService.getAllOperations();
        return ResponseEntity.ok(operationDTOS);
    }

    @PostMapping
    public ResponseEntity<OperationDTO> createOperation(@Valid @RequestBody OperationDTO operationDTO) {
        OperationDTO savedOperation = operationService.createOperation(operationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOperation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperationDTO> updateOperation(@PathVariable Long id, @Valid @RequestBody OperationDTO operationDTO) {
        return operationService.updateOperation(id, operationDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperation(@PathVariable Long id) {
        if (operationService.deleteOperationById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
