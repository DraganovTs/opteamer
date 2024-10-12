package homecode.opteamer.service;

import homecode.opteamer.model.Asset;
import homecode.opteamer.model.Inventory;
import homecode.opteamer.model.dtos.AssetDTO;
import homecode.opteamer.model.dtos.InventoryDTO;
import homecode.opteamer.model.enums.AssetType;
import homecode.opteamer.repository.AssetRepository;
import homecode.opteamer.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTests {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private AssetDTO assetDTO;
    private Asset asset;
    private InventoryDTO inventoryDTO;
    private Inventory inventory;

    @BeforeEach
    public void setUp() {
        assetDTO = new AssetDTO();
        assetDTO.setId(1L);
        assetDTO.setType(AssetType.EQUIPMENT);
        assetDTO.setName("Test Asset");

        asset = new Asset();
        asset.setId(1L);
        asset.setType(AssetType.EQUIPMENT);
        asset.setName("Test Asset");

        inventoryDTO = new InventoryDTO();
        inventoryDTO.setAssetId(1L);
        inventoryDTO.setAsset(assetDTO);
        inventoryDTO.setCount(5);

        inventory = new Inventory();
        inventory.setAssetId(1L);
        inventory.setAsset(asset);
        inventory.setCount(5);
    }

    @Test
    void save_ShouldSaveAndReturnInventoryDTO() {
        // Arrange
        mockInventoryRepository();
        mockAssetRepository();

        // Act
        InventoryDTO savedInventoryDTO = inventoryService.createInventory(inventoryDTO);

        // Assert
        assertNotNull(savedInventoryDTO);
        assertEquals(inventory.getCount(), savedInventoryDTO.getCount());
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    void update_ShouldUpdateAndReturnInventoryDTO() {
        // Arrange
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory)); // Return the existing inventory
        when(assetRepository.findById(asset.getId())).thenReturn(Optional.of(asset)); // Return the asset

        inventoryDTO.setCount(22); // Update count in DTO

        // Act
        Optional<InventoryDTO> updatedInventoryDTOOpt = inventoryService.updateInventory(1L, inventoryDTO);

        // Assert
        assertNotNull(updatedInventoryDTOOpt);
        InventoryDTO updatedInventoryDTO = updatedInventoryDTOOpt.get();
        assertNotNull(updatedInventoryDTO);
        assertEquals(inventoryDTO.getCount(), updatedInventoryDTO.getCount());
        verify(inventoryRepository, times(1)).findById(1L);
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    private void mockInventoryRepository() {
        when(inventoryRepository.save(any(Inventory.class))).thenAnswer(invocation -> {
            Inventory inventoryToSave = invocation.getArgument(0);
            inventoryToSave.setAssetId(1L);
            return inventoryToSave;
        });
    }

    private void mockAssetRepository() {
        when(assetRepository.findById(anyLong())).thenReturn(Optional.of(asset));
    }
}
