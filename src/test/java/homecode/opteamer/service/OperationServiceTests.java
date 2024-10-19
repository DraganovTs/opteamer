package homecode.opteamer.service;

import homecode.opteamer.exception.InvalidOperationException;
import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.*;
import homecode.opteamer.model.dtos.*;
import homecode.opteamer.model.enums.*;
import homecode.opteamer.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OperationServiceTests {

    @Mock
    private OperationRepository operationRepository;
    @Mock
    private OperationTypeRepository operationTypeRepository;
    @Mock
    private OperationRoomRepository operationRoomRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private TeamMemberRepository teamMemberRepository;

    @InjectMocks
    private OperationService operationService;

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
    OperationRoom operationRoom;
    OperationRoomDTO operationRoomDTO;
    Patient patient;
    PatientDTO patientDTO;
    TeamMember teamMember;
    Set<TeamMember> teamMemberSet = new HashSet<>();
    TeamMemberDTO teamMemberDTO;
    Set<TeamMemberDTO> teamMemberDTOSet = new HashSet<>();
    Operation operation;
    OperationDTO operationDTO;

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

        operationRoom = new OperationRoom();
        operationRoom.setRoomNr(101);
        operationRoom.setBuildingBlock("A");
        operationRoom.setFloor("33");
        operationRoom.setType(OperationRoomType.EMERGENCY_SURGERY);
        operationRoom.setState(OperationRoomState.MAINTENANCE);

        operationRoomDTO = new OperationRoomDTO();
        operationRoomDTO.setRoomNr(202);
        operationRoomDTO.setBuildingBlock("B");
        operationRoomDTO.setFloor("66");
        operationRoomDTO.setType(OperationRoomType.EMERGENCY_SURGERY);
        operationRoomDTO.setState(OperationRoomState.MAINTENANCE);

        patient = new Patient();
        patient.setId(1L);
        patient.setName("TestName");
        patient.setNin("123456789");

        patientDTO = new PatientDTO();
        patientDTO.setId(1L);
        patientDTO.setName("TestName");
        patientDTO.setNin("123456789");

        teamMember = new TeamMember();
        teamMember.setOperationProvider(operationProvider);

        teamMemberSet.add(teamMember);


        teamMemberDTO = new TeamMemberDTO();
        teamMemberDTO.setOperationProviderDTO(operationProviderDTO);


        teamMemberDTOSet.add(teamMemberDTO);


        operation = new Operation();
        operation.setOperationType(operationType);
        operation.setOperationRoom(operationRoom);
        operation.setPatient(patient);
        operation.setState(OperationState.IN_PROGRESS);
        operation.setStartDate(LocalDateTime.now());
        operation.setTeamMembers(teamMemberSet);

        operationDTO = new OperationDTO();
        operationDTO.setOperationTypeDTO(operationTypeDTO);
        operationDTO.setOperationRoomDTO(operationRoomDTO);
        operationDTO.setPatientDTO(patientDTO);
        operationDTO.setState(OperationState.IN_PROGRESS);
        operationDTO.setStartDate(LocalDateTime.now());
        operationDTO.setTeamMembersDTO(teamMemberDTOSet);

    }

    @Test
    void createOperation_ShouldSaveAndReturnOperationDTO(){
        when(operationRepository.save(any(Operation.class))).thenReturn(operation);
        when(operationTypeRepository.findByName(any())).thenReturn(Optional.of(operationType));
        when(operationRoomRepository.findById(any())).thenReturn(Optional.of(operationRoom));
        when(patientRepository.findById(any())).thenReturn(Optional.of(patient));
        when(teamMemberRepository.findById(any())).thenReturn(Optional.of(teamMember));

        OperationDTO savedOperationDTO = operationService.createOperation(operationDTO);

        assertNotNull(savedOperationDTO);
        assertEquals(operationDTO.getStartDate(), savedOperationDTO.getStartDate());

        verify(operationRepository,times(1)).save(any(Operation.class));
        verify(operationTypeRepository,times(1)).findByName(any());
        verify(operationRoomRepository,times(1)).findById(any());
        verify(patientRepository,times(1)).findById(any());
        verify(teamMemberRepository,times(1)).findById(any());
    }

    @Test
    void createOperation_ShouldThrowIfOperationTypeNotFound(){
        when(operationTypeRepository.findByName(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> operationService.createOperation(operationDTO));

        verify(operationTypeRepository,times(1)).findByName(any());
    }

    @Test
    void createOperation_ShouldThrowIfOperationRoomNotFound(){
        when(operationTypeRepository.findByName(any())).thenReturn(Optional.of(operationType));
        when(operationRoomRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> operationService.createOperation(operationDTO));

        verify(operationTypeRepository,times(1)).findByName(any());
        verify(operationRoomRepository,times(1)).findById(any());
    }

    @Test
    void createOperation_ShouldThrowIfPatientNotFound(){
        when(operationTypeRepository.findByName(any())).thenReturn(Optional.of(operationType));
        when(operationRoomRepository.findById(any())).thenReturn(Optional.of(operationRoom));
        when(patientRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> operationService.createOperation(operationDTO));

        verify(operationTypeRepository,times(1)).findByName(any());
        verify(operationRoomRepository,times(1)).findById(any());
        verify(patientRepository,times(1)).findById(any());
    }

    @Test
    void createOperation_ShouldThrowIfTeamMemberNotFound(){
        when(operationTypeRepository.findByName(any())).thenReturn(Optional.of(operationType));
        when(operationRoomRepository.findById(any())).thenReturn(Optional.of(operationRoom));
        when(patientRepository.findById(any())).thenReturn(Optional.of(patient));
        when(teamMemberRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> operationService.createOperation(operationDTO));

        verify(operationTypeRepository,times(1)).findByName(any());
        verify(operationRoomRepository,times(1)).findById(any());
        verify(patientRepository,times(1)).findById(any());
        verify(teamMemberRepository,times(1)).findById(any());
    }
    @Test
    void getOperationById_ShouldReturnOperationDTO_WhenOperationExists() {
        when(operationRepository.findById(anyLong())).thenReturn(Optional.of(operation));

        Optional<OperationDTO> foundOperation = operationService.getOperationById(1L);

        assertTrue(foundOperation.isPresent());
        assertEquals(operationDTO.getId(), foundOperation.get().getId());

        verify(operationRepository, times(1)).findById(anyLong());
    }

    @Test
    void getOperationById_ShouldReturnEmpty_WhenOperationDoesNotExist() {
        when(operationRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<OperationDTO> foundOperation = operationService.getOperationById(1L);

        assertFalse(foundOperation.isPresent());

        verify(operationRepository, times(1)).findById(anyLong());
    }

    @Test
    void getAllOperations_ShouldReturnListOfOperationDTOs() {
        when(operationRepository.findAll()).thenReturn(List.of(operation));

        List<OperationDTO> operationList = operationService.getAllOperations();

        assertNotNull(operationList);
        assertEquals(1, operationList.size());
        assertEquals(operationDTO.getId(), operationList.get(0).getId());

        verify(operationRepository, times(1)).findAll();
    }

    @Test
    void updateOperation_ShouldUpdateAndReturnOperationDTO_WhenOperationExists() {
        when(operationRepository.save(any(Operation.class))).thenReturn(operation);
        when(operationTypeRepository.findByName(any())).thenReturn(Optional.of(operationType));
        when(operationRoomRepository.findById(any())).thenReturn(Optional.of(operationRoom));
        when(patientRepository.findById(any())).thenReturn(Optional.of(patient));
        when(teamMemberRepository.findById(any())).thenReturn(Optional.of(teamMember));

        operationDTO.setState(OperationState.COMPLETED);

        Optional<OperationDTO> updatedOperation = operationService.updateOperation(1L, operationDTO);

        assertTrue(updatedOperation.isPresent());
        assertEquals(operationDTO.getState(), updatedOperation.get().getState());

        verify(operationRepository, times(1)).findById(anyLong());
        verify(operationRepository, times(1)).save(any(Operation.class));
    }

    @Test
    void updateOperation_ShouldReturnEmpty_WhenOperationDoesNotExist() {
        when(operationRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<OperationDTO> updatedOperation = operationService.updateOperation(1L, operationDTO);

        assertFalse(updatedOperation.isPresent());

        verify(operationRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteOperationById_ShouldReturnTrue_WhenOperationExists() {
        when(operationRepository.findById(anyLong())).thenReturn(Optional.of(operation));

        boolean isDeleted = operationService.deleteOperationById(1L);

        assertTrue(isDeleted);
        verify(operationRepository, times(1)).findById(anyLong());
        verify(operationRepository, times(1)).delete(any(Operation.class));
    }

    @Test
    void deleteOperationById_ShouldReturnFalse_WhenOperationDoesNotExist() {
        when(operationRepository.findById(anyLong())).thenReturn(Optional.empty());

        boolean isDeleted = operationService.deleteOperationById(1L);

        assertFalse(isDeleted);
        verify(operationRepository, times(1)).findById(anyLong());
        verify(operationRepository, never()).delete(any(Operation.class));
    }


    @Test
    void validateOperationTeam_ShouldThrowInvalidOperationException_WhenTeamIsIncomplete() {
        operationType.setOperationProviders(Set.of(operationProvider)); // Only 1 provider is required
        operation.setOperationType(operationType);

        teamMember.setOperationProvider(operationProvider2);

        operation.setTeamMembers(Set.of(teamMember));

        assertThrows(InvalidOperationException.class, () -> {
            operationService.createOperation(operationDTO);
        });

        verify(operationRepository, never()).save(any(Operation.class));
    }



}
