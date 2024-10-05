package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.ErrorResponseDTO;
import homecode.opteamer.model.dtos.OperationDTO;
import homecode.opteamer.service.OperationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/operations")
public class OperationController {

    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationDTO> getOperation(@PathVariable Long id) {
        Optional<OperationDTO> optionalOperationDTO = operationService.getOperationById(id);
        return optionalOperationDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<OperationDTO>> getAllOperations() {
        List<OperationDTO> operationDTOS = operationService.getAllOperations();
        return ResponseEntity.ok(operationDTOS);
    }

    @PostMapping
    public ResponseEntity<?> createOperation(@RequestBody OperationDTO operationDTO) {
       try {
        OperationDTO operationDTOSaved = operationService.createOperation(operationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(operationDTOSaved);
       }catch (Exception e) {
            return ResponseEntity.ok(new ErrorResponseDTO(  "Incorrect username or password",false ));
       }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperationDTO> updateOperation(@PathVariable Long id, @RequestBody OperationDTO operationDTO) {
        Optional<OperationDTO> optionalOperationDTO = operationService.updateOperation(id,operationDTO);
        return optionalOperationDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OperationDTO> deleteOperation(@PathVariable Long id) {
        boolean deleted = operationService.deleteOperationById(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
