package homecode.opteamer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import homecode.opteamer.model.dtos.OperationRoomDTO;
import homecode.opteamer.model.enums.OperationRoomState;
import homecode.opteamer.model.enums.OperationRoomType;
import homecode.opteamer.service.OperationRoomService;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class OperationRoomControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OperationRoomService operationRoomService;

    @Autowired
    private ObjectMapper objectMapper;

    OperationRoomDTO operationRoomDTO;

    @BeforeEach
    void setUp() {
        operationRoomDTO = new OperationRoomDTO();
        operationRoomDTO.setId(1L);
        operationRoomDTO.setRoomNr(222);
        operationRoomDTO.setType(OperationRoomType.GENERAL_SURGERY);
        operationRoomDTO.setState(OperationRoomState.STERILE);
        operationRoomDTO.setFloor("22");
        operationRoomDTO.setBuildingBlock("A");
    }


    @Test
    void testGetOperationRoomById() throws Exception {
        when(operationRoomService.getOperationRoomById(1L)).thenReturn(Optional.of(operationRoomDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operationRooms/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomNr").value(222))
                .andExpect(jsonPath("$.type").value(OperationRoomType.GENERAL_SURGERY.toString()))
                .andExpect(jsonPath("$.state").value(OperationRoomState.STERILE.toString()))
                .andExpect(jsonPath("$.floor").value("22"))
                .andExpect(jsonPath("$.buildingBlock").value("A"));
    }

    @Test
    void testGetOperationRoomByIdNotFound() throws Exception {
        when(operationRoomService.getOperationRoomById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operationRooms/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllOperationRooms() throws Exception {
        when(operationRoomService.getAllOperationRooms()).thenReturn(Arrays.asList(operationRoomDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operationRooms")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].roomNr").value(222));
    }

    @Test
    void testCreateOperationRoom() throws Exception {

        when(operationRoomService.createOperationRoom(any(OperationRoomDTO.class))).thenReturn(operationRoomDTO);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/operationRooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operationRoomDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.roomNr").value(222))
                .andExpect(jsonPath("$.type").value(OperationRoomType.GENERAL_SURGERY.toString()))
                .andExpect(jsonPath("$.state").value(OperationRoomState.STERILE.toString()))
                .andExpect(jsonPath("$.floor").value("22"))
                .andExpect(jsonPath("$.buildingBlock").value("A"));
    }


    @Test
    void testUpdateOperationRoom() throws Exception {
        Long roomId = 1L; // Define the ID you expect
        when(operationRoomService.updateOperationRoom(eq(roomId), any(OperationRoomDTO.class)))
                .thenReturn(Optional.of(operationRoomDTO));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/operationRooms/{id}", roomId) // Include the path variable
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operationRoomDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.roomNr").value(222))
                .andExpect(jsonPath("$.type").value(OperationRoomType.GENERAL_SURGERY.toString()))
                .andExpect(jsonPath("$.state").value(OperationRoomState.STERILE.toString()))
                .andExpect(jsonPath("$.floor").value("22"))
                .andExpect(jsonPath("$.buildingBlock").value("A"));
    }


    @Test
    void testUpdateOperationRoomNotFound() throws Exception {
        when(operationRoomService.updateOperationRoom(1L, operationRoomDTO))
                .thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/operationRooms/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operationRoomDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteOperationRoomById() throws Exception {
        when(operationRoomService.deleteOperationRoomById(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/operationRooms/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteOperationRoomByIdNotFound() throws Exception {
        when(operationRoomService.deleteOperationRoomById(1L)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/operationRooms/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
