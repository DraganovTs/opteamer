package homecode.opteamer.controller;

import homecode.opteamer.service.UserRegistrationService;
import homecode.opteamer.model.dtos.UserRegistrationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "User Registration API",
        description = "REST API for user registration in the Opteamer Application"
)
@RestController
@RequestMapping("/api/userRegistration")
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @Operation(
            summary = "Register a New User",
            description = "REST API to create a new user registration"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        userRegistrationService.createUser(userRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
