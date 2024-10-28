package homecode.opteamer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import homecode.opteamer.model.dtos.UserRegistrationDTO;
import homecode.opteamer.service.UserRegistrationService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserRegistrationControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRegistrationService userRegistrationService;

    private UserRegistrationDTO validUser;

    @BeforeEach
    void setUp() {
        validUser = new UserRegistrationDTO();
        validUser.setUsername("testuser");
        validUser.setPassword("password123");
        validUser.setEmail("testuser@example.com");
    }

    @Test
    void testCreateUser_Success() throws Exception {
        mockMvc.perform(post("/api/userRegistration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateUser_BadRequest() throws Exception {
        UserRegistrationDTO invalidUser = new UserRegistrationDTO();

        mockMvc.perform(post("/api/userRegistration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateUser_InternalServerError() throws Exception {
        doThrow(new RuntimeException("Unexpected error"))
                .when(userRegistrationService).createUser(any(UserRegistrationDTO.class));

        mockMvc.perform(post("/api/userRegistration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));
    }
}
