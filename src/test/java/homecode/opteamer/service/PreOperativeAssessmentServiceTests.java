package homecode.opteamer.service;

import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.PreOperativeAssessment;
import homecode.opteamer.model.dtos.PreOperativeAssessmentDTO;
import homecode.opteamer.repository.PreOperativeAssessmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PreOperativeAssessmentServiceTests {

    @Mock
    private PreOperativeAssessmentRepository preOperativeAssessmentRepository;

    @InjectMocks
    private PreOperativeAssessmentService preOperativeAssessmentService;

    private PreOperativeAssessment preOperativeAssessment;
    private PreOperativeAssessmentDTO preOperativeAssessmentDTO;

    @BeforeEach
    public void setUp() {
        preOperativeAssessment = new PreOperativeAssessment();
        preOperativeAssessment.setName("PreOp1");

        preOperativeAssessmentDTO = new PreOperativeAssessmentDTO();
        preOperativeAssessmentDTO.setName("PreOpDTO1");
    }

    @Test
    void createPreOperativeAssessment_ShouldSaveAndReturnPreOperativeAssessmentDTO() {
        when(preOperativeAssessmentRepository.save(any(PreOperativeAssessment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PreOperativeAssessmentDTO savedDTO = preOperativeAssessmentService.createPreOperativeAssessment(preOperativeAssessmentDTO);

        assertNotNull(savedDTO);
        assertEquals(preOperativeAssessmentDTO.getName(), savedDTO.getName());
        verify(preOperativeAssessmentRepository, times(1)).save(any(PreOperativeAssessment.class));
    }

    @Test
    void getPreOperativeAssessment_ShouldReturnPreOperativeAssessmentDTO() {
        when(preOperativeAssessmentRepository.findById(anyString())).thenReturn(Optional.of(preOperativeAssessment));

        PreOperativeAssessmentDTO foundDTO = preOperativeAssessmentService.getPreOperativeAssessment("PreOp1");

        assertNotNull(foundDTO);
        assertEquals(preOperativeAssessment.getName(), foundDTO.getName());
        verify(preOperativeAssessmentRepository, times(1)).findById("PreOp1");
    }

    @Test
    void getPreOperativeAssessment_ShouldThrowExceptionWhenNotFound() {
        when(preOperativeAssessmentRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            preOperativeAssessmentService.getPreOperativeAssessment("PreOpNotFound");
        });

        verify(preOperativeAssessmentRepository, times(1)).findById("PreOpNotFound");
    }

    @Test
    void updatePreOperativeAssessment_ShouldUpdateAndReturnPreOperativeAssessmentDTO() {
        when(preOperativeAssessmentRepository.findById(anyString())).thenReturn(Optional.of(preOperativeAssessment));
        when(preOperativeAssessmentRepository.save(any(PreOperativeAssessment.class))).thenReturn(preOperativeAssessment);

        preOperativeAssessmentDTO.setName("UpdatedName");
        PreOperativeAssessmentDTO updatedDTO = preOperativeAssessmentService.updatePreOperativeAssessment("PreOp1", preOperativeAssessmentDTO);

        assertNotNull(updatedDTO);
        assertEquals(preOperativeAssessmentDTO.getName(), updatedDTO.getName());
        verify(preOperativeAssessmentRepository, times(1)).findById("PreOp1");
        verify(preOperativeAssessmentRepository, times(1)).save(any(PreOperativeAssessment.class));
    }

    @Test
    void updatePreOperativeAssessment_ShouldThrowExceptionWhenNotFound() {
        when(preOperativeAssessmentRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            preOperativeAssessmentService.updatePreOperativeAssessment("PreOpNotFound", preOperativeAssessmentDTO);
        });

        verify(preOperativeAssessmentRepository, times(1)).findById("PreOpNotFound");
    }

    @Test
    void getAllPreOperativeAssessments_ShouldReturnListOfPreOperativeAssessmentDTOs() {
        when(preOperativeAssessmentRepository.findAll()).thenReturn(Arrays.asList(preOperativeAssessment));

        List<PreOperativeAssessmentDTO> assessmentDTOList = preOperativeAssessmentService.getAllPreOperativeAssessments();

        assertNotNull(assessmentDTOList);
        assertEquals(1, assessmentDTOList.size());
        assertEquals(preOperativeAssessment.getName(), assessmentDTOList.get(0).getName());
        verify(preOperativeAssessmentRepository, times(1)).findAll();
    }

    @Test
    void deletePreOperativeAssessment_ShouldDeleteAssessment() {
        when(preOperativeAssessmentRepository.findById(anyString())).thenReturn(Optional.of(preOperativeAssessment));
        doNothing().when(preOperativeAssessmentRepository).delete(any(PreOperativeAssessment.class));

        preOperativeAssessmentService.deletePreOperativeAssessment("PreOp1");

        verify(preOperativeAssessmentRepository, times(1)).findById("PreOp1");
        verify(preOperativeAssessmentRepository, times(1)).delete(preOperativeAssessment);
    }

    @Test
    void deletePreOperativeAssessment_ShouldThrowExceptionWhenNotFound() {
        when(preOperativeAssessmentRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            preOperativeAssessmentService.deletePreOperativeAssessment("PreOpNotFound");
        });

        verify(preOperativeAssessmentRepository, times(1)).findById("PreOpNotFound");
    }
}
