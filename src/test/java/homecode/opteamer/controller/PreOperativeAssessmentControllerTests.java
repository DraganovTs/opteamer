package homecode.opteamer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import homecode.opteamer.model.dtos.PreOperativeAssessmentDTO;
import homecode.opteamer.service.PreOperativeAssessmentService;
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
public class PreOperativeAssessmentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PreOperativeAssessmentService preOperativeAssessmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private PreOperativeAssessmentDTO preOperativeAssessmentDTO;

    @BeforeEach
    void setUp() {
        preOperativeAssessmentDTO = new PreOperativeAssessmentDTO();
        preOperativeAssessmentDTO.setName("Test Assessment");
    }

    @Test
    void testCreatePreOperativeAssessment() throws Exception {
        Mockito.when(preOperativeAssessmentService.createPreOperativeAssessment(any(PreOperativeAssessmentDTO.class)))
                .thenReturn(preOperativeAssessmentDTO);

        mockMvc.perform(post("/api/preOperativeAssessments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(preOperativeAssessmentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Assessment"));
    }

    @Test
    void testUpdatePreOperativeAssessment() throws Exception {
        Mockito.when(preOperativeAssessmentService.updatePreOperativeAssessment(eq("Test Assessment"), any(PreOperativeAssessmentDTO.class)))
                .thenReturn(preOperativeAssessmentDTO);

        mockMvc.perform(put("/api/preOperativeAssessments/{name}", "Test Assessment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(preOperativeAssessmentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Assessment"));
    }

    @Test
    void testGetAllPreOperativeAssessments() throws Exception {
        List<PreOperativeAssessmentDTO> preOperativeAssessmentDTOList = Arrays.asList(preOperativeAssessmentDTO);
        Mockito.when(preOperativeAssessmentService.getAllPreOperativeAssessments()).thenReturn(preOperativeAssessmentDTOList);

        mockMvc.perform(get("/api/preOperativeAssessments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Assessment"));
    }

    @Test
    void testGetPreOperativeAssessmentByName() throws Exception {
        Mockito.when(preOperativeAssessmentService.getPreOperativeAssessment("Test Assessment"))
                .thenReturn(preOperativeAssessmentDTO);

        mockMvc.perform(get("/api/preOperativeAssessments/{name}", "Test Assessment")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Assessment"));
    }

    @Test
    void testDeletePreOperativeAssessment() throws Exception {
        Mockito.doNothing().when(preOperativeAssessmentService).deletePreOperativeAssessment("Test Assessment");

        mockMvc.perform(delete("/api/preOperativeAssessments/{name}", "Test Assessment")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
