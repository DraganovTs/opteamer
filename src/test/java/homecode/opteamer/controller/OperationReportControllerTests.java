package homecode.opteamer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.dtos.*;
import homecode.opteamer.model.enums.*;
import homecode.opteamer.service.OperationReportService;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class OperationReportControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OperationReportService operationReportService;

    OperationReportDTO operationReportDTO;
    OperationProviderDTO operationProviderDTO;
    TeamMemberDTO teamMemberDTO1;
    TeamMemberDTO teamMemberDTO2;
    OperationDTO operationDTO;
    OperationTypeDTO operationTypeDTO;
    AssetDTO assetDTO1;
    AssetDTO assetDTO2;
    OperationProviderDTO operationProviderDTO1;
    OperationProviderDTO operationProviderDTO2;
    PreOperativeAssessmentDTO preOperativeAssessmentDTO1;
    PreOperativeAssessmentDTO preOperativeAssessmentDTO2;
    OperationRoomDTO operationRoomDTO;
    PatientDTO patientDTO;


    @BeforeEach
    void setUp() {
        operationProviderDTO = new OperationProviderDTO();
        operationProviderDTO.setType(OperationProviderType.CP_ROOM_NURSE);


        assetDTO1 = new AssetDTO();
        assetDTO1.setId(1L);
        assetDTO1.setName("Test Asset1");
        assetDTO1.setType(AssetType.MACHINE);

        assetDTO2 = new AssetDTO();
        assetDTO2.setId(1L);
        assetDTO2.setName("Test Asset2");
        assetDTO2.setType(AssetType.EQUIPMENT);

        operationProviderDTO1 = new OperationProviderDTO();
        operationProviderDTO1.setType(OperationProviderType.CP_ROOM_NURSE);

        operationProviderDTO2 = new OperationProviderDTO();
        operationProviderDTO2.setType(OperationProviderType.ANESTHESIOLOGIST);

        preOperativeAssessmentDTO1 = new PreOperativeAssessmentDTO();
        preOperativeAssessmentDTO1.setName("Test Assessment1");

        preOperativeAssessmentDTO2 = new PreOperativeAssessmentDTO();
        preOperativeAssessmentDTO2.setName("Test Assessment2");

        operationTypeDTO = new OperationTypeDTO();
        operationTypeDTO.setName("Test");
        operationTypeDTO.setRoomType(OperationRoomType.NEURO_SURGERY);
        operationTypeDTO.setDurationHours(2);
        operationTypeDTO.setAssetDTOS(new HashSet<>(Set.of(assetDTO1, assetDTO2)));
        operationTypeDTO.setOperationProvidersDTO(new HashSet<>(Set.of(operationProviderDTO1, operationProviderDTO2)));
        operationTypeDTO.setPreOperativeAssessmentsDTO(new HashSet<>(Set.of(preOperativeAssessmentDTO1, preOperativeAssessmentDTO2)));

        operationRoomDTO = new OperationRoomDTO();
        operationRoomDTO.setId(1L);
        operationRoomDTO.setRoomNr(222);
        operationRoomDTO.setType(OperationRoomType.GENERAL_SURGERY);
        operationRoomDTO.setState(OperationRoomState.STERILE);
        operationRoomDTO.setFloor("22");
        operationRoomDTO.setBuildingBlock("A");

        patientDTO = new PatientDTO();
        patientDTO.setId(1L);
        patientDTO.setName("John Doe");
        patientDTO.setNin("1234567897");

        teamMemberDTO1 = new TeamMemberDTO();
        teamMemberDTO1.setId(1L);
        teamMemberDTO1.setName("TestName");
        teamMemberDTO1.setOperationProviderDTO(operationProviderDTO1);

        teamMemberDTO2 = new TeamMemberDTO();
        teamMemberDTO2.setId(1L);
        teamMemberDTO2.setName("TestName");
        teamMemberDTO2.setOperationProviderDTO(operationProviderDTO2);

        operationDTO = new OperationDTO();
        operationDTO.setOperationTypeDTO(operationTypeDTO);
        operationDTO.setOperationRoomDTO(operationRoomDTO);
        operationDTO.setPatientDTO(patientDTO);
        operationDTO.setState(OperationState.IN_PROGRESS);
        operationDTO.setStartDate(LocalDateTime.now().plusDays(1));
        operationDTO.setTeamMembersDTO(new HashSet<>(Set.of(teamMemberDTO1, teamMemberDTO2)));

        operationReportDTO = new OperationReportDTO();
        operationReportDTO.setTeamMemberId(1L);
        operationReportDTO.setOperationId(1L);
        operationReportDTO.setTeamMemberDTO(teamMemberDTO1);
        operationReportDTO.setOperationDTO(operationDTO);
        operationReportDTO.setReport("Report must be at least 10 characters long");
    }

    @Test
    void testGetOperationReportById() throws Exception {
        when(operationReportService.findById(1L,1L)).thenReturn(Optional.of(operationReportDTO));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/operationReport/{teamMemberId}/{operationId}",1L,1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.report").value(operationReportDTO.getReport()));
    }

    @Test
    void testGetOperationReportByIdNotFound() throws Exception {
        when(operationReportService.findById(1L,1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operationReport/{teamMemberId}/{operationId}",1L,1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllOperationReports() throws Exception {
        when(operationReportService.findAll()).thenReturn(Arrays.asList(operationReportDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operationReport")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void testCreateOperationReport() throws Exception {
        when(operationReportService.createOperationReport(any(OperationReportDTO.class))).thenReturn(operationReportDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/operationReport")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(operationReportDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.report").value(operationReportDTO.getReport()));
    }

    @Test
    void testCreateOperationReportNotFound() throws Exception {
        doThrow(ResourceNotFoundException.class).when(operationReportService).createOperationReport(any(OperationReportDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/operationReport")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(operationReportDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateOperationReportViolatedValid() throws Exception {
        OperationReportDTO emtyOperationReportDTO = new OperationReportDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/operationReport")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emtyOperationReportDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateOperationReport() throws Exception {
        when(operationReportService.updateOperationReport(anyLong(),anyLong(), any(OperationReportDTO.class)))
                .thenReturn(Optional.of(operationReportDTO));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/operationReport/{teamMemberId}/{operationId}",1L,1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operationReportDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.report").value(operationReportDTO.getReport())); // Validate the response
    }



    @Test
    void testUpdateOperationReportNotFound() throws Exception {
        when(operationReportService.updateOperationReport(1L,1L,operationReportDTO))
                .thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/operationReport/{teamMemberId}/{operationId}",1L,1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(operationReportDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteOperationReport() throws Exception {
        when(operationReportService.deleteOperationReport(1L,1L))
                .thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/operationReport/{teamMemberId}/{operationId}",1L,1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteOperationReportNotFound() throws Exception {
       when(operationReportService.deleteOperationReport(1L,1L))
               .thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/operationReport/{teamMemberId}/{operationId}",1L,1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
