package homecode.opteamer.service;

import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.Asset;
import homecode.opteamer.model.dtos.AssetDTO;
import homecode.opteamer.model.enums.AssetType;
import homecode.opteamer.repository.AssetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssetServiceTests {

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private AssetService assetService;

    Asset asset = new Asset();
    AssetDTO assetDTO = new AssetDTO();

    @BeforeEach
    public void setUp() {
        asset.setId(1L);
        asset.setType(AssetType.MACHINE);
        asset.setName("Test Asset");

        assetDTO.setId(1L);
        assetDTO.setType(AssetType.EQUIPMENT);
        assetDTO.setName("Test DTO Asset");
    }

    @Test
    void save_ShouldSaveAndReturnAssetDTO(){
        when(assetRepository.save(any(Asset.class))).thenAnswer(invocation -> {
            Asset assetToSave = invocation.getArgument(0);
            assetToSave.setId(1L);
            return assetToSave;
        });
        AssetDTO savedAssetDTO = assetService.save(assetDTO);


        assertNotNull(savedAssetDTO);
        assertEquals(assetDTO.getName(), savedAssetDTO.getName());
        verify(assetRepository,times(1)).save(any(Asset.class));
    }

    @Test
    void updateAsset_ShouldUpdateAndReturnAssetDTO() {
        when(assetRepository.findById(anyLong())).thenReturn(Optional.of(asset));
        when(assetRepository.save(any(Asset.class))).thenReturn(asset);

        AssetDTO updatedAssetDTO = assetService.updateAsset(1L, assetDTO);

        assertNotNull(updatedAssetDTO);
        assertEquals(asset.getName(), updatedAssetDTO.getName());
        verify(assetRepository, times(1)).findById(1L);
        verify(assetRepository, times(1)).save(any(Asset.class));
    }

    @Test
    void updateAsset_ShouldThrowExceptionWhenAssetNotFound() {
        when(assetRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            assetService.updateAsset(1L, assetDTO);
        });

        verify(assetRepository, times(1)).findById(1L);
    }

    @Test
    void getAllAssets_ShouldReturnListOfAssetDTOs() {
        when(assetRepository.findAll()).thenReturn(Arrays.asList(asset));

        List<AssetDTO> assetDTOList = assetService.getAllAssets();

        assertNotNull(assetDTOList);
        assertEquals(1, assetDTOList.size());
        assertEquals(asset.getName(), assetDTOList.get(0).getName());
        verify(assetRepository, times(1)).findAll();
    }

    @Test
    void getAssetById_ShouldReturnAssetDTO() {
        when(assetRepository.findById(anyLong())).thenReturn(Optional.of(asset));

        AssetDTO foundAssetDTO = assetService.getAssetById(1L);

        assertNotNull(foundAssetDTO);
        assertEquals(asset.getName(), foundAssetDTO.getName());
        verify(assetRepository, times(1)).findById(1L);
    }

    @Test
    void getAssetById_ShouldThrowExceptionWhenAssetNotFound() {
        when(assetRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            assetService.getAssetById(1L);
        });

        verify(assetRepository, times(1)).findById(1L);
    }

    @Test
    void deleteAssetById_ShouldDeleteAsset() {
        when(assetRepository.findById(anyLong())).thenReturn(Optional.of(asset));
        doNothing().when(assetRepository).delete(any(Asset.class));

        assetService.deleteAssetById(1L);

        verify(assetRepository, times(1)).findById(1L);
        verify(assetRepository, times(1)).delete(asset);
    }

    @Test
    void deleteAssetById_ShouldThrowExceptionWhenAssetNotFound() {
        when(assetRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            assetService.deleteAssetById(1L);
        });

        verify(assetRepository, times(1)).findById(1L);
    }


}
