package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.AssetDTO;
import homecode.opteamer.service.AssetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping()
    public ResponseEntity<AssetDTO> createAsset(@RequestBody AssetDTO assetDTO) {
        AssetDTO createAssetDTO = assetService.save(assetDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAssetDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetDTO> updateAsset(@PathVariable Long id, @RequestBody AssetDTO assetDTO) {
        Optional<AssetDTO> assetDTOOptional = assetService.updateAsset(id, assetDTO);
        return assetDTOOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping()
    public ResponseEntity<List<AssetDTO>> getAllAssets() {
        List<AssetDTO> assetDTOList = assetService.getAllAssets();
        return ResponseEntity.status(HttpStatus.OK).body(assetDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetDTO> getAssetById(@PathVariable Long id) {
        Optional<AssetDTO> assetDTOOptional = assetService.getAssetById(id);
        return assetDTOOptional.map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAssetById(@PathVariable Long id) {
        boolean isDeleted = assetService.deleteAssetById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
