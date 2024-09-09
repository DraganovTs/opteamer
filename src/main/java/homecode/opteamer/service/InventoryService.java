package homecode.opteamer.service;

import homecode.opteamer.model.Asset;
import homecode.opteamer.model.Inventory;
import homecode.opteamer.model.dtos.AssetDTO;
import homecode.opteamer.model.dtos.InventoryDTO;
import homecode.opteamer.repository.AssetRepository;
import homecode.opteamer.repository.InventoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        if (asset == null) {
            throw new NoSuchElementException();
        }
        inventory.setAsset(asset);
        inventory.setCount(inventoryDTO.getCount());
        inventory = inventoryRepository.save(inventory);
        return new InventoryDTO(inventory.getAssetId(), new AssetDTO(asset.getId(), asset.getType(), asset.getName()), inventory.getCount());
    }

    public Optional<InventoryDTO> updateInventory(Long id, InventoryDTO inventoryDTO) {
        return inventoryRepository.findById(id).map(inventory -> {
            inventory.setCount(inventoryDTO.getCount());
            inventoryRepository.save(inventory);
            return mapEntityToDTO(inventory);
        });
    }

    public List<InventoryDTO> getAllInventories() {
        List<InventoryDTO> list = new ArrayList<>();
        Iterable<Inventory> inventories = inventoryRepository.findAll();
        inventories.forEach(inventory -> list.add(mapEntityToDTO(inventory)));
        return list;
    }

    public Optional<InventoryDTO> getInventoryById(Long id) {
        try {
            Inventory inventory = inventoryRepository.findById(id).orElseThrow();
            return Optional.of(mapEntityToDTO(inventory));
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

    private Inventory mapDTOToEntity(InventoryDTO inventoryDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(inventoryDTO, Inventory.class);
    }

    private InventoryDTO mapEntityToDTO(Inventory inventory) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(inventory, InventoryDTO.class);
    }
}
