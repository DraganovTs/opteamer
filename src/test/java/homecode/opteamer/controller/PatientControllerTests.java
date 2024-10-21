package homecode.opteamer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.dtos.PatientDTO;
import homecode.opteamer.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PatientControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

     PatientDTO patientDTO;

    @BeforeEach
    void setUp() {
        patientDTO = new PatientDTO();
        patientDTO.setId(1L);
        patientDTO.setName("John Doe");
        patientDTO.setNin("1234567897");
    }

    @Test
    void testGetPatientById() throws Exception {
        Mockito.when(patientService.getPatientById(1L)).thenReturn(patientDTO);

        mockMvc.perform(get( "/api/patients/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetPatientById_NotFound() throws Exception {
        Mockito.when(patientService.getPatientById(1L)).thenThrow(new ResourceNotFoundException("Patient not found with id: 1"));

        mockMvc.perform(get("/api/patients/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllPatients() throws Exception {
        List<PatientDTO> patientDTOList = Arrays.asList(patientDTO);
        Mockito.when(patientService.getAllPatients()).thenReturn(patientDTOList);

        mockMvc.perform(get("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void testCreatePatient() throws Exception {
        Mockito.when(patientService.createPatient(any(PatientDTO.class))).thenReturn(patientDTO);

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testUpdatePatient() throws Exception {
        Mockito.when(patientService.updatePatient(eq(1L), any(PatientDTO.class))).thenReturn(patientDTO);

        mockMvc.perform(put("/api/patients/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testUpdatePatient_NotFound() throws Exception {
        Mockito.when(patientService.updatePatient(eq(1L), any(PatientDTO.class)))
                .thenThrow(new ResourceNotFoundException("Patient not found with id: 1"));

        mockMvc.perform(put("/api/patients/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeletePatient() throws Exception {
        Mockito.doNothing().when(patientService).deletePatient(1L);

        mockMvc.perform(delete("/api/patients/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeletePatient_NotFound() throws Exception {
        Mockito.doThrow(new ResourceNotFoundException("Patient not found with id: 1")).when(patientService).deletePatient(1L);

        mockMvc.perform(delete("/api/patients/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
