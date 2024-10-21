package homecode.opteamer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import homecode.opteamer.model.dtos.AssetDTO;
import homecode.opteamer.model.dtos.InventoryDTO;
import homecode.opteamer.model.enums.AssetType;
import homecode.opteamer.service.InventoryService;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class InventoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private InventoryDTO inventoryDTO;

    @BeforeEach
    void setUp() {
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setName("Test Asset");
        assetDTO.setType(AssetType.MACHINE);

        inventoryDTO = new InventoryDTO();
        inventoryDTO.setAssetId(1L);
        inventoryDTO.setAsset(assetDTO);
    }

    @Test
    void testCreateInventory() throws Exception {
        Mockito.when(inventoryService.createInventory(any(InventoryDTO.class)))
                .thenReturn(inventoryDTO);

        mockMvc.perform(post("/api/inventories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventoryDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.asset.name").value("Test Asset"))
                .andExpect(jsonPath("$.assetId").value(1L));
    }

    @Test
    void testUpdateInventory() throws Exception {
        Mockito.when(inventoryService.updateInventory(eq(1L), any(InventoryDTO.class)))
                .thenReturn(Optional.of(inventoryDTO));

        mockMvc.perform(put("/api/inventories/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventoryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.asset.name").value("Test Asset"))
                .andExpect(jsonPath("$.assetId").value(1L));
    }

    @Test
    void testGetAllInventories() throws Exception {
        List<InventoryDTO> inventoryDTOList = Arrays.asList(inventoryDTO);
        Mockito.when(inventoryService.getAllInventories()).thenReturn(inventoryDTOList);

        mockMvc.perform(get("/api/inventories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].asset.name").value("Test Asset"));
    }

    @Test
    void testGetInventoryById() throws Exception {
        Mockito.when(inventoryService.getInventoryById(1L))
                .thenReturn(Optional.of(inventoryDTO));

        mockMvc.perform(get("/api/inventories/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.asset.name").value("Test Asset"))
                .andExpect(jsonPath("$.assetId").value(1L));
    }

    @Test
    void testDeleteInventoryById() throws Exception {
        Mockito.when(inventoryService.deleteInventoryById(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/inventories/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


}
