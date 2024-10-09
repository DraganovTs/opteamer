package homecode.opteamer.controller;

import homecode.opteamer.model.dtos.AuthenticationRequestDTO;
import homecode.opteamer.model.dtos.AuthenticationResponseDTO;
import homecode.opteamer.model.dtos.ErrorResponseDTO;
import homecode.opteamer.util.JwtUtil;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "Authentication API",
        description = "REST API for Authentication in Opteamer Application"
)
@RestController
@RequestMapping("/api/authenticate")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    @Qualifier("OpteamerUserDetailsService") UserDetailsService userDetailsService,
                                    JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(
            summary = "Authenticate User",
            description = "REST API to authenticate a user and return a JWT token"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authentication was successful"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<?> getAuthenticationToken(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getUsername(),
                            authenticationRequestDTO.getPassword()));

        } catch (BadCredentialsException e) {
            return ResponseEntity.ok(new AuthenticationResponseDTO("Incorrect username or password",
                    false,
                    ""));
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequestDTO.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponseDTO("Authentication was successful", true, jwt));
    }
}
