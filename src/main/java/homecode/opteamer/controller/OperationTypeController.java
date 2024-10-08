package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.OperationTypeDTO;
import homecode.opteamer.service.OperationTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/operationTypes")
public class OperationTypeController {

    private final OperationTypeService operationTypeService;

    public OperationTypeController(OperationTypeService operationTypeService) {
        this.operationTypeService = operationTypeService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<OperationTypeDTO> findByName(@PathVariable String name) {
        OperationTypeDTO operationTypeDTO = operationTypeService.getOperationTypeByName(name);
        return ResponseEntity.ok(operationTypeDTO);
    }

    @GetMapping
    public ResponseEntity<List<OperationTypeDTO>> findAll() {
        List<OperationTypeDTO> operationTypeDTOS = operationTypeService.getAllOperationTypes();
        return ResponseEntity.ok(operationTypeDTOS);
    }

    @PostMapping
    public ResponseEntity<OperationTypeDTO> create(@Valid @RequestBody OperationTypeDTO operationTypeDTO) {
        OperationTypeDTO createdOperationType = operationTypeService.createOperationType(operationTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOperationType);
    }

    @PutMapping("/{name}")
    public ResponseEntity<OperationTypeDTO> update(@PathVariable String name,
                                                   @Valid @RequestBody OperationTypeDTO operationTypeDTO) {
        OperationTypeDTO updatedOperationType = operationTypeService.updateOperationType(name, operationTypeDTO);
        return ResponseEntity.ok(updatedOperationType);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        operationTypeService.deleteOperationType(name);
        return ResponseEntity.noContent().build();
    }
}
