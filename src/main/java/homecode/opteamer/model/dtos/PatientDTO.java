package homecode.opteamer.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(
        name = "Patient",
        description = "Schema to hold patient information"
)
public class PatientDTO {

    @Schema(
            description = "Unique identifier for the patient",
            example = "1"
    )
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
    @Schema(
            description = "Name of the patient",
            example = "John Doe"
    )
    private String name;

    @NotBlank(message = "NIN cannot be blank")
    @Size(min = 2, max = 20, message = "Nin must be between 1 and 2 characters")
    @Schema(
            description = "National Identification Number of the patient",
            example = "123456"
    )
    private String nin;
}
