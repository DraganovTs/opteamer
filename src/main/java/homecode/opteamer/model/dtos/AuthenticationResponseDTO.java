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
        name = "AuthenticationResponse",
        description = "Schema for the response received after authentication"
)
public class AuthenticationResponseDTO extends AbstractResponseDTO {

    @Schema(
            description = "JWT token issued after successful authentication",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    )
    private String jwt;

    public AuthenticationResponseDTO(String message, boolean succeeded, String jwt) {
        super(message, succeeded);
        this.jwt = jwt;
    }
}
