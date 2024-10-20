package homecode.opteamer.service;

import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.*;
import homecode.opteamer.model.dtos.*;
import homecode.opteamer.model.embededIds.OperationReportId;
import homecode.opteamer.model.enums.*;
import homecode.opteamer.repository.OperationReportRepository;
import homecode.opteamer.repository.OperationRepository;
import homecode.opteamer.repository.TeamMemberRepository;
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
public class OperationReportServiceTests {

    @Mock
    private OperationReportRepository operationReportRepository;
    @Mock
    private TeamMemberRepository teamMemberRepository;
    @Mock
    private OperationRepository operationRepository;

    @InjectMocks
    private OperationReportService operationReportService;

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
    OperationReport operationReport;
    OperationReportDTO operationReportDTO;
    OperationReportId operationReportId;

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
        teamMember.setId(1l);
        teamMember.setOperationProvider(operationProvider);

        teamMemberSet.add(teamMember);


        teamMemberDTO = new TeamMemberDTO();
        teamMemberDTO.setId(1L);
        teamMemberDTO.setOperationProviderDTO(operationProviderDTO);


        teamMemberDTOSet.add(teamMemberDTO);


        operation = new Operation();
        operation.setId(1L);
        operation.setOperationType(operationType);
        operation.setOperationRoom(operationRoom);
        operation.setPatient(patient);
        operation.setState(OperationState.IN_PROGRESS);
        operation.setStartDate(LocalDateTime.now());
        operation.setTeamMembers(teamMemberSet);

        operationDTO = new OperationDTO();
        operationDTO.setId(1L);
        operationDTO.setOperationTypeDTO(operationTypeDTO);
        operationDTO.setOperationRoomDTO(operationRoomDTO);
        operationDTO.setPatientDTO(patientDTO);
        operationDTO.setState(OperationState.IN_PROGRESS);
        operationDTO.setStartDate(LocalDateTime.now());
        operationDTO.setTeamMembersDTO(teamMemberDTOSet);

        operationReportId = new OperationReportId(1L,1L);

        operationReport = new OperationReport();
        operationReport.setOperationReportId(operationReportId);
        operationReport.setOperation(operation);
        operationReport.setTeamMember(teamMember);
        operationReport.setReport("Test Report");

        operationReportDTO = new OperationReportDTO();
        operationReportDTO.setOperationId(1L);
        operationReportDTO.setTeamMemberId(1L);
        operationReportDTO.setTeamMemberId(1L);
        operationReportDTO.setOperationDTO(operationDTO);
        operationReportDTO.setTeamMemberDTO(teamMemberDTO);
        operationReportDTO.setReport("Test Report");
    }

    @Test
    void findById_ShouldReturnOperationReportDTO_WhenReportExists() {
        when(operationReportRepository.findById(any(OperationReportId.class))).thenReturn(Optional.of(operationReport));

        Optional<OperationReportDTO> foundReport = operationReportService.findById(teamMember.getId(), operation.getId());

        assertNotNull(foundReport);
        assertEquals(operationReportDTO.getReport(), foundReport.get().getReport());

        verify(operationReportRepository, times(1)).findById(any(OperationReportId.class));
    }

    @Test
    void findById_ShouldReturnEmpty_WhenReportDoesNotExist() {
        when(operationReportRepository.findById(any(OperationReportId.class))).thenReturn(Optional.empty());

        Optional<OperationReportDTO> foundReport = operationReportService.findById(teamMember.getId(), operation.getId());

        assertFalse(foundReport.isPresent());

        verify(operationReportRepository, times(1)).findById(any(OperationReportId.class));
    }


    @Test
    void findAll_ShouldReturnListOfOperationReportDTOs() {
        when(operationReportRepository.findAll()).thenReturn(List.of(operationReport));

        List<OperationReportDTO> reportList = operationReportService.findAll();

        assertNotNull(reportList);
        assertEquals(1, reportList.size());
        assertEquals(operationReportDTO.getReport(), reportList.get(0).getReport());

        verify(operationReportRepository, times(1)).findAll();
    }

    @Test
    void createOperationReport_ShouldSaveAndReturnOperationReportDTO_WhenEntitiesExist() {
        when(teamMemberRepository.findById(anyLong())).thenReturn(Optional.of(teamMember));
        when(operationRepository.findById(anyLong())).thenReturn(Optional.of(operation));
        when(operationReportRepository.save(any(OperationReport.class))).thenReturn(operationReport);

        OperationReportDTO savedReport = operationReportService.createOperationReport(operationReportDTO);

        assertNotNull(savedReport);
        assertEquals(operationReportDTO.getReport(), savedReport.getReport());

        verify(teamMemberRepository, times(1)).findById(anyLong());
        verify(operationRepository, times(1)).findById(anyLong());
        verify(operationReportRepository, times(1)).save(any(OperationReport.class));
    }

    @Test
    void createOperationReport_ShouldThrowResourceNotFoundException_WhenTeamMemberNotFound() {
        when(teamMemberRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> operationReportService.createOperationReport(operationReportDTO));
    }

    @Test
    void createOperationReport_ShouldThrowResourceNotFoundException_WhenOperationNotFound() {
        when(teamMemberRepository.findById(anyLong())).thenReturn(Optional.of(teamMember));
        when(operationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> operationReportService.createOperationReport(operationReportDTO));
    }


    @Test
    void updateOperationReport_ShouldUpdateAndReturnOperationReportDTO_WhenReportExists() {
        when(operationReportRepository.findById(any(OperationReportId.class))).thenReturn(Optional.of(operationReport));
        when(operationReportRepository.save(any(OperationReport.class))).thenReturn(operationReport);

        Optional<OperationReportDTO> updatedReport = operationReportService.updateOperationReport(teamMember.getId(), operation.getId(), operationReportDTO);

        assertTrue(updatedReport.isPresent());
        assertEquals(operationReportDTO.getReport(), updatedReport.get().getReport());

        verify(operationReportRepository, times(1)).findById(any(OperationReportId.class));
        verify(operationReportRepository, times(1)).save(any(OperationReport.class));
    }

    @Test
    void updateOperationReport_ShouldReturnEmpty_WhenReportDoesNotExist() {
        when(operationReportRepository.findById(any(OperationReportId.class))).thenReturn(Optional.empty());

        Optional<OperationReportDTO> updatedReport = operationReportService.updateOperationReport(teamMember.getId(), operation.getId(), operationReportDTO);

        assertFalse(updatedReport.isPresent());

        verify(operationReportRepository, times(1)).findById(any(OperationReportId.class));
        verify(operationReportRepository, never()).save(any(OperationReport.class));
    }

    @Test
    void deleteOperationReport_ShouldReturnTrue_WhenReportExists() {
        when(operationReportRepository.findById(any(OperationReportId.class))).thenReturn(Optional.of(operationReport));

        boolean isDeleted = operationReportService.deleteOperationReport(teamMember.getId(), operation.getId());

        assertTrue(isDeleted);

        verify(operationReportRepository, times(1)).findById(any(OperationReportId.class));
        verify(operationReportRepository, times(1)).delete(any(OperationReport.class));
    }

    @Test
    void deleteOperationReport_ShouldReturnFalse_WhenReportDoesNotExist() {
        when(operationReportRepository.findById(any(OperationReportId.class))).thenReturn(Optional.empty());

        boolean isDeleted = operationReportService.deleteOperationReport(teamMember.getId(), operation.getId());

        assertFalse(isDeleted);

        verify(operationReportRepository, times(1)).findById(any(OperationReportId.class));
        verify(operationReportRepository, never()).delete(any(OperationReport.class));
    }



}
