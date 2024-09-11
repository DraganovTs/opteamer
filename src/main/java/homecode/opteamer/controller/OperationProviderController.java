package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.OperationProviderDTO;
import homecode.opteamer.service.OperationProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/operationProviders")
public class OperationProviderController {

    private final OperationProviderService operationProviderService;

    public OperationProviderController(OperationProviderService operationProviderService) {
        this.operationProviderService = operationProviderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationProviderDTO> getOperationProvider(@PathVariable String id) {
        Optional<OperationProviderDTO> operationProviderDTOOptional =
                operationProviderService.getOperationProviderById(id);
        return operationProviderDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<OperationProviderDTO>> getOperationProviders() {
        List<OperationProviderDTO> list = operationProviderService.getAllOperationProviders();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping
    public ResponseEntity<OperationProviderDTO> createOperationProvider(@RequestBody OperationProviderDTO operationProviderDTO) {
        OperationProviderDTO createdOperationProviderDTO = operationProviderService.createOperationProvider(operationProviderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOperationProviderDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperationProviderDTO> updateOperationProvider(@PathVariable String id,
                                                                        @RequestBody OperationProviderDTO operationProviderDTO) {
        Optional<OperationProviderDTO> operationProviderDTOOptional =
                operationProviderService.updateOperationProvider(id, operationProviderDTO);
        return operationProviderDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OperationProviderDTO> deleteOperationProvider(@PathVariable String id) {
        boolean deleted = operationProviderService.deleteOperationProvider(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
