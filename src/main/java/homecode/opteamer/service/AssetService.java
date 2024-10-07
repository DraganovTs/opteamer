package homecode.opteamer.service;

import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.Asset;
import homecode.opteamer.model.dtos.AssetDTO;
import homecode.opteamer.repository.AssetRepository;
import homecode.opteamer.util.MapperUtility;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssetService {

    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public AssetDTO save(AssetDTO assetDTO) {
        Asset asset = MapperUtility.mapDTOToEntity(assetDTO, Asset.class);
        asset = assetRepository.save(asset);
        return MapperUtility.mapEntityToDTO(asset, AssetDTO.class);
    }

    public AssetDTO updateAsset(Long id, AssetDTO assetDTO) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));

        asset.setType(assetDTO.getType());
        asset.setName(assetDTO.getName());
        assetRepository.save(asset);

        return MapperUtility.mapEntityToDTO(asset, AssetDTO.class);
    }

    public List<AssetDTO> getAllAssets() {
        List<AssetDTO> assetDTOList = new ArrayList<>();
        Iterable<Asset> assets = assetRepository.findAll();
        assets.forEach(asset -> assetDTOList.add(MapperUtility.mapEntityToDTO(asset, AssetDTO.class)));
        return assetDTOList;
    }

    public AssetDTO getAssetById(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));
        return MapperUtility.mapEntityToDTO(asset, AssetDTO.class);
    }

    public void deleteAssetById(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));
        assetRepository.delete(asset);
    }
}
