package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.InventoryDTO;
import homecode.opteamer.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    @PostMapping()
    public ResponseEntity<InventoryDTO> createAsset(@RequestBody InventoryDTO inventoryDTO) {
        InventoryDTO createInventoryDTO = inventoryService.createInventory(inventoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createInventoryDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryDTO> updateAsset(@PathVariable Long id, @RequestBody InventoryDTO inventoryDTO) {
        Optional<InventoryDTO> inventoryDTOOptional = inventoryService.updateInventory(id, inventoryDTO);
        return inventoryDTOOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping()
    public ResponseEntity<List<InventoryDTO>> getAllAssets() {
        List<InventoryDTO> inventoryDTOList = inventoryService.getAllInventories();
        return ResponseEntity.status(HttpStatus.OK).body(inventoryDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getAssetById(@PathVariable Long id) {
        Optional<InventoryDTO> inventoryDTOOptional = inventoryService.getInventoryById(id);
        return inventoryDTOOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAssetById(@PathVariable Long id) {
        boolean isDeleted = inventoryService.deleteInventoryById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
