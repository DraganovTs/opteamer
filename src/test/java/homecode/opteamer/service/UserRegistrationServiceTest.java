package homecode.opteamer.service;

import homecode.opteamer.model.Role;
import homecode.opteamer.model.User;
import homecode.opteamer.model.dtos.UserRegistrationDTO;
import homecode.opteamer.model.enums.ERole;
import homecode.opteamer.repository.RoleRepository;
import homecode.opteamer.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserRegistrationService userRegistrationService;

    private UserRegistrationDTO userRegistrationDTO;
    private Role userRole;

    @BeforeEach
    void setUp() {
        userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername("testuser");
        userRegistrationDTO.setEmail("testuser@example.com");
        userRegistrationDTO.setPassword("password123");

        userRole = new Role();
        userRole.setId(1L);
        userRole.setName(ERole.ROLE_USER);
    }

    @Test
    void testCreateUser_Success() {
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(userRole));

        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123");

        userRegistrationService.createUser(userRegistrationDTO);

        verify(userRepository, times(1)).save(any(User.class));

        verify(roleRepository, times(1)).findByName(ERole.ROLE_USER);

        verify(passwordEncoder, times(1)).encode("password123");
    }

    @Test
    void testCreateUser_RoleNotFound() {
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            userRegistrationService.createUser(userRegistrationDTO);
        });

        verify(userRepository, never()).save(any(User.class));
    }

}
