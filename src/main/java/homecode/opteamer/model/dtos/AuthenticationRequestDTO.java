package homecode.opteamer.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(
        name = "AuthenticationRequest",
        description = "Schema for user authentication requests"
)
public class AuthenticationRequestDTO {

    @Schema(
            description = "Username for authentication",
            example = "john.doe"
    )
    private String username;

    @Schema(
            description = "Password for authentication",
            example = "P@ssw0rd"
    )
    private String password;
}
