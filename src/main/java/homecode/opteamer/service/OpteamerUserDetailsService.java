package homecode.opteamer.service;

import homecode.opteamer.model.User;
import homecode.opteamer.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("OpteamerUserDetailsService")
public class OpteamerUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public OpteamerUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found whit username: " + username));
        return homecode.opteamer.model.OpteamerUserDetailsService.build(user);
    }
}
