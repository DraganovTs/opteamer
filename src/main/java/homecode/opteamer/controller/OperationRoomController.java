package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.OperationRoomDTO;
import homecode.opteamer.service.OperationRoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/operationRooms")
public class OperationRoomController {

    private final OperationRoomService operationRoomService;

    public OperationRoomController(OperationRoomService operationRoomService) {
        this.operationRoomService = operationRoomService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationRoomDTO> getOperationRoomById(@PathVariable Long id) {
        Optional<OperationRoomDTO> operationRoomDTOOptional = operationRoomService.getOperationRoomById(id);
        return operationRoomDTOOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND).build());
    }


    @GetMapping
    public ResponseEntity<List<OperationRoomDTO>> getOperationRooms() {
        List<OperationRoomDTO> operationRoomDTOList = operationRoomService.getAllOperationRooms();
        return ResponseEntity.status(HttpStatus.OK).body(operationRoomDTOList);
    }

    @PostMapping
    public ResponseEntity<OperationRoomDTO> createOperationRoom(@Valid @RequestBody OperationRoomDTO operationRoomDTO) {
        OperationRoomDTO operationRoomDTOCreated = operationRoomService.createOperationRoom(operationRoomDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(operationRoomDTOCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperationRoomDTO> updateOperationRoom(@PathVariable Long id,
                                                               @Valid @RequestBody OperationRoomDTO operationRoomDTO) {
        Optional<OperationRoomDTO> operationRoomDTOOptional = operationRoomService.updateOperationRoom(id,operationRoomDTO);
        return operationRoomDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OperationRoomDTO> deleteOperationRoom(@PathVariable Long id) {
        boolean isDeleted = operationRoomService.deleteOperationRoomById(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
