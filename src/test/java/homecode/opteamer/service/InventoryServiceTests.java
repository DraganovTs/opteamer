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


import java.util.Arrays;
import java.util.List;
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

     AssetDTO assetDTO;
     Asset asset;
     InventoryDTO inventoryDTO;
     Inventory inventory;

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
       when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);
       when(assetRepository.findById(anyLong())).thenReturn(Optional.of(asset));


        InventoryDTO savedInventoryDTO = inventoryService.createInventory(inventoryDTO);


        assertNotNull(savedInventoryDTO);
        assertEquals(inventory.getCount(), savedInventoryDTO.getCount());
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    void update_ShouldUpdateAndReturnInventoryDTO() {
        when(inventoryRepository.findById(any())).thenReturn(Optional.of(inventory)); // Return the existing inventory
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);

        inventoryDTO.setCount(22);


        Optional<InventoryDTO> updatedInventoryDTOOpt = inventoryService.updateInventory(1L, inventoryDTO);


        assertNotNull(updatedInventoryDTOOpt);
        InventoryDTO updatedInventoryDTO = updatedInventoryDTOOpt.get();
        assertEquals(inventory.getCount(), updatedInventoryDTO.getCount());
        verify(inventoryRepository, times(1)).findById(any());
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    void getInventoryByID_ShouldReturnInventoryDTO() {
        when(inventoryRepository.findById(any())).thenReturn(Optional.of(inventory)); // Return the existing inventory

        Optional<InventoryDTO> foundedInventoryDTO = inventoryService.getInventoryById(1L);

        assertNotNull(foundedInventoryDTO);
        assertEquals(inventory.getCount(), foundedInventoryDTO.get().getCount());
        verify(inventoryRepository, times(1)).findById(any());
    }

    @Test
    void getAllInventory_ShouldReturnListOfInventoryDTO() {
        when(inventoryRepository.findAll()).thenReturn(Arrays.asList(inventory));

        List<InventoryDTO> allInventories = inventoryService.getAllInventories();

        assertNotNull(allInventories);
        assertEquals(1, allInventories.size());
        verify(inventoryRepository, times(1)).findAll();
    }

    @Test
    void deleteInventory_ShouldDeleteInventoryDTO() {
        when(inventoryRepository.findById(any())).thenReturn(Optional.of(inventory));
        doNothing().when(inventoryRepository).delete(any());

        inventoryService.deleteInventoryById(1L);

        verify(inventoryRepository, times(1)).findById(any());
        verify(inventoryRepository, times(1)).delete(any());
    }








}
