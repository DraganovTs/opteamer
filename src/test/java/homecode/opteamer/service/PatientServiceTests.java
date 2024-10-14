package homecode.opteamer.service;


import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.Patient;
import homecode.opteamer.model.dtos.PatientDTO;
import homecode.opteamer.repository.PatientRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTests {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;
    private Patient patient2;
    private PatientDTO patientDTO;

    @BeforeEach
    public void setUp() {
        patient = new Patient();
        patient.setName("TestName");
        patient.setNin("123456789");

        patient2 = new Patient();
        patient2.setName("TestName2");
        patient2.setNin("123456789");

        patientDTO = new PatientDTO();
        patientDTO.setName("TestName");
        patientDTO.setNin("123456789");
    }

    @Test
    void createPatient_ShouldSaveAndReturnPatient() {
        when(patientRepository.save(any(Patient.class)))
                .thenAnswer(invocation -> patient);

        PatientDTO savedDTO = patientService.createPatient(patientDTO);

        assertNotNull(savedDTO);
        assertEquals(patientDTO.getName(), savedDTO.getName());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void getPatient_ShouldReturnPatientDTO() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));

        PatientDTO foundPatient = patientService.getPatientById(1L);

        assertNotNull(foundPatient);
        assertEquals(patientDTO.getName(), foundPatient.getName());
        verify(patientRepository, times(1)).findById(anyLong());
    }


    @Test
    void getPatient_ShouldThrowExceptionIfPatientNotFound() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()-> {
            patientService.getPatientById(2L);
        });

        verify(patientRepository, times(1)).findById(anyLong());
    }

    @Test
    void updatePatient_ShouldUpdateAndReturnPatientDTO() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        patientDTO.setName("UpdatedName");
        PatientDTO savedDTO = patientService.updatePatient(1L, patientDTO);

        assertNotNull(savedDTO);
        assertEquals(patientDTO.getName(), savedDTO.getName());
        verify(patientRepository, times(1)).findById(anyLong());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void updatePatient_ShouldThrowExceptionIfPatientNotFound() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()-> {
            patientService.updatePatient(1L, patientDTO);
        });

        verify(patientRepository, times(1)).findById(anyLong());
    }

    @Test
    void getAllPatients_ShouldReturnListOfPatientDTOs() {
        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient,patient2));

        List<PatientDTO> patientDTOs = patientService.getAllPatients();

        assertNotNull(patientDTOs);
        assertEquals(patientDTOs.size(), 2);
        assertEquals(patientDTOs.get(0).getName(), patient.getName());
        assertEquals(patientDTOs.get(1).getName(), patient2.getName());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void deletePatient_ShouldDeletePatient() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));
        doNothing().when(patientRepository).delete(any(Patient.class));

        patientService.deletePatient(1L);

        verify(patientRepository, times(1)).findById(anyLong());
        verify(patientRepository, times(1)).delete(any(Patient.class));

    }

    @Test
    void deletePatient_ShouldThrowExceptionIfPatientNotFound() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()-> {
            patientService.deletePatient(1L);
        });

        verify(patientRepository, times(1)).findById(anyLong());
    }


}
