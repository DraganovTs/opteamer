package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.OperationProviderDTO;
import homecode.opteamer.service.OperationProviderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operationProviders")
public class OperationProviderController {

    private final OperationProviderService operationProviderService;

    public OperationProviderController(OperationProviderService operationProviderService) {
        this.operationProviderService = operationProviderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationProviderDTO> getOperationProvider(@PathVariable String id) {
        return operationProviderService.getOperationProviderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<OperationProviderDTO>> getAllOperationProviders() {
        List<OperationProviderDTO> operationProviders = operationProviderService.getAllOperationProviders();
        return ResponseEntity.ok(operationProviders);
    }

    @PostMapping
    public ResponseEntity<OperationProviderDTO> createOperationProvider(@Valid @RequestBody OperationProviderDTO operationProviderDTO) {
        OperationProviderDTO createdOperationProvider = operationProviderService.createOperationProvider(operationProviderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOperationProvider);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperationProviderDTO> updateOperationProvider(@PathVariable String id,
                                                                        @Valid @RequestBody OperationProviderDTO operationProviderDTO) {
        return operationProviderService.updateOperationProvider(id, operationProviderDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperationProvider(@PathVariable String id) {
        return operationProviderService.deleteOperationProvider(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
