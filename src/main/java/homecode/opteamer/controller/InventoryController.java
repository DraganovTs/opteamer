package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.InventoryDTO;
import homecode.opteamer.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "CRUD REST APIs for Inventories",
        description = "CRUD REST APIs for managing inventories in the Opteamer Application"
)
@RestController
@RequestMapping("/api/inventories")
@Validated
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Operation(
            summary = "Create Inventory",
            description = "REST API to create a new inventory item"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Inventory created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<InventoryDTO> createInventory(@Valid @RequestBody InventoryDTO inventoryDTO) {
        InventoryDTO createdInventoryDTO = inventoryService.createInventory(inventoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInventoryDTO);
    }

    @Operation(
            summary = "Update Inventory",
            description = "REST API to update an existing inventory item by ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventory updated successfully"),
            @ApiResponse(responseCode = "404", description = "Inventory not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable Long id, @Valid @RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.updateInventory(id, inventoryDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "Get All Inventories",
            description = "REST API to fetch all inventories"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all inventories"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllInventories() {
        List<InventoryDTO> inventoryDTOList = inventoryService.getAllInventories();
        return ResponseEntity.ok(inventoryDTOList);
    }

    @Operation(
            summary = "Get Inventory by ID",
            description = "REST API to fetch a specific inventory item by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventory found and retrieved"),
            @ApiResponse(responseCode = "404", description = "Inventory not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable Long id) {
        return inventoryService.getInventoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "Delete Inventory",
            description = "REST API to delete an inventory item by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Inventory deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Inventory not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryById(@PathVariable Long id) {
        return inventoryService.deleteInventoryById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
