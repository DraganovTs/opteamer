package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.OperationRoomDTO;
import homecode.opteamer.service.OperationRoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "CRUD REST APIs for Operation Room",
        description = "CRUD REST APIs for managing operation rooms in the Opteamer Application"
)
@RestController
@RequestMapping("/api/operationRooms")
public class OperationRoomController {

    private final OperationRoomService operationRoomService;

    public OperationRoomController(OperationRoomService operationRoomService) {
        this.operationRoomService = operationRoomService;
    }

    @Operation(
            summary = "Get Operation Room by ID",
            description = "REST API to fetch a specific operation room by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operation Room found and retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Operation Room not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OperationRoomDTO> getOperationRoomById(@PathVariable Long id) {
        Optional<OperationRoomDTO> operationRoomDTOOptional = operationRoomService.getOperationRoomById(id);
        return operationRoomDTOOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "Get All Operation Rooms",
            description = "REST API to fetch all operation rooms"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all operation rooms"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<OperationRoomDTO>> getOperationRooms() {
        List<OperationRoomDTO> operationRoomDTOList = operationRoomService.getAllOperationRooms();
        return ResponseEntity.status(HttpStatus.OK).body(operationRoomDTOList);
    }

    @Operation(
            summary = "Create a New Operation Room",
            description = "REST API to create a new operation room"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Operation Room created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<OperationRoomDTO> createOperationRoom(@Valid @RequestBody OperationRoomDTO operationRoomDTO) {
        OperationRoomDTO operationRoomDTOCreated = operationRoomService.createOperationRoom(operationRoomDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(operationRoomDTOCreated);
    }

    @Operation(
            summary = "Update an Existing Operation Room",
            description = "REST API to update an operation room by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operation Room updated successfully"),
            @ApiResponse(responseCode = "404", description = "Operation Room not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<OperationRoomDTO> updateOperationRoom(@PathVariable Long id,
                                                                @Valid @RequestBody OperationRoomDTO operationRoomDTO) {
        Optional<OperationRoomDTO> operationRoomDTOOptional = operationRoomService.updateOperationRoom(id, operationRoomDTO);
        return operationRoomDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "Delete an Operation Room",
            description = "REST API to delete an operation room by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Operation Room deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Operation Room not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperationRoom(@PathVariable Long id) {
        boolean isDeleted = operationRoomService.deleteOperationRoomById(id);
        return isDeleted
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
