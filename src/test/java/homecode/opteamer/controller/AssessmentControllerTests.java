package homecode.opteamer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import homecode.opteamer.model.dtos.*;
import homecode.opteamer.model.enums.OperationProviderType;
import homecode.opteamer.service.AssessmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AssessmentControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AssessmentService assessmentService;

    AssessmentDTO assessmentDTO;
    OperationProviderDTO operationProviderDTO;
    TeamMemberDTO teamMemberDTO;
    PreOperativeAssessmentDTO preOperativeAssessmentDTO;
    PatientDTO patientDTO;

    @BeforeEach
    void setUp() {
        operationProviderDTO = new OperationProviderDTO();
        operationProviderDTO.setType(OperationProviderType.CP_ROOM_NURSE);

        teamMemberDTO = new TeamMemberDTO();
        teamMemberDTO.setId(1L);
        teamMemberDTO.setName("TestName");
        teamMemberDTO.setOperationProviderDTO(operationProviderDTO);

        preOperativeAssessmentDTO = new PreOperativeAssessmentDTO();
        preOperativeAssessmentDTO.setName("Test Assessment");

        patientDTO = new PatientDTO();
        patientDTO.setId(1L);
        patientDTO.setName("John Doe");
        patientDTO.setNin("1234567897");

        assessmentDTO = new AssessmentDTO();
        assessmentDTO.setTeamMemberId(1L);
        assessmentDTO.setPreOpAssessmentId("Test Assessment");
        assessmentDTO.setPatientId(1L);
        assessmentDTO.setTeamMemberDTO(teamMemberDTO);
        assessmentDTO.setPreOperativeAssessmentDTO(preOperativeAssessmentDTO);
        assessmentDTO.setPatientDTO(patientDTO);
        assessmentDTO.setStartDate(LocalDateTime.now().plusDays(1));
    }

    @Test
    void testGetAssessmentById() throws Exception {
        when(assessmentService.getAssessmentById(anyLong(),anyString(),anyLong())).thenReturn(Optional.of(assessmentDTO));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/assessments/{teamMemberId}/{preOpAName}/{patientId}",1L,"Test Assessment",1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamMemberId").value(1L));
    }

    @Test
    void testGetAssessmentByIdNotFound() throws Exception {
        when(assessmentService.getAssessmentById(1L,"Test Assessment",1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/assessments/{teamMemberId}/{preOpAName}/{patientId}",1L,"Test Assessment",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    void testGetAllAssessments() throws Exception {
        when(assessmentService.getAllAssessments()).thenReturn(Arrays.asList(assessmentDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/assessments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void testGetAllAssessmentsEmpty() throws Exception {
        when(assessmentService.getAllAssessments()).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/assessments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }


    @Test
    void testCreateAssessment() throws Exception {
        when(assessmentService.createAssessment(any(AssessmentDTO.class))).thenReturn(assessmentDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/assessments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assessmentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.teamMemberId").value(1L));
    }

    @Test
    void testUpdateAssessmentInvalidAssessment() throws Exception {
        AssessmentDTO errorAssessmentDTO = new AssessmentDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/assessments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(errorAssessmentDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateAssessment() throws Exception {
        when(assessmentService.updateAssessment(anyLong(),anyLong(),anyString(),any(AssessmentDTO.class)))
                .thenReturn(Optional.of(assessmentDTO));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/assessments/{teamMemberId}/{patientId}/{preOpAName}",
                                1L, 1L,"Test Assessment", preOperativeAssessmentDTO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(preOperativeAssessmentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamMemberId").value(1L));
    }

    @Test
    void testUpdateAssessmentNotFound() throws Exception {
        when(assessmentService.updateAssessment(anyLong(),anyLong(),anyString(),any(AssessmentDTO.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/assessments/{teamMemberId}/{patientId}/{preOpAName}",
                                1L, 1L,"Test Assessment", preOperativeAssessmentDTO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(preOperativeAssessmentDTO)))
                .andExpect(status().isNotFound());
    }


    @Test
    void testDeleteAssessment() throws Exception {
        when(assessmentService.deleteAssessment(anyLong(),anyLong(),anyString()))
                .thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/assessments/{teamMemberId}/{patientId}/{preOpAName}",
                1L, 1L,"Test Assessment")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAssessmentNotFound() throws Exception {
        when(assessmentService.deleteAssessment(anyLong(),anyLong(),anyString()))
                .thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/assessments/{teamMemberId}/{patientId}/{preOpAName}",
                                1L, 1L,"Test Assessment")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
