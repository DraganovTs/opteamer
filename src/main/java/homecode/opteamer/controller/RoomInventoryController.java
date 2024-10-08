package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.RoomInventoryDTO;
import homecode.opteamer.service.RoomInventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/roomInventories")
public class RoomInventoryController {

    private final RoomInventoryService roomInventoryService;

    public RoomInventoryController(RoomInventoryService roomInventoryService) {
        this.roomInventoryService = roomInventoryService;
    }

    @GetMapping("/{assetId}/{roomId}")
    public ResponseEntity<RoomInventoryDTO> getRoomInventory(@PathVariable("assetId") Long assetId,
                                                             @PathVariable("roomId") Long roomId) {
        RoomInventoryDTO roomInventoryDTO = roomInventoryService.getRoomInventoryById(assetId, roomId);
        return ResponseEntity.ok(roomInventoryDTO);
    }

    @GetMapping
    public ResponseEntity<List<RoomInventoryDTO>> getAllRoomInventories() {
        List<RoomInventoryDTO> roomInventoryDTOList = roomInventoryService.getAllRoomInventories();
        return ResponseEntity.ok(roomInventoryDTOList);
    }

    @PostMapping
    public ResponseEntity<RoomInventoryDTO> createRoomInventory(@Valid @RequestBody RoomInventoryDTO roomInventoryDTO) {
        RoomInventoryDTO roomInventoryDTOCreated = roomInventoryService.createRoomInventory(roomInventoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomInventoryDTOCreated);
    }

    @PutMapping("/{assetId}/{roomId}")
    public ResponseEntity<RoomInventoryDTO> updateRoomInventory(@PathVariable Long assetId,
                                                                @PathVariable Long roomId,
                                                                @Valid @RequestBody RoomInventoryDTO roomInventoryDTO) {
        RoomInventoryDTO updatedRoomInventoryDTO = roomInventoryService.updateRoomInventory(assetId, roomId, roomInventoryDTO);
        return ResponseEntity.ok(updatedRoomInventoryDTO);
    }

    @DeleteMapping("/{assetId}/{roomId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long assetId, @PathVariable Long roomId) {
        roomInventoryService.deleteRoomInventory(assetId, roomId);
        return ResponseEntity.noContent().build();
    }
}
