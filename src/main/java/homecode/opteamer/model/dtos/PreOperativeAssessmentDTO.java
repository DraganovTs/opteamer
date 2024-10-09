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
        name = "PreOperativeAssessment",
        description = "Schema to hold pre-operative assessment details"
)
public class PreOperativeAssessmentDTO {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Schema(
            description = "Name of the pre-operative assessment",
            example = "Cardiovascular Assessment"
    )
    private String name;
}
