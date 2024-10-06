package homecode.opteamer.model.dtos;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponseDTO extends AbstractResponseDTO{
    private LocalDateTime timestamp;
    private String details;

    public ErrorResponseDTO(String message, boolean success, String details) {
        super(message, success);
        this.timestamp = LocalDateTime.now();
        this.details = details;
    }
}
