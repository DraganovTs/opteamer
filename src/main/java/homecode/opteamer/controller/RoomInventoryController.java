package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.RoomInventoryDTO;
import homecode.opteamer.service.RoomInventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        Optional<RoomInventoryDTO> roomInventoryDTOOptional = roomInventoryService.getRoomInventoryById(assetId, roomId);
        return roomInventoryDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<RoomInventoryDTO>> getAllRoomInventory() {
        List<RoomInventoryDTO> roomInventoryDTOList = roomInventoryService.getAllRoomInventory();
        return ResponseEntity.status(HttpStatus.OK).body(roomInventoryDTOList);
    }

    @PostMapping
    public ResponseEntity<RoomInventoryDTO> createRoomInventory(@RequestBody RoomInventoryDTO roomInventoryDTO) {
        try {
            RoomInventoryDTO roomInventoryDTOCreated = roomInventoryService.createRoomInventory(roomInventoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(roomInventoryDTOCreated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(roomInventoryDTO);
        }
    }

    @PutMapping("/{assetId}/{roomId}")
    public ResponseEntity<RoomInventoryDTO> updateRoomInventory(@PathVariable Long assetId,
                                                                @PathVariable Long roomId,
                                                                @RequestBody RoomInventoryDTO roomInventoryDTO) {
        Optional<RoomInventoryDTO> roomInventoryDTOOptional = roomInventoryService.updateRoomInventory(assetId, roomId,
                roomInventoryDTO);
        return roomInventoryDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{assetId}/{roomId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long assetId, @PathVariable Long roomId) {
        boolean deleted = roomInventoryService.deleteRoomInventory(assetId, roomId);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
