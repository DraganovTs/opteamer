package homecode.opteamer.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.dtos.AssetDTO;
import homecode.opteamer.model.dtos.OperationProviderDTO;
import homecode.opteamer.model.dtos.OperationTypeDTO;
import homecode.opteamer.model.dtos.PreOperativeAssessmentDTO;
import homecode.opteamer.model.enums.AssetType;
import homecode.opteamer.model.enums.OperationProviderType;
import homecode.opteamer.model.enums.OperationRoomType;
import homecode.opteamer.service.OperationTypeService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class OperationTypeControllerTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private OperationTypeService operationTypeService;

    OperationTypeDTO operationTypeDTO;
    AssetDTO assetDTO1;
    AssetDTO assetDTO2;
    OperationProviderDTO operationProviderDTO1;
    OperationProviderDTO operationProviderDTO2;
    PreOperativeAssessmentDTO preOperativeAssessmentDTO1;
    PreOperativeAssessmentDTO preOperativeAssessmentDTO2;


    @BeforeEach
    void setUp() {
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
    }


    @Test
    void getOperationTypeByName() throws Exception {
        when(operationTypeService.getOperationTypeByName("Test")).thenReturn(operationTypeDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operationTypes/{name}", "Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(operationTypeDTO.getName()))
                .andExpect(jsonPath("$.roomType").value(operationTypeDTO.getRoomType().name()))  // Use name() for enum
                .andExpect(jsonPath("$.durationHours").value(operationTypeDTO.getDurationHours()))
                .andExpect(jsonPath("$.assetDTOS.size()").value(operationTypeDTO.getAssetDTOS().size()))
                .andExpect(jsonPath("$.operationProvidersDTO.size()").value(operationTypeDTO.getOperationProvidersDTO().size()))
                .andExpect(jsonPath("$.preOperativeAssessmentsDTO.size()").value(operationTypeDTO.getPreOperativeAssessmentsDTO().size()));
    }

    @Test
    void getOperationTypeByNameNotFound() throws Exception {
        when(operationTypeService.getOperationTypeByName("Test"))
                .thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operationTypes/{name}", "Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllOperationTypes() throws Exception {
        when(operationTypeService.getAllOperationTypes()).thenReturn(Arrays.asList(operationTypeDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operationTypes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value(operationTypeDTO.getName()));
    }

    @Test
    void testGetAllOperationTypesEmpty() throws Exception {
        when(operationTypeService.getAllOperationTypes()).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operationTypes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    void testCreateOperationType() throws Exception {
        when(operationTypeService.createOperationType(any(OperationTypeDTO.class))).thenReturn(operationTypeDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/operationTypes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operationTypeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(operationTypeDTO.getName()));
    }

    @Test
    void testCreateOperationTypeNotFound() throws Exception {
        when(operationTypeService.createOperationType(operationTypeDTO)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/operationTypes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(objectMapper.writeValueAsString(operationTypeDTO)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testCreateOperationTypeValidationError() throws Exception {
        OperationTypeDTO operationTypeDTO = new OperationTypeDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/operationTypes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operationTypeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateOperationType() throws Exception {
        when(operationTypeService.updateOperationType(eq("Test"), any(OperationTypeDTO.class))).thenReturn(operationTypeDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/operationTypes/{name}","Test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operationTypeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(operationTypeDTO.getName()));
    }

    @Test
    void testUpdateOperationTypeNotFound() throws Exception {
        when(operationTypeService.updateOperationType(eq("Test"), any(OperationTypeDTO.class)))
                .thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/operationTypes/{name}","Test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operationTypeDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateOperationTypeValidationError() throws Exception {

        OperationTypeDTO errorDTO = new OperationTypeDTO();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/operationTypes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(errorDTO)))
                .andExpect(status().isInternalServerError());

    }

    @Test
    void testDeleteOperationType() throws Exception {
        doNothing().when(operationTypeService).deleteOperationType("Test");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/operationTypes/{name}", "Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteOperationTypeNotFound() throws Exception {
        doThrow(ResourceNotFoundException.class).when(operationTypeService).deleteOperationType("Test");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/operationTypes/{name}", "Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testInternalServerError() throws Exception {
        when(operationTypeService.getOperationTypeByName("Test"))
                .thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/roomInventories/{name}", "Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));
    }


}
