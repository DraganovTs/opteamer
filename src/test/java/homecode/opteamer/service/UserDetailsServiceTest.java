package homecode.opteamer.service;

import homecode.opteamer.model.Role;
import homecode.opteamer.model.User;
import homecode.opteamer.model.enums.ERole;
import homecode.opteamer.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OpteamerUserDetailsService opteamerUserDetailsService;

    private User user;

    @BeforeEach
    void setUp() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1L,ERole.ROLE_USER,new HashSet<>()));
        user = new User("testuser", "testuser@example.com", "password123", roles);
    }

    @Test
    void testLoadUserByUsername_Success() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        UserDetails userDetails = opteamerUserDetailsService.loadUserByUsername("testuser");

        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());

        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            opteamerUserDetailsService.loadUserByUsername("nonexistentuser");
        });

        assertEquals("User not found whit username: nonexistentuser", exception.getMessage());

        verify(userRepository, times(1)).findByUsername("nonexistentuser");
    }
}
