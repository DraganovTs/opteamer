package homecode.opteamer.service;

import homecode.opteamer.model.Asset;
import homecode.opteamer.model.dtos.AssetDTO;
import homecode.opteamer.repository.AssetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AssetService {

    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public AssetDTO save(AssetDTO assetDTO) {
        Asset asset = mapDTOToEntity(assetDTO);
        asset = assetRepository.save(asset);
        return mapEntityToDTO(asset);
    }

    public Optional<AssetDTO> updateAsset(Long id,AssetDTO assetDTO) {
        return assetRepository.findById(id).map(asset -> {
            asset.setType(assetDTO.getType());
            asset.setName(assetDTO.getName());
            assetRepository.save(asset);
            return mapEntityToDTO(asset);
        });
    }

    public List<AssetDTO> getAllAssets() {
        List<AssetDTO> list = new ArrayList<>();
        Iterable<Asset> assets = assetRepository.findAll();
        assets.forEach(asset -> list.add(mapEntityToDTO(asset)));
        return list;
    }

    public Optional<AssetDTO> getAssetById(Long id) {
        try {
            Asset asset = assetRepository.findById(id).orElseThrow();
            return Optional.of(mapEntityToDTO(asset));
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    public boolean deleteAssetById(Long id) {
        return assetRepository.findById(id).map(asset -> {
            assetRepository.delete(asset);
            return true;
        }).orElse(false);
    }

    private Asset mapDTOToEntity(AssetDTO assetDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(assetDTO, Asset.class);
    }

    private AssetDTO mapEntityToDTO(Asset asset) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(asset, AssetDTO.class);
    }
}
