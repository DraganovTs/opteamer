package homecode.opteamer.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(
        name = "ErrorResponse",
        description = "Schema for error responses in the API"
)
public class ErrorResponseDTO extends AbstractResponseDTO {

    @Schema(
            description = "Timestamp of when the error occurred",
            example = "2024-10-09T10:00:00"
    )
    private LocalDateTime timestamp;

    @Schema(
            description = "Detailed description of the error",
            example = "Invalid credentials provided"
    )
    private String details;

    public ErrorResponseDTO(String message, boolean success, String details) {
        super(message, success);
        this.timestamp = LocalDateTime.now();
        this.details = details;
    }
}
