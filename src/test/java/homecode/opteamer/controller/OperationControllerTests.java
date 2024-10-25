package homecode.opteamer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import homecode.opteamer.model.dtos.*;
import homecode.opteamer.model.enums.*;
import homecode.opteamer.service.OperationService;
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
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class OperationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OperationService operationService;

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
    TeamMemberDTO teamMemberDTO1;
    TeamMemberDTO teamMemberDTO2;

    @BeforeEach
    public void setUp() {
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
    }


    @Test
    void getOperationById() throws Exception {
        when(operationService.getOperationById(anyLong())).thenReturn(Optional.of(operationDTO));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/operations/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value(OperationState.IN_PROGRESS.toString()))
                .andExpect(jsonPath("$.teamMembersDTO.size()").value(2));
    }

    @Test
    void getOperationByIdNotFound() throws Exception {
        when(operationService.getOperationById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operations/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllOperations() throws Exception {
        when(operationService.getAllOperations()).thenReturn(Arrays.asList(operationDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void getAllOperationsEmptyList() throws Exception {
        when(operationService.getAllOperations()).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    void createOperation() throws Exception {
        when(operationService.createOperation(any(OperationDTO.class))).thenReturn(operationDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/operations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(operationDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.state").value(OperationState.IN_PROGRESS.toString()))
                .andExpect(jsonPath("$.teamMembersDTO.size()").value(2));
    }

    @Test
    void createOperationViolateValidation() throws Exception {
        OperationDTO errorOperationDTO = new OperationDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/operations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(errorOperationDTO)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void testUpdateOperation() throws Exception {
        when(operationService.updateOperation(eq(1L), any(OperationDTO.class))).thenReturn(Optional.of(operationDTO));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/operations/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value(OperationState.IN_PROGRESS.toString()))
                .andExpect(jsonPath("$.teamMembersDTO.size()").value(2));
    }



    @Test
    void testUpdateOperationNotFound() throws Exception {
        when(operationService.updateOperation(eq(1L),any(OperationDTO.class))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/operations/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(operationDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteOperation() throws Exception {
       when(operationService.deleteOperationById(anyLong())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/operations/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteOperationNotFound() throws Exception {
        when(operationService.deleteOperationById(anyLong())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/operations/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
