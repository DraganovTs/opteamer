package homecode.opteamer.service;

import homecode.opteamer.model.Asset;
import homecode.opteamer.model.Inventory;
import homecode.opteamer.model.dtos.AssetDTO;
import homecode.opteamer.model.dtos.InventoryDTO;
import homecode.opteamer.repository.AssetRepository;
import homecode.opteamer.repository.InventoryRepository;
import homecode.opteamer.util.MapperUtility;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class InventoryService {

    private final InventoryRepository inventoryRepository;

    private final AssetRepository assetRepository;

    public InventoryService(InventoryRepository inventoryRepository, AssetRepository assetRepository) {
        this.inventoryRepository = inventoryRepository;
        this.assetRepository = assetRepository;
    }


    public InventoryDTO createInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = new Inventory();
        Asset asset = assetRepository.findById(inventoryDTO.getAssetId()).get();
        inventory.setAsset(asset);
        inventory.setCount(inventoryDTO.getCount());
        inventory = inventoryRepository.save(inventory);
        return new InventoryDTO(inventory.getAssetId(), new AssetDTO(asset.getId(), asset.getType(), asset.getName()), inventory.getCount());
    }

    public Optional<InventoryDTO> updateInventory(Long id, InventoryDTO inventoryDTO) {
        return inventoryRepository.findById(id).map(inventory -> {
            inventory.setCount(inventoryDTO.getCount());
            inventoryRepository.save(inventory);
            return MapperUtility.mapEntityToDTO(inventory , InventoryDTO.class);
        });
    }

    public List<InventoryDTO> getAllInventories() {
        List<InventoryDTO> list = new ArrayList<>();
        Iterable<Inventory> inventories = inventoryRepository.findAll();
        inventories.forEach(inventory -> list.add(MapperUtility.mapEntityToDTO(inventory , InventoryDTO.class)));
        return list;
    }

    public Optional<InventoryDTO> getInventoryById(Long id) {
        try {
            Inventory inventory = inventoryRepository.findById(id).orElseThrow();
            return Optional.of(MapperUtility.mapEntityToDTO(inventory , InventoryDTO.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    public boolean deleteInventoryById(Long id) {
        return inventoryRepository.findById(id).map(inventory -> {
            inventoryRepository.delete(inventory);
            return true;
        }).orElse(false);
    }



}
