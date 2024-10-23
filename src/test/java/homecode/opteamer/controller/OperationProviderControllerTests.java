package homecode.opteamer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.dtos.OperationProviderDTO;
import homecode.opteamer.model.enums.OperationProviderType;
import homecode.opteamer.service.OperationProviderService;
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

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class OperationProviderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OperationProviderService operationProviderService;

    @Autowired
    private ObjectMapper objectMapper;

    OperationProviderDTO operationProviderDTO;

    @BeforeEach
    void setUp() {
        operationProviderDTO = new OperationProviderDTO();
        operationProviderDTO.setType(OperationProviderType.CP_ROOM_NURSE);
    }

    @Test
    void testGetOperationProviderById() throws Exception {
        when(operationProviderService.getOperationProviderById(anyString())).thenReturn(operationProviderDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operationProviders/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value(OperationProviderType.CP_ROOM_NURSE.toString()));

    }

    @Test
    void testGetOperationProviderByIdNotFound() throws Exception {
        when(operationProviderService.getOperationProviderById(anyString())).thenThrow(
                new ResourceNotFoundException("Operation Provider whit type CP_ROOM_NURSE was not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operationProviders/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());

    }

    @Test
    void testGetAllOperationProviders() throws Exception {
        when(operationProviderService.getAllOperationProviders()).thenReturn(Arrays.asList(operationProviderDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operationProviders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].type").value(OperationProviderType.CP_ROOM_NURSE.toString()));
    }

    @Test
    void testCreateOperationProvider() throws Exception {
        when(operationProviderService.createOperationProvider(any(OperationProviderDTO.class)))
                .thenReturn(operationProviderDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/operationProviders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operationProviderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value(OperationProviderType.CP_ROOM_NURSE.toString()));  // Only check type
    }

    @Test
    void testUpdateOperationProvider() throws Exception {
        when(operationProviderService.updateOperationProvider(anyString(),any(OperationProviderDTO.class)))
                .thenReturn(operationProviderDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/operationProviders/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operationProviderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value(OperationProviderType.CP_ROOM_NURSE.toString()));

    }

    @Test
    void testUpdateOperationProviderNotFound() throws Exception {
        when(operationProviderService.updateOperationProvider(anyString(),any(OperationProviderDTO.class)))
                .thenThrow(new ResourceNotFoundException("Operation Provider not found for type: CP_ROOM_NURSE"));


        mockMvc.perform(MockMvcRequestBuilders.put("/api/operationProviders/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(operationProviderDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteOperationProviderById() throws Exception {
        when(operationProviderService.deleteOperationProvider(anyString())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/operationProviders/{id}", "CP_ROOM_NURSE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteOperationProviderByIdNotFound() throws Exception {
        when(operationProviderService.deleteOperationProvider(anyString()))
                .thenThrow(new ResourceNotFoundException("Operation Provider not found for type: CP_ROOM_NURSE"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/operationProviders/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
