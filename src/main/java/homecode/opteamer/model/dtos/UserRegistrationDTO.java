package homecode.opteamer.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(
        name = "UserRegistration",
        description = "Schema to hold user registration information"
)
public class UserRegistrationDTO {

    @NotBlank(message = "Username cannot be blank")
    @Schema(
            description = "Username for the new user",
            example = "johndoe"
    )
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Schema(
            description = "Email address of the new user",
            example = "johndoe@example.com"
    )
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Schema(
            description = "Password for the new user",
            example = "securePassword123"
    )
    private String password;
}
