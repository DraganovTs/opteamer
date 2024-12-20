package homecode.opteamer.service;

import homecode.opteamer.exception.InvalidOperationException;
import homecode.opteamer.model.Role;
import homecode.opteamer.model.User;
import homecode.opteamer.model.dtos.UserRegistrationDTO;
import homecode.opteamer.model.enums.ERole;
import homecode.opteamer.repository.RoleRepository;
import homecode.opteamer.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(UserRegistrationDTO userRegistrationDTO) {
        if (userRepository.existsByUsername(userRegistrationDTO.getUsername()) ||
                userRepository.existsByEmail(userRegistrationDTO.getEmail())) {
            throw new InvalidOperationException("User with this username or email already exists.");
        }

        Role role = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new InvalidOperationException("Default user role not found."));

        User user = new User(
                userRegistrationDTO.getUsername(),
                userRegistrationDTO.getEmail(),
                passwordEncoder.encode(userRegistrationDTO.getPassword()),
                new HashSet<>(Arrays.asList(role))
        );

        userRepository.save(user);
    }
}
