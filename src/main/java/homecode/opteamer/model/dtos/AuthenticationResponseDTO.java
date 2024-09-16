package homecode.opteamer.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationResponseDTO extends AbstractResponseDTO {
    private String jwt;

    public AuthenticationResponseDTO(String message, boolean succeeded, String jwt) {
        super(message, succeeded);
        this.jwt = jwt;
    }
}
