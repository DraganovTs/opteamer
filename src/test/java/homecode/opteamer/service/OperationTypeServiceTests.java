package homecode.opteamer.service;

import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.Asset;
import homecode.opteamer.model.OperationProvider;
import homecode.opteamer.model.OperationType;
import homecode.opteamer.model.PreOperativeAssessment;
import homecode.opteamer.model.dtos.AssetDTO;
import homecode.opteamer.model.dtos.OperationProviderDTO;
import homecode.opteamer.model.dtos.OperationTypeDTO;
import homecode.opteamer.model.dtos.PreOperativeAssessmentDTO;
import homecode.opteamer.model.enums.AssetType;
import homecode.opteamer.model.enums.OperationProviderType;
import homecode.opteamer.model.enums.OperationRoomType;
import homecode.opteamer.repository.AssetRepository;
import homecode.opteamer.repository.OperationProviderRepository;
import homecode.opteamer.repository.OperationTypeRepository;
import homecode.opteamer.repository.PreOperativeAssessmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OperationTypeServiceTests {

    @Mock
    private OperationTypeRepository operationTypeRepository;
    @Mock
    private AssetRepository assetRepository;
    @Mock
    private PreOperativeAssessmentRepository preOperativeAssessmentRepository;
    @Mock
    private OperationProviderRepository operationProviderRepository;

    @InjectMocks
    private OperationTypeService operationTypeService;

    OperationProvider operationProvider;
    OperationProvider operationProvider2;
    OperationProviderDTO operationProviderDTO;
    OperationProviderDTO operationProviderDTO2;
    Set<OperationProvider> operationProviderSet = new HashSet<>();
    Set<OperationProviderDTO> operationProviderSetDTO = new HashSet<>();
    PreOperativeAssessment preOperativeAssessment;
    PreOperativeAssessment preOperativeAssessment2;
    PreOperativeAssessmentDTO preOperativeAssessmentDTO;
    PreOperativeAssessmentDTO preOperativeAssessmentDTO2;
    Set<PreOperativeAssessment> preOperativeAssessmentSet = new HashSet<>();
    Set<PreOperativeAssessmentDTO> preOperativeAssessmentSetDTO = new HashSet<>();
    Asset asset;
    Asset asset2;
    AssetDTO assetDTO;
    AssetDTO assetDTO2;
    Set<Asset> assetsSet = new HashSet<>();
    Set<AssetDTO> assetsDTOSet = new HashSet<>();
    OperationType operationType;
    OperationTypeDTO operationTypeDTO;

    @BeforeEach
    public void setUp() {
        preOperativeAssessment = new PreOperativeAssessment();
        preOperativeAssessment.setName("preOperativeAssessment");

        preOperativeAssessment2 = new PreOperativeAssessment();
        preOperativeAssessment2.setName("preOperativeAssessment2");

        preOperativeAssessmentSet.add(preOperativeAssessment);
        preOperativeAssessmentSet.add(preOperativeAssessment2);

        preOperativeAssessmentDTO = new PreOperativeAssessmentDTO();
        preOperativeAssessmentDTO.setName("preOperativeAssessment");

        preOperativeAssessmentDTO2 = new PreOperativeAssessmentDTO();
        preOperativeAssessmentDTO2.setName("preOperativeAssessment2");

        preOperativeAssessmentSetDTO.add(preOperativeAssessmentDTO);
        preOperativeAssessmentSetDTO.add(preOperativeAssessmentDTO2);

        asset = new Asset();
        asset.setId(1L);
        asset.setType(AssetType.MACHINE);
        asset.setName("Test Asset");

        asset2 = new Asset();
        asset2.setId(2L);
        asset2.setType(AssetType.EQUIPMENT);
        asset2.setName("Test Asset");

        assetsSet.add(asset);
        assetsSet.add(asset2);

        assetDTO = new AssetDTO();
        assetDTO.setId(1L);
        assetDTO.setType(AssetType.EQUIPMENT);
        assetDTO.setName("Test DTO Asset");

        assetDTO2 = new AssetDTO();
        assetDTO2.setId(2L);
        assetDTO2.setType(AssetType.MACHINE);
        assetDTO2.setName("Test DTO Asset");

        assetsDTOSet.add(assetDTO);
        assetsDTOSet.add(assetDTO2);

        operationProvider = new OperationProvider();
        operationProvider.setType(OperationProviderType.CP_ROOM_NURSE);

        operationProvider2 = new OperationProvider();
        operationProvider2.setType(OperationProviderType.SURGEON);

        operationProviderSet.add(operationProvider);
        operationProviderSet.add(operationProvider2);

        operationProviderDTO = new OperationProviderDTO();
        operationProviderDTO.setType(OperationProviderType.CP_ROOM_NURSE);

        operationProviderDTO2 = new OperationProviderDTO();
        operationProviderDTO2.setType(OperationProviderType.SURGEON);

        operationProviderSetDTO.add(operationProviderDTO);
        operationProviderSetDTO.add(operationProviderDTO2);

        operationType = new OperationType();
        operationType.setName("Test OperationType");
        operationType.setAssets(assetsSet);
        operationType.setRoomType(OperationRoomType.EMERGENCY_SURGERY);
        operationType.setPreOperativeAssessments(preOperativeAssessmentSet);
        operationType.setOperationProviders(operationProviderSet);
        operationType.setDurationHours(22);

        operationTypeDTO = new OperationTypeDTO();
        operationTypeDTO.setName("Test OperationType");
        operationTypeDTO.setAssetDTOS(assetsDTOSet);
        operationTypeDTO.setRoomType(OperationRoomType.EMERGENCY_SURGERY);
        operationTypeDTO.setPreOperativeAssessmentsDTO(preOperativeAssessmentSetDTO);
        operationTypeDTO.setOperationProvidersDTO(operationProviderSetDTO);
        operationTypeDTO.setDurationHours(22);

    }

    @Test
    void createOperationType_ShouldSaveAndReturnOperationTypeDTO(){
        when(operationTypeRepository.save(any(OperationType.class))).thenReturn(operationType);
        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));
        when(assetRepository.findById(2L)).thenReturn(Optional.of(asset2));
        when(preOperativeAssessmentRepository.findByName("preOperativeAssessment")).thenReturn(Optional.of(preOperativeAssessment));
        when(preOperativeAssessmentRepository.findByName("preOperativeAssessment2")).thenReturn(Optional.of(preOperativeAssessment2));


        when(operationProviderRepository.findByType(OperationProviderType.CP_ROOM_NURSE)).thenReturn(Optional.of(operationProvider));
        when(operationProviderRepository.findByType(OperationProviderType.SURGEON)).thenReturn(Optional.of(operationProvider2));

        OperationTypeDTO savedOperationTypeDTO = operationTypeService.createOperationType(operationTypeDTO);

        assertNotNull(savedOperationTypeDTO);
        assertEquals(operationTypeDTO.getRoomType(), savedOperationTypeDTO.getRoomType());
        assertEquals(operationTypeDTO.getOperationProvidersDTO().size(), savedOperationTypeDTO.getOperationProvidersDTO().size());
        assertEquals(operationTypeDTO.getAssetDTOS().size(), savedOperationTypeDTO.getAssetDTOS().size());
        assertEquals(operationTypeDTO.getPreOperativeAssessmentsDTO().size(), savedOperationTypeDTO.getPreOperativeAssessmentsDTO().size());
        assertEquals(operationTypeDTO.getName(), savedOperationTypeDTO.getName());
        assertEquals(operationTypeDTO.getDurationHours(), savedOperationTypeDTO.getDurationHours());

        verify(operationTypeRepository, times(1)).save(any(OperationType.class));
        verify(assetRepository, times(1)).findById(1L);
        verify(assetRepository, times(1)).findById(2L);
        verify(preOperativeAssessmentRepository, times(1)).findByName("preOperativeAssessment");
        verify(preOperativeAssessmentRepository, times(1)).findByName("preOperativeAssessment2");
        verify(operationProviderRepository, times(1)).findByType(OperationProviderType.CP_ROOM_NURSE);
        verify(operationProviderRepository, times(1)).findByType(OperationProviderType.SURGEON);
    }

    @Test
    void createOperationType_ShouldThrowIfAssetNotFound(){
        when(assetRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> operationTypeService.createOperationType(operationTypeDTO));

        verify(assetRepository, times(1)).findById(1L);
    }

    @Test
    void createOperationType_ShouldThrowIfPreOperativeAssessmentNotFound(){
        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));
        when(assetRepository.findById(2L)).thenReturn(Optional.of(asset2));
        when(preOperativeAssessmentRepository.findByName("preOperativeAssessment")).thenReturn(Optional.empty());
        when(preOperativeAssessmentRepository.findByName("preOperativeAssessment2")).thenReturn(Optional.of(preOperativeAssessment2));

        assertThrows(ResourceNotFoundException.class, () -> operationTypeService.createOperationType(operationTypeDTO));

        verify(assetRepository, times(1)).findById(1L);
        verify(assetRepository, times(1)).findById(2L);
        verify(preOperativeAssessmentRepository, times(1)).findByName("preOperativeAssessment");
        verify(preOperativeAssessmentRepository, times(1)).findByName("preOperativeAssessment2");
    }

    @Test
    void createOperationType_ShouldThrowIfOperationProviderNotFound(){
        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));
        when(assetRepository.findById(2L)).thenReturn(Optional.of(asset2));
        when(preOperativeAssessmentRepository.findByName("preOperativeAssessment")).thenReturn(Optional.of(preOperativeAssessment));
        when(preOperativeAssessmentRepository.findByName("preOperativeAssessment2")).thenReturn(Optional.of(preOperativeAssessment2));
        when(operationProviderRepository.findByType(OperationProviderType.CP_ROOM_NURSE)).thenReturn(Optional.of(operationProvider));
        when(operationProviderRepository.findByType(OperationProviderType.SURGEON)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> operationTypeService.createOperationType(operationTypeDTO));

        verify(assetRepository, times(1)).findById(1L);
        verify(assetRepository, times(1)).findById(2L);
        verify(preOperativeAssessmentRepository, times(1)).findByName("preOperativeAssessment");
        verify(preOperativeAssessmentRepository, times(1)).findByName("preOperativeAssessment2");
        verify(operationProviderRepository, times(1)).findByType(OperationProviderType.CP_ROOM_NURSE);
        verify(operationProviderRepository, times(1)).findByType(OperationProviderType.SURGEON);
    }
}
