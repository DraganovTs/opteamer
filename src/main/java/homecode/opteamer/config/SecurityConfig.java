package homecode.opteamer.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Qualifier("OpteamerUserDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/assets").permitAll()
                        .requestMatchers("/api/assets/**").permitAll()
                        .requestMatchers("/api/inventories").permitAll()
                        .requestMatchers("/api/inventories/**").permitAll()
                        .requestMatchers("/api/preOperativeAssessments").permitAll()
                        .requestMatchers("/api/preOperativeAssessments/**").permitAll()
                        .requestMatchers("/api/patients/").permitAll()
                        .requestMatchers("/api/patients/**").permitAll()
                        .requestMatchers("/api/operationProviders/").permitAll()
                        .requestMatchers("/api/operationProviders/**").permitAll()
                        .requestMatchers("/api/operationRooms/").permitAll()
                        .requestMatchers("/api/operationRooms/**").permitAll()
                        .requestMatchers("/api/teamMembers/").permitAll()
                        .requestMatchers("/api/teamMembers/**").permitAll()
                        .requestMatchers("/api/roomInventories/").permitAll()
                        .requestMatchers("/api/roomInventories/**").permitAll()
                        .requestMatchers("/api/operationTypes/").permitAll()
                        .requestMatchers("/api/operationTypes/**").permitAll()
                        .requestMatchers("/api/operations/").permitAll()
                        .requestMatchers("/api/operations/**").permitAll()
                        .requestMatchers("/api/operationReport/").permitAll()
                        .requestMatchers("/api/operationReport/**").permitAll()
                        .requestMatchers("/api/userRegistration/").permitAll()
                        .requestMatchers("/api/userRegistration/**").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(LogoutConfigurer::permitAll);


        return http.build();
    }

    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
