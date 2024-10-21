package homecode.opteamer.service;

import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.*;
import homecode.opteamer.model.dtos.*;
import homecode.opteamer.model.embededIds.AssessmentId;
import homecode.opteamer.model.enums.OperationProviderType;
import homecode.opteamer.repository.AssessmentRepository;
import homecode.opteamer.repository.PatientRepository;
import homecode.opteamer.repository.PreOperativeAssessmentRepository;
import homecode.opteamer.repository.TeamMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssessmentServiceTests {

    @Mock
    private AssessmentRepository assessmentRepository;
    @Mock
    private TeamMemberRepository teamMemberRepository;
    @Mock
    private PreOperativeAssessmentRepository preOperativeAssessmentRepository;
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private AssessmentService assessmentService;

    TeamMember teamMember;
    TeamMemberDTO teamMemberDTO;
    OperationProvider operationProvider;
    OperationProviderDTO operationProviderDTO;
    Patient patient;
    PatientDTO patientDTO;
    PreOperativeAssessment preOperativeAssessment;
    PreOperativeAssessmentDTO preOperativeAssessmentDTO;
    AssessmentId assessmentId;
    Assessment assessment;
    AssessmentDTO assessmentDTO;


    @BeforeEach
    public void setUp() {
        operationProvider = new OperationProvider();
        operationProvider.setType(OperationProviderType.CP_ROOM_NURSE);

        operationProviderDTO = new OperationProviderDTO();
        operationProviderDTO.setType(OperationProviderType.CP_ROOM_NURSE);

        teamMember = new TeamMember();
        teamMember.setId(1L);
        teamMember.setOperationProvider(operationProvider);

        teamMemberDTO = new TeamMemberDTO();
        teamMemberDTO.setId(1L);
        teamMemberDTO.setOperationProviderDTO(operationProviderDTO);

        patient = new Patient();
        patient.setId(1L);
        patient.setName("TestName");
        patient.setNin("123456789");

        patientDTO = new PatientDTO();
        patientDTO.setId(1L);
        patientDTO.setName("TestName");
        patientDTO.setNin("123456789");

        preOperativeAssessment = new PreOperativeAssessment();
        preOperativeAssessment.setName("PreOp1");

        preOperativeAssessmentDTO = new PreOperativeAssessmentDTO();
        preOperativeAssessmentDTO.setName("PreOp1");

        assessmentId = new AssessmentId(1L, 1L, "PreOp1");

        assessment = new Assessment();
        assessment.setAssessmentId(assessmentId);
        assessment.setPatient(patient);
        assessment.setPreOperativeAssessment(preOperativeAssessment);
        assessment.setTeamMember(teamMember);
        assessment.setStartDate(LocalDateTime.now());

        assessmentDTO = new AssessmentDTO();
        assessmentDTO.setTeamMemberId(1L);
        assessmentDTO.setPatientId(1L);
        assessmentDTO.setPreOpAssessmentId("PreOp1");
        assessmentDTO.setPatientDTO(patientDTO);
        assessmentDTO.setTeamMemberDTO(teamMemberDTO);
        assessmentDTO.setPreOperativeAssessmentDTO(preOperativeAssessmentDTO);
        assessmentDTO.setStartDate(LocalDateTime.now());
    }

    @Test
    void testGetAssessmentById_ShouldReturnAssessmentDTO() {
        when(assessmentRepository.findById(any(AssessmentId.class))).thenReturn(Optional.of(assessment));

        Optional<AssessmentDTO> foundAssessment = assessmentService.getAssessmentById(1L, "PreOp1", 1L);

        assertNotNull(foundAssessment);
        assertEquals(assessmentDTO.getPatientDTO().getName(), foundAssessment.get().getPatientDTO().getName());
        verify(assessmentRepository, times(1)).findById(any(AssessmentId.class));
    }

    @Test
    void testGetAssessmentById_ShouldNotFound() {
        when(assessmentRepository.findById(any(AssessmentId.class))).thenReturn(Optional.empty());

        Optional<AssessmentDTO> result = assessmentService.getAssessmentById(1L, "PreOp1", 1L);

        assertFalse(result.isPresent());
        verify(assessmentRepository, times(1)).findById(any(AssessmentId.class));
    }

    @Test
    void testGetAllAssessments_ShouldReturnListOfAssessmentDTO() {
        when(assessmentRepository.findAll()).thenReturn(Arrays.asList(assessment));

        List<AssessmentDTO> foundAssessments = assessmentService.getAllAssessments();

        assertNotNull(foundAssessments);

        verify(assessmentRepository, times(1)).findAll();
    }

    @Test
    void testCreateAssessment_ShouldSaveAndReturnAssessmentDTO() {
        when(teamMemberRepository.findById(1L)).thenReturn(Optional.of(teamMember));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(preOperativeAssessmentRepository.findByName("PreOp1")).thenReturn(Optional.of(preOperativeAssessment));
        when(assessmentRepository.save(any(Assessment.class))).thenReturn(assessment);

        AssessmentDTO savedAssessment = assessmentService.createAssessment(assessmentDTO);

        assertNotNull(savedAssessment);
        assertEquals(assessmentDTO.getPatientDTO().getName(), savedAssessment.getPatientDTO().getName());
        verify(assessmentRepository, times(1)).save(any(Assessment.class));
    }

    @Test
    void testCreateAssessment_ShouldThrowIfTeamMemberNotFound() {
        when(teamMemberRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> assessmentService.createAssessment(assessmentDTO));

        verify(teamMemberRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateAssessment_ShouldThrowIfPreOperativeAssessmentNotFound() {
        when(teamMemberRepository.findById(1L)).thenReturn(Optional.of(teamMember));
        when(preOperativeAssessmentRepository.findByName(anyString())).thenReturn(Optional.of(preOperativeAssessment));

        assertThrows(ResourceNotFoundException.class, () -> assessmentService.createAssessment(assessmentDTO));

        verify(teamMemberRepository, times(1)).findById(1L);
        verify(preOperativeAssessmentRepository, times(1)).findByName(anyString());
    }


    @Test
    void testCreateAssessment_ShouldThrowIfPatientNotFound() {
        when(teamMemberRepository.findById(1L)).thenReturn(Optional.of(teamMember));
        when(preOperativeAssessmentRepository.findByName(anyString())).thenReturn(Optional.of(preOperativeAssessment));
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> assessmentService.createAssessment(assessmentDTO));

        verify(teamMemberRepository, times(1)).findById(1L);
        verify(preOperativeAssessmentRepository, times(1)).findByName(anyString());
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateAssessment_Success() {
        when(assessmentRepository.findById(any(AssessmentId.class))).thenReturn(Optional.of(assessment));
        when(assessmentRepository.save(any(Assessment.class))).thenReturn(assessment);

        Optional<AssessmentDTO> result = assessmentService.updateAssessment(1L, 1L, "PreOp1", assessmentDTO);

        assertTrue(result.isPresent());
        assertEquals(assessmentDTO.getStartDate(), result.get().getStartDate());
        verify(assessmentRepository, times(1)).save(any(Assessment.class));
    }

    @Test
    void testUpdateAssessment_NotFound() {
        when(assessmentRepository.findById(any(AssessmentId.class))).thenReturn(Optional.empty());

        Optional<AssessmentDTO> result = assessmentService.updateAssessment(1L, 1L, "PreOp1", assessmentDTO);

        assertFalse(result.isPresent());
        verify(assessmentRepository, never()).save(any(Assessment.class));
    }

    @Test
    void testDeleteAssessment_Success() {
        when(assessmentRepository.findById(any(AssessmentId.class))).thenReturn(Optional.of(assessment));

        boolean result = assessmentService.deleteAssessment(1L, 1L, "PreOp1");

        assertTrue(result);
        verify(assessmentRepository, times(1)).delete(any(Assessment.class));
    }

    @Test
    void testDeleteAssessment_NotFound() {
        when(assessmentRepository.findById(any(AssessmentId.class))).thenReturn(Optional.empty());

        boolean result = assessmentService.deleteAssessment(1L, 1L, "PreOp1");

        assertFalse(result);
        verify(assessmentRepository, never()).delete(any(Assessment.class));
    }
}
