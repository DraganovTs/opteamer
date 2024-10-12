package homecode.opteamer.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import homecode.opteamer.model.dtos.AssetDTO;
import homecode.opteamer.model.enums.AssetType;
import homecode.opteamer.service.AssetService;
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
public class AssetControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssetService assetService;

    @Autowired
    private ObjectMapper objectMapper;

    private AssetDTO assetDTO;

    @BeforeEach
    void setUp() {
        assetDTO = new AssetDTO();
        assetDTO.setId(1L);
        assetDTO.setName("Test Asset");
        assetDTO.setType(AssetType.MACHINE);
    }

    @Test
    void testCreateAsset() throws Exception {
        Mockito.when(assetService.save(any(AssetDTO.class))).thenReturn(assetDTO);

        mockMvc.perform(post("/api/assets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assetDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Asset"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testUpdateAsset() throws Exception {
        Mockito.when(assetService.updateAsset(eq(1L), any(AssetDTO.class))).thenReturn(assetDTO);

        mockMvc.perform(put("/api/assets/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assetDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Asset"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetAllAssets() throws Exception {
        List<AssetDTO> assetDTOList = Arrays.asList(assetDTO);
        Mockito.when(assetService.getAllAssets()).thenReturn(assetDTOList);

        mockMvc.perform(get("/api/assets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Asset"));
    }

    @Test
    void testGetAssetById() throws Exception {
        Mockito.when(assetService.getAssetById(1L)).thenReturn(assetDTO);

        mockMvc.perform(get("/api/assets/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Asset"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testDeleteAsset() throws Exception {
        Mockito.doNothing().when(assetService).deleteAssetById(1L);

        mockMvc.perform(delete("/api/assets/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


}
