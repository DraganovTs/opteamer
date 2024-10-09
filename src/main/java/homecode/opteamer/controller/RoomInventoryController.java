package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.ErrorResponseDTO;
import homecode.opteamer.model.dtos.RoomInventoryDTO;
import homecode.opteamer.service.RoomInventoryService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "CRUD REST APIs for Room Inventories",
        description = "CRUD REST APIs for managing room inventories in the Opteamer Application"
)
@RestController
@RequestMapping("/api/roomInventories")
public class RoomInventoryController {

    private final RoomInventoryService roomInventoryService;

    public RoomInventoryController(RoomInventoryService roomInventoryService) {
        this.roomInventoryService = roomInventoryService;
    }

    @Operation(
            summary = "Get Room Inventory by Asset and Room ID",
            description = "REST API to fetch a specific room inventory by asset ID and room ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room inventory found and retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Room inventory not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{assetId}/{roomId}")
    public ResponseEntity<RoomInventoryDTO> getRoomInventory(@PathVariable("assetId") Long assetId,
                                                             @PathVariable("roomId") Long roomId) {
        RoomInventoryDTO roomInventoryDTO = roomInventoryService.getRoomInventoryById(assetId, roomId);
        return ResponseEntity.ok(roomInventoryDTO);
    }

    @Operation(
            summary = "Get All Room Inventories",
            description = "REST API to fetch all room inventories"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all room inventories"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<RoomInventoryDTO>> getAllRoomInventories() {
        List<RoomInventoryDTO> roomInventoryDTOList = roomInventoryService.getAllRoomInventories();
        return ResponseEntity.ok(roomInventoryDTOList);
    }

    @Operation(
            summary = "Create a New Room Inventory",
            description = "REST API to create a new room inventory"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Room inventory created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<RoomInventoryDTO> createRoomInventory(@Valid @RequestBody RoomInventoryDTO roomInventoryDTO) {
        RoomInventoryDTO roomInventoryDTOCreated = roomInventoryService.createRoomInventory(roomInventoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomInventoryDTOCreated);
    }

    @Operation(
            summary = "Update an Existing Room Inventory",
            description = "REST API to update a room inventory by asset ID and room ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room inventory updated successfully"),
            @ApiResponse(responseCode = "404", description = "Room inventory not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{assetId}/{roomId}")
    public ResponseEntity<RoomInventoryDTO> updateRoomInventory(@PathVariable Long assetId,
                                                                @PathVariable Long roomId,
                                                                @Valid @RequestBody RoomInventoryDTO roomInventoryDTO) {
        RoomInventoryDTO updatedRoomInventoryDTO = roomInventoryService.updateRoomInventory(assetId, roomId, roomInventoryDTO);
        return ResponseEntity.ok(updatedRoomInventoryDTO);
    }

    @Operation(
            summary = "Delete a Room Inventory",
            description = "REST API to delete a room inventory by asset ID and room ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Room inventory deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Room inventory not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{assetId}/{roomId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long assetId, @PathVariable Long roomId) {
        roomInventoryService.deleteRoomInventory(assetId, roomId);
        return ResponseEntity.noContent().build();
    }
}
