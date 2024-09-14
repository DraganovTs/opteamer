package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.OperationTypeDTO;
import homecode.opteamer.service.OperationTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/operationTypes")
public class OperationTypeController {

    private final OperationTypeService operationTypeService;

    public OperationTypeController(OperationTypeService operationTypeService) {
        this.operationTypeService = operationTypeService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<OperationTypeDTO> findByName(@PathVariable String name) {
        Optional<OperationTypeDTO> operationTypeDTOOptional = operationTypeService.getOperationTypeById(name);
        return operationTypeDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<OperationTypeDTO>> findAll() {
        List<OperationTypeDTO> operationTypeDTOS = operationTypeService.getAllOperationTypes();
        return ResponseEntity.ok(operationTypeDTOS);
    }

    @PostMapping
    public ResponseEntity<OperationTypeDTO> create(@RequestBody OperationTypeDTO operationTypeDTO) {
        OperationTypeDTO operationType = operationTypeService.createOperationType(operationTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(operationType);
    }

    @PutMapping("/{name}")
    public ResponseEntity<OperationTypeDTO> update(@PathVariable String name,
                                                   @RequestBody OperationTypeDTO operationTypeDTO) {
        Optional<OperationTypeDTO> operationTypeDTOOptional = operationTypeService.updateOperationType(name, operationTypeDTO);
        return operationTypeDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @DeleteMapping("/{name}")
    public ResponseEntity<OperationTypeDTO> delete(@PathVariable String name) {
        boolean isDeleted = operationTypeService.deleteOperationType(name);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
