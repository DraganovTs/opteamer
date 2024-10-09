package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.AssetDTO;
import homecode.opteamer.service.AssetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Assets",
        description = "CRUD REST APIs in Opteamer Application to CREATE, UPDATE, FETCH AND DELETE Assets"
)
@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @Operation(
            summary = "Create a New Asset",
            description = "REST API to create a new asset in the Opteamer application"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Asset created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<AssetDTO> createAsset(@Valid @RequestBody AssetDTO assetDTO) {
        AssetDTO createdAsset = assetService.save(assetDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAsset);
    }

    @Operation(
            summary = "Update an Existing Asset",
            description = "REST API to update an existing asset by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Asset updated successfully"),
            @ApiResponse(responseCode = "404", description = "Asset not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AssetDTO> updateAsset(@PathVariable Long id, @Valid @RequestBody AssetDTO assetDTO) {
        AssetDTO updatedAsset = assetService.updateAsset(id, assetDTO);
        return ResponseEntity.ok(updatedAsset);
    }

    @Operation(
            summary = "Fetch All Assets",
            description = "REST API to get a list of all assets in the Opteamer application"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assets fetched successfully")
    })
    @GetMapping
    public ResponseEntity<List<AssetDTO>> getAllAssets() {
        List<AssetDTO> assets = assetService.getAllAssets();
        return ResponseEntity.ok(assets);
    }

    @Operation(
            summary = "Fetch Asset by ID",
            description = "REST API to get an asset by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Asset fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Asset not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AssetDTO> getAssetById(@PathVariable Long id) {
        AssetDTO asset = assetService.getAssetById(id);
        return ResponseEntity.ok(asset);
    }

    @Operation(
            summary = "Delete an Asset",
            description = "REST API to delete an asset by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Asset deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Asset not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssetById(@PathVariable Long id) {
        assetService.deleteAssetById(id);
        return ResponseEntity.noContent().build();
    }
}
