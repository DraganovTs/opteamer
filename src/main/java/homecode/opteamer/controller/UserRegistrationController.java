package homecode.opteamer.controller;

import homecode.opteamer.service.UserRegistrationService;
import homecode.opteamer.model.dtos.UserRegistrationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userRegistration")
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }


    @PostMapping
    public ResponseEntity<?> createTeamMember(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        userRegistrationService.createUser(userRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}


