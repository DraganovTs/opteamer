package homecode.opteamer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.dtos.AssetDTO;
import homecode.opteamer.model.dtos.OperationRoomDTO;
import homecode.opteamer.model.dtos.RoomInventoryDTO;
import homecode.opteamer.model.enums.AssetType;
import homecode.opteamer.model.enums.OperationRoomState;
import homecode.opteamer.model.enums.OperationRoomType;
import homecode.opteamer.service.RoomInventoryService;
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
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RoomInventoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoomInventoryService roomInventoryService;

    RoomInventoryDTO roomInventoryDTO;
    AssetDTO assetDTO;
    OperationRoomDTO operationRoomDTO;


    @BeforeEach
    public void setUp() {

        assetDTO = new AssetDTO();
        assetDTO.setId(1L);
        assetDTO.setType(AssetType.MACHINE);
        assetDTO.setName("Test Asset");

        operationRoomDTO = new OperationRoomDTO();
        operationRoomDTO.setId(1L);
        operationRoomDTO.setBuildingBlock("A");
        operationRoomDTO.setFloor("4");
        operationRoomDTO.setRoomNr(22);
        operationRoomDTO.setState(OperationRoomState.STERILE);
        operationRoomDTO.setType(OperationRoomType.GENERAL_SURGERY);


        roomInventoryDTO = new RoomInventoryDTO();
        roomInventoryDTO.setRoomInventoryId(1L);
        roomInventoryDTO.setOperationRoomId(1L);
        roomInventoryDTO.setCount(3);
        roomInventoryDTO.setAssetDTO(assetDTO);
        roomInventoryDTO.setOperationRoomDTO(operationRoomDTO);

    }

    @Test
    void testGetRoomInventoryById() throws Exception {
        when(roomInventoryService.getRoomInventoryById(anyLong(), anyLong())).thenReturn(roomInventoryDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/roomInventories/{assetId}/{roomId}",1L,1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(3))
                .andExpect(jsonPath("$.roomInventoryId").value(roomInventoryDTO.getRoomInventoryId()))
                .andExpect(jsonPath("$.operationRoomId").value(roomInventoryDTO.getOperationRoomId()))
                .andExpect(jsonPath("$.assetDTO.name").value(assetDTO.getName()))
                .andExpect(jsonPath("$.assetDTO.type").value(assetDTO.getType().toString()))
                .andExpect(jsonPath("$.assetDTO.id").value(assetDTO.getId()))
                .andExpect(jsonPath("$.operationRoomDTO.id").value(operationRoomDTO.getId()))
                .andExpect(jsonPath("$.operationRoomDTO.type").value(operationRoomDTO.getType().toString()));
    }

    @Test
    void testGetRoomInventoryByIdNotFound() throws Exception {
        when(roomInventoryService.getRoomInventoryById(anyLong(), anyLong()))
                .thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/roomInventories/{assetId}/{roomId}",1L,1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllRoomInventory() throws Exception {
        when(roomInventoryService.getAllRoomInventories()).thenReturn(Arrays.asList(roomInventoryDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/roomInventories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$.[0].roomInventoryId").value(roomInventoryDTO.getRoomInventoryId()))
                .andExpect(jsonPath("$.[0].operationRoomId").value(roomInventoryDTO.getOperationRoomId()))
                .andExpect(jsonPath("$.[0].count").value(roomInventoryDTO.getCount()));
    }

    @Test
    void testGetAllRoomInventoriesEmpty() throws Exception {
        when(roomInventoryService.getAllRoomInventories()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/roomInventories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }


    @Test
    void testCreateRoomInventory() throws Exception {
        when(roomInventoryService.createRoomInventory(any(RoomInventoryDTO.class))).thenReturn(roomInventoryDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/roomInventories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomInventoryDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roomInventoryId").value(roomInventoryDTO.getRoomInventoryId()))
                .andExpect(jsonPath("$.operationRoomId").value(roomInventoryDTO.getOperationRoomId()))
                .andExpect(jsonPath("$.count").value(roomInventoryDTO.getCount()));

    }

    @Test
    void testCreateRoomInventoryValidationError() throws Exception {
        RoomInventoryDTO invalidRoomInventoryDTO = new RoomInventoryDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/roomInventories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRoomInventoryDTO)))
                .andExpect(status().isBadRequest());
    }



    @Test
    void testUpdateRoomInventory() throws Exception {
        when(roomInventoryService.updateRoomInventory(anyLong(),anyLong(),any(RoomInventoryDTO.class)))
                .thenReturn(roomInventoryDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/roomInventories/{assetId}/{roomId}",1L,1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomInventoryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomInventoryId").value(roomInventoryDTO.getRoomInventoryId()))
                .andExpect(jsonPath("$.operationRoomId").value(roomInventoryDTO.getOperationRoomId()))
                .andExpect(jsonPath("$.count").value(roomInventoryDTO.getCount()));
    }

    @Test
    void testUpdateRoomInventoryNotFound() throws Exception {
        when(roomInventoryService.updateRoomInventory(anyLong(), anyLong(),any(RoomInventoryDTO.class)))
                .thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/roomInventories/{assetId}/{roomId}",1L,1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomInventoryDTO)))
                .andExpect(status().isNotFound());
    }


    @Test
    void testUpdateRoomInventoryValidationError() throws Exception {
        RoomInventoryDTO invalidRoomInventoryDTO = new RoomInventoryDTO();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/roomInventories/{assetId}/{roomId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRoomInventoryDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteRoomInventory() throws Exception {
        doNothing().when(roomInventoryService).deleteRoomInventory(anyLong(), anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/roomInventories/{assetId}/{roomId}",1L,1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteRoomInventoryNotFound() throws Exception {
        doThrow(ResourceNotFoundException.class)
                .when(roomInventoryService).deleteRoomInventory(anyLong(), anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/roomInventories/{assetId}/{roomId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void testInternalServerError() throws Exception {
        when(roomInventoryService.getRoomInventoryById(anyLong(), anyLong()))
                .thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/roomInventories/{assetId}/{roomId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));
    }




}
