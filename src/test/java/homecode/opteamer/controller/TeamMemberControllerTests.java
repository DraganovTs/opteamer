package homecode.opteamer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import homecode.opteamer.exception.ResourceNotFoundException;
import homecode.opteamer.model.dtos.OperationProviderDTO;
import homecode.opteamer.model.dtos.TeamMemberDTO;
import homecode.opteamer.model.enums.OperationProviderType;
import homecode.opteamer.service.TeamMemberService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TeamMemberControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamMemberService teamMemberService;

    @Autowired
    private ObjectMapper objectMapper;

    OperationProviderDTO operationProviderDTO;
    TeamMemberDTO teamMemberDTO;

    @BeforeEach
    public void setUp() {
        operationProviderDTO = new OperationProviderDTO();
        operationProviderDTO.setType(OperationProviderType.CP_ROOM_NURSE);

        teamMemberDTO = new TeamMemberDTO();
        teamMemberDTO.setId(1L);
        teamMemberDTO.setName("TestName");
        teamMemberDTO.setOperationProviderDTO(operationProviderDTO);

    }


    @Test
    void testGetTeamMemberById() throws Exception {
        when(teamMemberService.findTeamMemberById(anyLong())).thenReturn(teamMemberDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teamMembers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("TestName"))
                .andExpect(jsonPath("$.operationProviderDTO.type").value(OperationProviderType.CP_ROOM_NURSE.toString()));
    }


    @Test
    void testGetTeamMemberByIdNotFound() throws Exception {
        when(teamMemberService.findTeamMemberById(anyLong()))
                .thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teamMembers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());


    }

    @Test
    void testGetAllTeamMembers() throws Exception {
        when(teamMemberService.getAllTeamMembers()).thenReturn(Arrays.asList(teamMemberDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teamMembers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("TestName"));
    }

    @Test
    void createTeamMember() throws Exception {
        when(teamMemberService.createTeamMember(any(TeamMemberDTO.class))).thenReturn(teamMemberDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/teamMembers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teamMemberDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("TestName"))
                .andExpect(jsonPath("$.operationProviderDTO.type").value(OperationProviderType.CP_ROOM_NURSE.toString()));
    }

    @Test
    void testUpdateTeamMember() throws Exception {
        when(teamMemberService.updateTeamMember(anyLong(),any(TeamMemberDTO.class))).thenReturn(teamMemberDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/teamMembers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teamMemberDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("TestName"));
    }

    @Test
    void testUpdateTeamMemberNotFound() throws Exception {
        when(teamMemberService.updateTeamMember(anyLong(),any(TeamMemberDTO.class))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/teamMembers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teamMemberDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteTeamMember() throws Exception {
        doNothing().when(teamMemberService).deleteTeamMember(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/teamMembers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


}
